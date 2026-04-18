package com.portage.app.service.impl;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.dto.request.ClientCompanyRequest;
import com.portage.app.dto.response.ClientCompanyResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.ClientCompanyMapper;
import com.portage.app.repository.ClientCompanyRepository;
import com.portage.app.service.ClientCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientCompanyServiceImpl implements ClientCompanyService {

    private final ClientCompanyRepository repository;
    private final ClientCompanyMapper mapper;

    @Override
    public ClientCompanyResponse create(ClientCompanyRequest request) {
        ClientCompany company = ClientCompany.builder()
                .name(request.getName())
                .siret(request.getSiret())
                .address(request.getAddress())
                .billingAddress(request.getBillingAddress())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .contactName(request.getContactName())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .paymentTermsDays(request.getPaymentTermsDays())
                .active(request.isActive())
                .vatNumber(request.getVatNumber())
                .build();
        return mapper.toResponse(repository.save(company));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientCompanyResponse findById(Long id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientCompanyResponse> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientCompanyResponse> findAllActive() {
        return mapper.toResponseList(repository.findByActiveTrue());
    }

    @Override
    public ClientCompanyResponse update(Long id, ClientCompanyRequest request) {
        ClientCompany company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", id));

        company.setName(request.getName());
        company.setSiret(request.getSiret());
        company.setAddress(request.getAddress());
        company.setBillingAddress(request.getBillingAddress());
        company.setCity(request.getCity());
        company.setPostalCode(request.getPostalCode());
        company.setCountry(request.getCountry());
        company.setContactName(request.getContactName());
        company.setContactEmail(request.getContactEmail());
        company.setContactPhone(request.getContactPhone());
        company.setPaymentTermsDays(request.getPaymentTermsDays());
        company.setVatNumber(request.getVatNumber());

        return mapper.toResponse(repository.save(company));
    }

    @Override
    public ClientCompanyResponse toggleActive(Long id) {
        ClientCompany company = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", id));
        company.setActive(!company.isActive());
        return mapper.toResponse(repository.save(company));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ClientCompany", id);
        }
        repository.deleteById(id);
    }
}
