package com.portage.app.repository;

import com.portage.app.domain.entity.ExpenseReport;
import com.portage.app.domain.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseReportRepository extends JpaRepository<ExpenseReport, Long> {
    List<ExpenseReport> findByConsultantId(Long consultantId);
    List<ExpenseReport> findByMissionId(Long missionId);
    List<ExpenseReport> findByStatus(ExpenseStatus status);
    List<ExpenseReport> findByConsultantIdAndMonthAndYear(Long consultantId, int month, int year);
}
