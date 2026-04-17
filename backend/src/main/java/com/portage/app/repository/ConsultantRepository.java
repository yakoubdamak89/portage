package com.portage.app.repository;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
    Optional<Consultant> findByUserId(Long userId);
    Optional<Consultant> findByEmail(String email);
    List<Consultant> findByManager(User manager);
    boolean existsByEmail(String email);
}
