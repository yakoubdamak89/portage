package com.portage.app.controller;

import com.portage.app.dto.request.PayslipRequest;
import com.portage.app.dto.response.PayslipResponse;
import com.portage.app.service.PayslipService;
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
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
@Tag(name = "Payslips", description = "Payslip management APIs")
public class PayslipController {

    private final PayslipService payslipService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Create payslip")
    public ResponseEntity<PayslipResponse> create(@Valid @RequestBody PayslipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payslipService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all payslips")
    public ResponseEntity<List<PayslipResponse>> findAll(
            @RequestParam(required = false) Long consultantId) {
        if (consultantId != null) return ResponseEntity.ok(payslipService.findByConsultant(consultantId));
        return ResponseEntity.ok(payslipService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payslip by ID")
    public ResponseEntity<PayslipResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(payslipService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Update payslip")
    public ResponseEntity<PayslipResponse> update(@PathVariable Long id, @Valid @RequestBody PayslipRequest request) {
        return ResponseEntity.ok(payslipService.update(id, request));
    }

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Generate payslip for consultant/month/year")
    public ResponseEntity<PayslipResponse> generate(
            @RequestParam Long consultantId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.status(HttpStatus.CREATED).body(payslipService.generate(consultantId, month, year));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete payslip")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        payslipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
