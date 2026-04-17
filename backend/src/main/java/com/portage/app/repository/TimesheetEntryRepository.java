package com.portage.app.repository;

import com.portage.app.domain.entity.TimesheetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry, Long> {
    List<TimesheetEntry> findByTimesheetId(Long timesheetId);
    void deleteByTimesheetId(Long timesheetId);
}
