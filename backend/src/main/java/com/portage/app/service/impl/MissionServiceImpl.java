package com.portage.app.service.impl;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.enums.MissionStatus;
import com.portage.app.dto.request.MissionRequest;
import com.portage.app.dto.response.MissionResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.MissionMapper;
import com.portage.app.repository.ClientCompanyRepository;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.MissionRepository;
import com.portage.app.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final ConsultantRepository consultantRepository;
    private final ClientCompanyRepository clientCompanyRepository;
    private final MissionMapper mapper;

    @Override
    public MissionResponse create(MissionRequest request) {
        Consultant consultant = consultantRepository.findById(request.getConsultantId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));
        ClientCompany client = clientCompanyRepository.findById(request.getClientCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", request.getClientCompanyId()));

        Mission mission = Mission.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .consultant(consultant)
                .clientCompany(client)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .dailyRate(request.getDailyRate())
                .estimatedDays(request.getEstimatedDays())
                .status(request.getStatus() != null ? request.getStatus() : MissionStatus.DRAFT)
                .billingType(request.getBillingType())
                .build();

        return mapper.toResponse(missionRepository.save(mission));
    }

    @Override
    @Transactional(readOnly = true)
    public MissionResponse findById(Long id) {
        return mapper.toResponse(missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionResponse> findAll() {
        return mapper.toResponseList(missionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionResponse> findByConsultant(Long consultantId) {
        return mapper.toResponseList(missionRepository.findByConsultantId(consultantId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionResponse> findByClient(Long clientId) {
        return mapper.toResponseList(missionRepository.findByClientCompanyId(clientId));
    }

    @Override
    public MissionResponse update(Long id, MissionRequest request) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission", id));

        if (request.getConsultantId() != null) {
            Consultant consultant = consultantRepository.findById(request.getConsultantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Consultant", request.getConsultantId()));
            mission.setConsultant(consultant);
        }
        if (request.getClientCompanyId() != null) {
            ClientCompany client = clientCompanyRepository.findById(request.getClientCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("ClientCompany", request.getClientCompanyId()));
            mission.setClientCompany(client);
        }

        mission.setTitle(request.getTitle());
        mission.setDescription(request.getDescription());
        mission.setStartDate(request.getStartDate());
        mission.setEndDate(request.getEndDate());
        mission.setDailyRate(request.getDailyRate());
        mission.setEstimatedDays(request.getEstimatedDays());
        if (request.getStatus() != null) mission.setStatus(request.getStatus());
        mission.setBillingType(request.getBillingType());

        return mapper.toResponse(missionRepository.save(mission));
    }

    @Override
    public MissionResponse updateStatus(Long id, MissionStatus status) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission", id));
        mission.setStatus(status);
        return mapper.toResponse(missionRepository.save(mission));
    }

    @Override
    public void delete(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mission", id);
        }
        missionRepository.deleteById(id);
    }
}
