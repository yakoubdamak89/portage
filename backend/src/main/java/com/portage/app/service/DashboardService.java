package com.portage.app.service;

import com.portage.app.dto.response.DashboardConsultantResponse;
import com.portage.app.dto.response.DashboardManagerResponse;

public interface DashboardService {
    DashboardManagerResponse getManagerDashboard();
    DashboardConsultantResponse getConsultantDashboard(Long consultantId);
}
