package com.portage.app.repository;

import com.portage.app.domain.entity.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientContactRepository extends JpaRepository<ClientContact, Long> {
    List<ClientContact> findByClientCompanyId(Long clientCompanyId);
}
