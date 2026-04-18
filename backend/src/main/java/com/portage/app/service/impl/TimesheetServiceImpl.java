package com.portage.app.service.impl;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.entity.Timesheet;
import com.portage.app.domain.entity.User;
import com.portage.app.domain.enums.TimesheetStatus;
import com.portage.app.dto.request.TimesheetRequest;
import com.portage.app.dto.response.TimesheetResponse;
import com.portage.app.exception.BadRequestException;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.TimesheetMapper;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.MissionRepository;
import com.portage.app.repository.TimesheetRepository;
import com.portage.app.repository.UserRepository;
import com.portage.app.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimesheetServiceImpl implements TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ConsultantRepository consultantRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;
    private final TimesheetMapper mapper;

    @Override
    public TimesheetResponse create(TimesheetRequest request) {
        Consultant consultant = consultantRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));

        Mission mission = request.getMissionId() != null
                ? missionRepository.findById(request.getMissionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mission", request.getMissionId()))
                : null;

        Timesheet timesheet = Timesheet.builder()
                .consultant(consultant)
                .mission(mission)
                .month(request.getMonth())
                .year(request.getYear())
                .status(TimesheetStatus.DRAFT)
                .totalDays(request.getTotalDays() != null ? request.getTotalDays() : BigDecimal.ZERO)
                .totalHours(request.getTotalHours() != null ? request.getTotalHours() : BigDecimal.ZERO)
                .comments(request.getComments())
                .build();

        return mapper.toResponse(timesheetRepository.save(timesheet));
    }

    @Override
    @Transactional(readOnly = true)
    public TimesheetResponse findById(Long id) {
        return mapper.toResponse(timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimesheetResponse> findAll() {
        return mapper.toResponseList(timesheetRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimesheetResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(timesheetRepository.findByConsultantId(consultantId));
    }

    @Override
    public TimesheetResponse update(Long id, TimesheetRequest request) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", id));

        if (timesheet.getStatus() == TimesheetStatus.VALIDATED) {
            throw new BadRequestException("Cannot modify a validated timesheet");
        }

        if (request.getTotalDays() != null) timesheet.setTotalDays(request.getTotalDays());
        if (request.getTotalHours() != null) timesheet.setTotalHours(request.getTotalHours());
        timesheet.setComments(request.getComments());

        return mapper.toResponse(timesheetRepository.save(timesheet));
    }

    @Override
    public TimesheetResponse submit(Long id) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", id));

        if (timesheet.getStatus() != TimesheetStatus.DRAFT && timesheet.getStatus() != TimesheetStatus.REJECTED) {
            throw new BadRequestException("Timesheet cannot be submitted in current status: " + timesheet.getStatus());
        }

        timesheet.setStatus(TimesheetStatus.SUBMITTED);
        timesheet.setSubmittedAt(LocalDateTime.now());
        return mapper.toResponse(timesheetRepository.save(timesheet));
    }

    @Override
    public TimesheetResponse validate(Long id, Long validatedByUserId) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", id));

        if (timesheet.getStatus() != TimesheetStatus.SUBMITTED) {
            throw new BadRequestException("Timesheet must be submitted before validation");
        }

        User validator = userRepository.findById(validatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", validatedByUserId));

        timesheet.setStatus(TimesheetStatus.VALIDATED);
        timesheet.setValidatedAt(LocalDateTime.now());
        timesheet.setValidatedBy(validator);
        return mapper.toResponse(timesheetRepository.save(timesheet));
    }

    @Override
    public TimesheetResponse reject(Long id, String comments) {
        Timesheet timesheet = timesheetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", id));

        if (timesheet.getStatus() != TimesheetStatus.SUBMITTED) {
            throw new BadRequestException("Timesheet must be submitted before rejection");
        }

        timesheet.setStatus(TimesheetStatus.REJECTED);
        timesheet.setComments(comments);
        return mapper.toResponse(timesheetRepository.save(timesheet));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCA(Long consultantId, int month, int year) {
        return timesheetRepository.findByConsultantIdAndMonthAndYear(consultantId, month, year)
                .map(ts -> {
                    BigDecimal rate = ts.getConsultant().getDailyRate() != null ? ts.getConsultant().getDailyRate() : BigDecimal.ZERO;
                    return ts.getTotalDays().multiply(rate);
                })
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void delete(Long id) {
        if (!timesheetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Timesheet", id);
        }
        timesheetRepository.deleteById(id);
    }
}
