package com.portage.app.service.impl;

import com.portage.app.domain.enums.InvoiceStatus;
import com.portage.app.domain.enums.MissionStatus;
import com.portage.app.domain.enums.TimesheetStatus;
import com.portage.app.dto.response.*;
import com.portage.app.mapper.InvoiceMapper;
import com.portage.app.mapper.MissionMapper;
import com.portage.app.mapper.PayslipMapper;
import com.portage.app.mapper.TimesheetMapper;
import com.portage.app.repository.*;
import com.portage.app.service.DashboardService;
import com.portage.app.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final ConsultantRepository consultantRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final MissionRepository missionRepository;
    private final TimesheetRepository timesheetRepository;
    private final InvoiceRepository invoiceRepository;
    private final PayslipRepository payslipRepository;
    private final WalletService walletService;
    private final MissionMapper missionMapper;
    private final InvoiceMapper invoiceMapper;
    private final TimesheetMapper timesheetMapper;
    private final PayslipMapper payslipMapper;

    @Override
    public DashboardManagerResponse getManagerDashboard() {
        long totalConsultants = consultantRepository.count();
        long totalClients = clientCompanyRepository.count();
        long activeMissions = missionRepository.findByStatus(MissionStatus.ACTIVE).size();
        long pendingTimesheets = timesheetRepository.findByStatus(TimesheetStatus.SUBMITTED).size();
        long unpaidInvoices = invoiceRepository.countByStatus(InvoiceStatus.SENT)
                + invoiceRepository.countByStatus(InvoiceStatus.OVERDUE);

        List<MissionResponse> recentMissions = missionMapper.toResponseList(
                missionRepository.findByStatus(MissionStatus.ACTIVE));
        List<InvoiceResponse> overdueInvoices = invoiceMapper.toResponseList(
                invoiceRepository.findByStatus(InvoiceStatus.OVERDUE));

        return DashboardManagerResponse.builder()
                .totalConsultants(totalConsultants)
                .activeConsultants(totalConsultants)
                .totalClients(totalClients)
                .activeMissions(activeMissions)
                .pendingTimesheets(pendingTimesheets)
                .unpaidInvoices(unpaidInvoices)
                .totalRevenue(BigDecimal.ZERO)
                .monthlyRevenue(BigDecimal.ZERO)
                .recentMissions(recentMissions)
                .overdueInvoices(overdueInvoices)
                .build();
    }

    @Override
    public DashboardConsultantResponse getConsultantDashboard(Long consultantId) {
        var consultant = consultantRepository.findById(consultantId).orElse(null);
        if (consultant == null) {
            return DashboardConsultantResponse.builder().consultantId(consultantId).build();
        }

        BigDecimal walletBalance = walletService.calculateBalance(consultantId);

        List<MissionResponse> missions = missionMapper.toResponseList(
                missionRepository.findByConsultantId(consultantId));
        long activeMissions = missions.stream()
                .filter(m -> m.getStatus() == MissionStatus.ACTIVE).count();

        List<TimesheetResponse> recentTimesheets = timesheetMapper.toResponseList(
                timesheetRepository.findByConsultantId(consultantId));

        List<PayslipResponse> recentPayslips = payslipMapper.toResponseList(
                payslipRepository.findByConsultantId(consultantId));

        long pendingTimesheets = recentTimesheets.stream()
                .filter(t -> t.getStatus() == TimesheetStatus.DRAFT || t.getStatus() == TimesheetStatus.REJECTED)
                .count();
        long unpaidInvoices = invoiceRepository.findByConsultantId(consultantId).stream()
                .filter(i -> i.getStatus() == InvoiceStatus.SENT || i.getStatus() == InvoiceStatus.OVERDUE)
                .count();

        return DashboardConsultantResponse.builder()
                .consultantId(consultantId)
                .consultantName(consultant.getFirstName() + " " + consultant.getLastName())
                .walletBalance(walletBalance)
                .monthlyRevenue(BigDecimal.ZERO)
                .yearlyRevenue(BigDecimal.ZERO)
                .activeMissions(activeMissions)
                .pendingTimesheets(pendingTimesheets)
                .unpaidInvoices(unpaidInvoices)
                .missions(missions)
                .recentTimesheets(recentTimesheets)
                .recentPayslips(recentPayslips)
                .build();
    }
}
