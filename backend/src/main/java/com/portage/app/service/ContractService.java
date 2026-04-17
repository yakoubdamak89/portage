package com.portage.app.service;

import com.portage.app.dto.request.ContractRequest;
import com.portage.app.dto.response.ContractResponse;

import java.util.List;

public interface ContractService {
    ContractResponse create(ContractRequest request);
    ContractResponse findById(Long id);
    List<ContractResponse> findAll();
    List<ContractResponse> findByConsultant(Long consultantId);
    ContractResponse update(Long id, ContractRequest request);
    void delete(Long id);
}
