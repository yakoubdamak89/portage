package com.portage.app.service;

import com.portage.app.dto.request.ClientCompanyRequest;
import com.portage.app.dto.response.ClientCompanyResponse;

import java.util.List;

public interface ClientCompanyService {
    ClientCompanyResponse create(ClientCompanyRequest request);
    ClientCompanyResponse findById(Long id);
    List<ClientCompanyResponse> findAll();
    List<ClientCompanyResponse> findAllActive();
    ClientCompanyResponse update(Long id, ClientCompanyRequest request);
    ClientCompanyResponse toggleActive(Long id);
    void delete(Long id);
}
