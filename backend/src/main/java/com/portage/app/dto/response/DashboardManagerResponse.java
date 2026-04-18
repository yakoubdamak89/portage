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
public class DashboardManagerResponse {
    private long totalConsultants;
    private long activeConsultants;
    private long totalClients;
    private long activeMissions;
    private long pendingTimesheets;
    private long unpaidInvoices;
    private BigDecimal totalRevenue;
    private BigDecimal monthlyRevenue;
    private List<MissionResponse> recentMissions;
    private List<InvoiceResponse> overdueInvoices;
}
