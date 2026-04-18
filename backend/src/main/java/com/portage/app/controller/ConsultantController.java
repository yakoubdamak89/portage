package com.portage.app.controller;

import com.portage.app.dto.request.ConsultantRequest;
import com.portage.app.dto.response.ConsultantResponse;
import com.portage.app.service.ConsultantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultants")
@RequiredArgsConstructor
@Tag(name = "Consultants", description = "Consultant management APIs")
public class ConsultantController {

    private final ConsultantService consultantService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Create consultant")
    public ResponseEntity<ConsultantResponse> create(@Valid @RequestBody ConsultantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultantService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Get all consultants")
    public ResponseEntity<List<ConsultantResponse>> findAll() {
        return ResponseEntity.ok(consultantService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get consultant by ID")
    public ResponseEntity<ConsultantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consultantService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get consultant by user ID")
    public ResponseEntity<ConsultantResponse> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(consultantService.findByUserId(userId));
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Get consultants by manager")
    public ResponseEntity<List<ConsultantResponse>> findByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(consultantService.findByManager(managerId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Update consultant")
    public ResponseEntity<ConsultantResponse> update(@PathVariable Long id, @Valid @RequestBody ConsultantRequest request) {
        return ResponseEntity.ok(consultantService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete consultant")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stats")
    @Operation(summary = "Get consultant stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        return ResponseEntity.ok(consultantService.getStats(id));
    }
}
