package com.portage.app.service;

import com.portage.app.domain.enums.MissionStatus;
import com.portage.app.dto.request.MissionRequest;
import com.portage.app.dto.response.MissionResponse;

import java.util.List;

public interface MissionService {
    MissionResponse create(MissionRequest request);
    MissionResponse findById(Long id);
    List<MissionResponse> findAll();
    List<MissionResponse> findByConsultant(Long consultantId);
    List<MissionResponse> findByClient(Long clientId);
    MissionResponse update(Long id, MissionRequest request);
    MissionResponse updateStatus(Long id, MissionStatus status);
    void delete(Long id);
}
