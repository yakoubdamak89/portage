package com.portage.app.service.impl;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.ExpenseReport;
import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.entity.User;
import com.portage.app.domain.enums.ExpenseStatus;
import com.portage.app.dto.request.ExpenseReportRequest;
import com.portage.app.dto.response.ExpenseReportResponse;
import com.portage.app.exception.BadRequestException;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.ExpenseReportMapper;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.ExpenseReportRepository;
import com.portage.app.repository.MissionRepository;
import com.portage.app.repository.UserRepository;
import com.portage.app.service.ExpenseReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseReportServiceImpl implements ExpenseReportService {

    private final ExpenseReportRepository expenseReportRepository;
    private final ConsultantRepository consultantRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final ExpenseReportMapper mapper;

    @Override
    public ExpenseReportResponse create(ExpenseReportRequest request) {
        Consultant consultant = consultantRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));

        Mission mission = request.getMissionId() != null
                ? missionRepository.findById(request.getMissionId()).orElse(null) : null;

        ExpenseReport report = ExpenseReport.builder()
                .consultant(consultant)
                .mission(mission)
                .month(request.getMonth())
                .year(request.getYear())
                .status(ExpenseStatus.DRAFT)
                .build();

        return mapper.toResponse(expenseReportRepository.save(report));
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseReportResponse findById(Long id) {
        return mapper.toResponse(expenseReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseReport", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReportResponse> findAll() {
        return mapper.toResponseList(expenseReportRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseReportResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(expenseReportRepository.findByConsultantId(consultantId));
    }

    @Override
    public ExpenseReportResponse update(Long id, ExpenseReportRequest request) {
        ExpenseReport report = expenseReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseReport", id));

        if (report.getStatus() == ExpenseStatus.VALIDATED) {
            throw new BadRequestException("Cannot modify a validated expense report");
        }

        report.setMonth(request.getMonth());
        report.setYear(request.getYear());

        return mapper.toResponse(expenseReportRepository.save(report));
    }

    @Override
    public ExpenseReportResponse submit(Long id) {
        ExpenseReport report = expenseReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseReport", id));

        if (report.getStatus() != ExpenseStatus.DRAFT && report.getStatus() != ExpenseStatus.REJECTED) {
            throw new BadRequestException("Report cannot be submitted in current status");
        }

        report.setStatus(ExpenseStatus.SUBMITTED);
        report.setSubmittedAt(LocalDateTime.now());
        return mapper.toResponse(expenseReportRepository.save(report));
    }

    @Override
    public ExpenseReportResponse validate(Long id, Long validatedByUserId) {
        ExpenseReport report = expenseReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseReport", id));

        if (report.getStatus() != ExpenseStatus.SUBMITTED) {
            throw new BadRequestException("Report must be submitted before validation");
        }

        User validator = userRepository.findById(validatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", validatedByUserId));

        report.setStatus(ExpenseStatus.VALIDATED);
        report.setValidatedAt(LocalDateTime.now());
        report.setValidatedBy(validator);
        return mapper.toResponse(expenseReportRepository.save(report));
    }

    @Override
    public ExpenseReportResponse reject(Long id) {
        ExpenseReport report = expenseReportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseReport", id));

        if (report.getStatus() != ExpenseStatus.SUBMITTED) {
            throw new BadRequestException("Report must be submitted before rejection");
        }

        report.setStatus(ExpenseStatus.REJECTED);
        return mapper.toResponse(expenseReportRepository.save(report));
    }

    @Override
    public void delete(Long id) {
        if (!expenseReportRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExpenseReport", id);
        }
        expenseReportRepository.deleteById(id);
    }
}
