package com.portage.app.service.impl;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.User;
import com.portage.app.dto.request.ConsultantRequest;
import com.portage.app.dto.response.ConsultantResponse;
import com.portage.app.exception.BadRequestException;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.ConsultantMapper;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.InvoiceRepository;
import com.portage.app.repository.MissionRepository;
import com.portage.app.repository.UserRepository;
import com.portage.app.service.ConsultantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsultantServiceImpl implements ConsultantService {

    private final ConsultantRepository consultantRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final InvoiceRepository invoiceRepository;
    private final ConsultantMapper consultantMapper;

    @Override
    public ConsultantResponse create(ConsultantRequest request) {
        if (consultantRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Consultant email already exists: " + request.getEmail());
        }

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));
        }

        User manager = null;
        if (request.getManagerId() != null) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager", request.getManagerId()));
        }

        Consultant consultant = Consultant.builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .socialSecurityNumber(request.getSocialSecurityNumber())
                .contractType(request.getContractType())
                .dailyRate(request.getDailyRate())
                .hourlyRate(request.getHourlyRate())
                .managementFeeRate(request.getManagementFeeRate())
                .status(request.getStatus())
                .ribIban(request.getRibIban())
                .ribBic(request.getRibBic())
                .manager(manager)
                .build();

        return consultantMapper.toResponse(consultantRepository.save(consultant));
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultantResponse findById(Long id) {
        return consultantMapper.toResponse(consultantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultantResponse findByUserId(Long userId) {
        return consultantMapper.toResponse(consultantRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant not found for user: " + userId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultantResponse> findAll() {
        return consultantMapper.toResponseList(consultantRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultantResponse> findByManager(Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", managerId));
        return consultantMapper.toResponseList(consultantRepository.findByManager(manager));
    }

    @Override
    public ConsultantResponse update(Long id, ConsultantRequest request) {
        Consultant consultant = consultantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", id));

        if (!consultant.getEmail().equals(request.getEmail()) &&
                consultantRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Consultant email already exists: " + request.getEmail());
        }

        User manager = null;
        if (request.getManagerId() != null) {
            manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager", request.getManagerId()));
        }

        consultant.setFirstName(request.getFirstName());
        consultant.setLastName(request.getLastName());
        consultant.setEmail(request.getEmail());
        consultant.setPhone(request.getPhone());
        consultant.setAddress(request.getAddress());
        consultant.setDateOfBirth(request.getDateOfBirth());
        consultant.setSocialSecurityNumber(request.getSocialSecurityNumber());
        consultant.setContractType(request.getContractType());
        consultant.setDailyRate(request.getDailyRate());
        consultant.setHourlyRate(request.getHourlyRate());
        consultant.setManagementFeeRate(request.getManagementFeeRate());
        consultant.setStatus(request.getStatus());
        consultant.setRibIban(request.getRibIban());
        consultant.setRibBic(request.getRibBic());
        consultant.setManager(manager);

        return consultantMapper.toResponse(consultantRepository.save(consultant));
    }

    @Override
    public void delete(Long id) {
        if (!consultantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Consultant", id);
        }
        consultantRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getStats(Long consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", consultantId));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMissions", missionRepository.findByConsultantId(consultantId).size());
        stats.put("activeMissions", missionRepository.findByConsultantIdAndStatus(consultantId,
                com.portage.app.domain.enums.MissionStatus.ACTIVE).size());
        stats.put("totalInvoices", invoiceRepository.findByConsultantId(consultantId).size());
        stats.put("consultantName", consultant.getFirstName() + " " + consultant.getLastName());
        return stats;
    }
}
