package com.portage.app.controller;

import com.portage.app.dto.request.TimesheetRequest;
import com.portage.app.dto.response.TimesheetResponse;
import com.portage.app.service.TimesheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/timesheets")
@RequiredArgsConstructor
@Tag(name = "Timesheets", description = "Timesheet management APIs")
public class TimesheetController {

    private final TimesheetService timesheetService;

    @PostMapping
    @Operation(summary = "Create timesheet")
    public ResponseEntity<TimesheetResponse> create(@Valid @RequestBody TimesheetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheetService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all timesheets")
    public ResponseEntity<List<TimesheetResponse>> findAll(
            @RequestParam(required = false) Long consultantId) {
        if (consultantId != null) return ResponseEntity.ok(timesheetService.findByConsultant(consultantId));
        return ResponseEntity.ok(timesheetService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get timesheet by ID")
    public ResponseEntity<TimesheetResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update timesheet")
    public ResponseEntity<TimesheetResponse> update(@PathVariable Long id, @Valid @RequestBody TimesheetRequest request) {
        return ResponseEntity.ok(timesheetService.update(id, request));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submit timesheet")
    public ResponseEntity<TimesheetResponse> submit(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetService.submit(id));
    }

    @PostMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Validate timesheet")
    public ResponseEntity<TimesheetResponse> validate(
            @PathVariable Long id,
            @RequestParam Long validatedByUserId) {
        return ResponseEntity.ok(timesheetService.validate(id, validatedByUserId));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Reject timesheet")
    public ResponseEntity<TimesheetResponse> reject(
            @PathVariable Long id,
            @RequestParam(required = false) String comments) {
        return ResponseEntity.ok(timesheetService.reject(id, comments));
    }

    @GetMapping("/ca")
    @Operation(summary = "Calculate CA for consultant/month/year")
    public ResponseEntity<Map<String, BigDecimal>> calculateCA(
            @RequestParam Long consultantId,
            @RequestParam int month,
            @RequestParam int year) {
        BigDecimal ca = timesheetService.calculateCA(consultantId, month, year);
        return ResponseEntity.ok(Map.of("ca", ca));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Delete timesheet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timesheetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
