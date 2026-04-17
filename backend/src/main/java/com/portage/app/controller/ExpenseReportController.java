package com.portage.app.controller;

import com.portage.app.dto.request.ExpenseReportRequest;
import com.portage.app.dto.response.ExpenseReportResponse;
import com.portage.app.service.ExpenseReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expense Reports", description = "Expense report management APIs")
public class ExpenseReportController {

    private final ExpenseReportService expenseReportService;

    @PostMapping
    @Operation(summary = "Create expense report")
    public ResponseEntity<ExpenseReportResponse> create(@Valid @RequestBody ExpenseReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseReportService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all expense reports")
    public ResponseEntity<List<ExpenseReportResponse>> findAll(
            @RequestParam(required = false) Long consultantId) {
        if (consultantId != null) return ResponseEntity.ok(expenseReportService.findByConsultant(consultantId));
        return ResponseEntity.ok(expenseReportService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expense report by ID")
    public ResponseEntity<ExpenseReportResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseReportService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update expense report")
    public ResponseEntity<ExpenseReportResponse> update(@PathVariable Long id, @Valid @RequestBody ExpenseReportRequest request) {
        return ResponseEntity.ok(expenseReportService.update(id, request));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submit expense report")
    public ResponseEntity<ExpenseReportResponse> submit(@PathVariable Long id) {
        return ResponseEntity.ok(expenseReportService.submit(id));
    }

    @PostMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Validate expense report")
    public ResponseEntity<ExpenseReportResponse> validate(
            @PathVariable Long id,
            @RequestParam Long validatedByUserId) {
        return ResponseEntity.ok(expenseReportService.validate(id, validatedByUserId));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Reject expense report")
    public ResponseEntity<ExpenseReportResponse> reject(@PathVariable Long id) {
        return ResponseEntity.ok(expenseReportService.reject(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Delete expense report")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseReportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
