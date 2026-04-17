package com.portage.app.repository;

import com.portage.app.domain.entity.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {
    List<ClientCompany> findByActiveTrue();
    boolean existsBySiret(String siret);
}
