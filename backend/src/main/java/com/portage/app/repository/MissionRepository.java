package com.portage.app.repository;

import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByConsultantId(Long consultantId);
    List<Mission> findByClientCompanyId(Long clientCompanyId);
    List<Mission> findByStatus(MissionStatus status);
    List<Mission> findByConsultantIdAndStatus(Long consultantId, MissionStatus status);
}
