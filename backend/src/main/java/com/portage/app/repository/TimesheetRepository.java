package com.portage.app.repository;

import com.portage.app.domain.entity.Timesheet;
import com.portage.app.domain.enums.TimesheetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByConsultantId(Long consultantId);
    List<Timesheet> findByMissionId(Long missionId);
    List<Timesheet> findByStatus(TimesheetStatus status);
    Optional<Timesheet> findByConsultantIdAndMonthAndYear(Long consultantId, int month, int year);
    List<Timesheet> findByConsultantIdAndStatus(Long consultantId, TimesheetStatus status);
}
