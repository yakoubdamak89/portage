package com.portage.app.service.impl;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Payslip;
import com.portage.app.dto.request.PayslipRequest;
import com.portage.app.dto.response.PayslipResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.PayslipMapper;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.PayslipRepository;
import com.portage.app.repository.TimesheetRepository;
import com.portage.app.service.PayslipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PayslipServiceImpl implements PayslipService {

    private final PayslipRepository payslipRepository;
    private final ConsultantRepository consultantRepository;
    private final TimesheetRepository timesheetRepository;
    private final PayslipMapper mapper;

    @Override
    public PayslipResponse create(PayslipRequest request) {
        Consultant consultant = consultantRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));

        Payslip payslip = Payslip.builder()
                .consultant(consultant)
                .month(request.getMonth())
                .year(request.getYear())
                .grossSalary(request.getGrossSalary() != null ? request.getGrossSalary() : BigDecimal.ZERO)
                .netSalary(request.getNetSalary() != null ? request.getNetSalary() : BigDecimal.ZERO)
                .employerCharges(request.getEmployerCharges() != null ? request.getEmployerCharges() : BigDecimal.ZERO)
                .employeeCharges(request.getEmployeeCharges() != null ? request.getEmployeeCharges() : BigDecimal.ZERO)
                .expenses(request.getExpenses() != null ? request.getExpenses() : BigDecimal.ZERO)
                .filePath(request.getFilePath())
                .generatedAt(LocalDateTime.now())
                .build();

        return mapper.toResponse(payslipRepository.save(payslip));
    }

    @Override
    @Transactional(readOnly = true)
    public PayslipResponse findById(Long id) {
        return mapper.toResponse(payslipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payslip", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayslipResponse> findAll() {
        return mapper.toResponseList(payslipRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayslipResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(payslipRepository.findByConsultantId(consultantId));
    }

    @Override
    public PayslipResponse update(Long id, PayslipRequest request) {
        Payslip payslip = payslipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payslip", id));

        if (request.getGrossSalary() != null) payslip.setGrossSalary(request.getGrossSalary());
        if (request.getNetSalary() != null) payslip.setNetSalary(request.getNetSalary());
        if (request.getEmployerCharges() != null) payslip.setEmployerCharges(request.getEmployerCharges());
        if (request.getEmployeeCharges() != null) payslip.setEmployeeCharges(request.getEmployeeCharges());
        if (request.getExpenses() != null) payslip.setExpenses(request.getExpenses());
        if (request.getFilePath() != null) payslip.setFilePath(request.getFilePath());

        return mapper.toResponse(payslipRepository.save(payslip));
    }

    @Override
    public PayslipResponse generate(Long consultantId, int month, int year) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", consultantId));

        BigDecimal dailyRate = consultant.getDailyRate() != null ? consultant.getDailyRate() : BigDecimal.ZERO;
        BigDecimal feeRate = consultant.getManagementFeeRate() != null
                ? consultant.getManagementFeeRate() : new BigDecimal("10");

        BigDecimal totalDays = timesheetRepository.findByConsultantIdAndMonthAndYear(consultantId, month, year)
                .map(Payslip -> Payslip.getTotalDays())
                .orElse(BigDecimal.ZERO);

        BigDecimal grossRevenue = totalDays.multiply(dailyRate);
        BigDecimal managementFee = grossRevenue.multiply(feeRate.divide(new BigDecimal("100")));
        BigDecimal grossSalary = grossRevenue.subtract(managementFee);
        BigDecimal employeeCharges = grossSalary.multiply(new BigDecimal("0.22"));
        BigDecimal employerCharges = grossSalary.multiply(new BigDecimal("0.42"));
        BigDecimal netSalary = grossSalary.subtract(employeeCharges);

        PayslipRequest request = new PayslipRequest();
        request.setConsultantId(consultantId);
        request.setMonth(month);
        request.setYear(year);
        request.setGrossSalary(grossSalary);
        request.setNetSalary(netSalary);
        request.setEmployerCharges(employerCharges);
        request.setEmployeeCharges(employeeCharges);
        request.setExpenses(BigDecimal.ZERO);

        return create(request);
    }

    @Override
    public void delete(Long id) {
        if (!payslipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payslip", id);
        }
        payslipRepository.deleteById(id);
    }
}
