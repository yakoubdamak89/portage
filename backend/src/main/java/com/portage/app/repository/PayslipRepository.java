package com.portage.app.repository;

import com.portage.app.domain.entity.Payslip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    List<Payslip> findByConsultantId(Long consultantId);
    Optional<Payslip> findByConsultantIdAndMonthAndYear(Long consultantId, int month, int year);
}
