package com.portage.app.controller;

import com.portage.app.dto.response.DashboardConsultantResponse;
import com.portage.app.dto.response.DashboardManagerResponse;
import com.portage.app.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Dashboard APIs")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/manager")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Get manager dashboard")
    public ResponseEntity<DashboardManagerResponse> getManagerDashboard() {
        return ResponseEntity.ok(dashboardService.getManagerDashboard());
    }

    @GetMapping("/consultant/{id}")
    @Operation(summary = "Get consultant dashboard")
    public ResponseEntity<DashboardConsultantResponse> getConsultantDashboard(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardService.getConsultantDashboard(id));
    }
}
