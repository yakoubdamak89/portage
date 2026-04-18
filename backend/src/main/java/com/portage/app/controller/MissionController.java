package com.portage.app.controller;

import com.portage.app.domain.enums.MissionStatus;
import com.portage.app.dto.request.MissionRequest;
import com.portage.app.dto.response.MissionResponse;
import com.portage.app.service.MissionService;
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
@RequestMapping("/api/missions")
@RequiredArgsConstructor
@Tag(name = "Missions", description = "Mission management APIs")
public class MissionController {

    private final MissionService missionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Create mission")
    public ResponseEntity<MissionResponse> create(@Valid @RequestBody MissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(missionService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all missions")
    public ResponseEntity<List<MissionResponse>> findAll(
            @RequestParam(required = false) Long consultantId,
            @RequestParam(required = false) Long clientId) {
        if (consultantId != null) return ResponseEntity.ok(missionService.findByConsultant(consultantId));
        if (clientId != null) return ResponseEntity.ok(missionService.findByClient(clientId));
        return ResponseEntity.ok(missionService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mission by ID")
    public ResponseEntity<MissionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(missionService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Update mission")
    public ResponseEntity<MissionResponse> update(@PathVariable Long id, @Valid @RequestBody MissionRequest request) {
        return ResponseEntity.ok(missionService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")
    @Operation(summary = "Update mission status")
    public ResponseEntity<MissionResponse> updateStatus(@PathVariable Long id, @RequestParam MissionStatus status) {
        return ResponseEntity.ok(missionService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete mission")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
