package com.portage.app.service;

import com.portage.app.dto.request.ConsultantRequest;
import com.portage.app.dto.response.ConsultantResponse;

import java.util.List;
import java.util.Map;

public interface ConsultantService {
    ConsultantResponse create(ConsultantRequest request);
    ConsultantResponse findById(Long id);
    ConsultantResponse findByUserId(Long userId);
    List<ConsultantResponse> findAll();
    List<ConsultantResponse> findByManager(Long managerId);
    ConsultantResponse update(Long id, ConsultantRequest request);
    void delete(Long id);
    Map<String, Object> getStats(Long consultantId);
}
