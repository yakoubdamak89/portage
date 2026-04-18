package com.portage.app.service.impl;

import com.portage.app.domain.entity.*;
import com.portage.app.domain.enums.ContractStatus;
import com.portage.app.dto.request.ContractRequest;
import com.portage.app.dto.response.ContractResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.ContractMapper;
import com.portage.app.repository.*;
import com.portage.app.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ConsultantRepository consultantRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final MissionRepository missionRepository;
    private final ContractMapper mapper;

    @Override
    public ContractResponse create(ContractRequest request) {
        Consultant consultant = request.getConsultantId() != null
                ? consultantRepository.findById(request.getConsultantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()))
                : null;
        ClientCompany client = request.getClientCompanyId() != null
                ? clientCompanyRepository.findById(request.getClientCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", request.getClientCompanyId()))
                : null;
        Mission mission = request.getMissionId() != null
                ? missionRepository.findById(request.getMissionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mission", request.getMissionId()))
                : null;

        Contract contract = Contract.builder()
                .type(request.getType())
                .status(request.getStatus() != null ? request.getStatus() : ContractStatus.DRAFT)
                .consultant(consultant)
                .clientCompany(client)
                .mission(mission)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .filePath(request.getFilePath())
                .signedDate(request.getSignedDate())
                .build();

        return mapper.toResponse(contractRepository.save(contract));
    }

    @Override
    @Transactional(readOnly = true)
    public ContractResponse findById(Long id) {
        return mapper.toResponse(contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractResponse> findAll() {
        return mapper.toResponseList(contractRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(contractRepository.findByConsultantId(consultantId));
    }

    @Override
    public ContractResponse update(Long id, ContractRequest request) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", id));

        if (request.getStatus() != null) contract.setStatus(request.getStatus());
        contract.setType(request.getType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setFilePath(request.getFilePath());
        contract.setSignedDate(request.getSignedDate());

        return mapper.toResponse(contractRepository.save(contract));
    }

    @Override
    public void delete(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contract", id);
        }
        contractRepository.deleteById(id);
    }
}
