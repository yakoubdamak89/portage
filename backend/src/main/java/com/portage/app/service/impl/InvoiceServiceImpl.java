package com.portage.app.service.impl;

import com.portage.app.domain.entity.*;
import com.portage.app.domain.enums.InvoiceStatus;
import com.portage.app.dto.request.InvoiceRequest;
import com.portage.app.dto.response.InvoiceResponse;
import com.portage.app.exception.BadRequestException;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.InvoiceMapper;
import com.portage.app.repository.*;
import com.portage.app.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ConsultantRepository consultantRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final MissionRepository missionRepository;
    private final TimesheetRepository timesheetRepository;
    private final InvoiceMapper mapper;

    @Override
    public InvoiceResponse create(InvoiceRequest request) {
        Consultant consultant = consultantRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));
        ClientCompany client = clientCompanyRepository.findById(request.getClientCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", request.getClientCompanyId()));

        Mission mission = request.getMissionId() != null
                ? missionRepository.findById(request.getMissionId()).orElse(null) : null;
        Timesheet timesheet = request.getTimesheetId() != null
                ? timesheetRepository.findById(request.getTimesheetId()).orElse(null) : null;

        String invoiceNumber = request.getInvoiceNumber() != null ? request.getInvoiceNumber() : generateInvoiceNumber();

        BigDecimal totalHT = request.getTotalHT() != null ? request.getTotalHT() : BigDecimal.ZERO;
        BigDecimal vatRate = request.getVatRate() != null ? request.getVatRate() : new BigDecimal("20.00");
        BigDecimal totalTTC = request.getTotalTTC() != null ? request.getTotalTTC()
                : totalHT.multiply(BigDecimal.ONE.add(vatRate.divide(new BigDecimal("100"))));

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceNumber)
                .consultant(consultant)
                .clientCompany(client)
                .mission(mission)
                .timesheet(timesheet)
                .issueDate(request.getIssueDate() != null ? request.getIssueDate() : LocalDate.now())
                .dueDate(request.getDueDate())
                .totalHT(totalHT)
                .vatRate(vatRate)
                .totalTTC(totalTTC)
                .status(request.getStatus() != null ? request.getStatus() : InvoiceStatus.DRAFT)
                .build();

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse findById(Long id) {
        return mapper.toResponse(invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findAll() {
        return mapper.toResponseList(invoiceRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(invoiceRepository.findByConsultantId(consultantId));
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));

        if (request.getTotalHT() != null) invoice.setTotalHT(request.getTotalHT());
        if (request.getVatRate() != null) invoice.setVatRate(request.getVatRate());
        if (request.getTotalTTC() != null) invoice.setTotalTTC(request.getTotalTTC());
        if (request.getStatus() != null) invoice.setStatus(request.getStatus());
        if (request.getDueDate() != null) invoice.setDueDate(request.getDueDate());
        if (request.getIssueDate() != null) invoice.setIssueDate(request.getIssueDate());

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse generateFromTimesheet(Long timesheetId) {
        Timesheet timesheet = timesheetRepository.findById(timesheetId)
                .orElseThrow(() -> new ResourceNotFoundException("Timesheet", timesheetId));

        if (invoiceRepository.findByTimesheetId(timesheetId).isPresent()) {
            throw new BadRequestException("Invoice already exists for timesheet: " + timesheetId);
        }

        BigDecimal dailyRate = timesheet.getConsultant().getDailyRate() != null
                ? timesheet.getConsultant().getDailyRate() : BigDecimal.ZERO;
        BigDecimal totalHT = timesheet.getTotalDays().multiply(dailyRate);
        BigDecimal vatRate = new BigDecimal("20.00");
        BigDecimal totalTTC = totalHT.multiply(new BigDecimal("1.20"));

        Integer paymentTermsDays = (timesheet.getMission() != null
                && timesheet.getMission().getClientCompany() != null
                && timesheet.getMission().getClientCompany().getPaymentTermsDays() != null)
                ? timesheet.getMission().getClientCompany().getPaymentTermsDays() : 30;

        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .consultant(timesheet.getConsultant())
                .clientCompany(timesheet.getMission() != null ? timesheet.getMission().getClientCompany() : null)
                .mission(timesheet.getMission())
                .timesheet(timesheet)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(paymentTermsDays))
                .totalHT(totalHT)
                .vatRate(vatRate)
                .totalTTC(totalTTC)
                .status(InvoiceStatus.DRAFT)
                .build();

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse send(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
        invoice.setStatus(InvoiceStatus.SENT);
        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse markPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void delete(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice", id);
        }
        invoiceRepository.deleteById(id);
    }

    private String generateInvoiceNumber() {
        String prefix = "INV-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "-";
        long count = invoiceRepository.count() + 1;
        return prefix + String.format("%04d", count);
    }
}
