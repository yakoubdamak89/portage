package com.portage.app.repository;

import com.portage.app.domain.entity.Contract;
import com.portage.app.domain.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByConsultantId(Long consultantId);
    List<Contract> findByClientCompanyId(Long clientCompanyId);
    List<Contract> findByMissionId(Long missionId);
    List<Contract> findByStatus(ContractStatus status);
}
