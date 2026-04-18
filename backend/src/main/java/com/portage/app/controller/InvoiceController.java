package com.portage.app.controller;

import com.portage.app.dto.request.InvoiceRequest;
import com.portage.app.dto.response.InvoiceResponse;
import com.portage.app.service.InvoiceService;
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
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "Invoice management APIs")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Create invoice")
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all invoices")
    public ResponseEntity<List<InvoiceResponse>> findAll(
            @RequestParam(required = false) Long consultantId) {
        if (consultantId != null) return ResponseEntity.ok(invoiceService.findByConsultant(consultantId));
        return ResponseEntity.ok(invoiceService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    public ResponseEntity<InvoiceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Update invoice")
    public ResponseEntity<InvoiceResponse> update(@PathVariable Long id, @Valid @RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(invoiceService.update(id, request));
    }

    @PostMapping("/generate-from-timesheet/{timesheetId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Generate invoice from timesheet")
    public ResponseEntity<InvoiceResponse> generateFromTimesheet(@PathVariable Long timesheetId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.generateFromTimesheet(timesheetId));
    }

    @PostMapping("/{id}/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Send invoice")
    public ResponseEntity<InvoiceResponse> send(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.send(id));
    }

    @PostMapping("/{id}/mark-paid")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Mark invoice as paid")
    public ResponseEntity<InvoiceResponse> markPaid(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.markPaid(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete invoice")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
