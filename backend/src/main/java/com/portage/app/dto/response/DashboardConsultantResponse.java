package com.portage.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardConsultantResponse {
    private Long consultantId;
    private String consultantName;
    private BigDecimal walletBalance;
    private BigDecimal monthlyRevenue;
    private BigDecimal yearlyRevenue;
    private long activeMissions;
    private long pendingTimesheets;
    private long unpaidInvoices;
    private List<MissionResponse> missions;
    private List<TimesheetResponse> recentTimesheets;
    private List<PayslipResponse> recentPayslips;
}
