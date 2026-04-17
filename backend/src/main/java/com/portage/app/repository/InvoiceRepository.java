package com.portage.app.repository;

import com.portage.app.domain.entity.Invoice;
import com.portage.app.domain.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByConsultantId(Long consultantId);
    List<Invoice> findByClientCompanyId(Long clientCompanyId);
    List<Invoice> findByMissionId(Long missionId);
    List<Invoice> findByStatus(InvoiceStatus status);
    Optional<Invoice> findByTimesheetId(Long timesheetId);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    long countByStatus(InvoiceStatus status);
}
