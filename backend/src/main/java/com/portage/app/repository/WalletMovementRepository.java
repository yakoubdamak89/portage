package com.portage.app.repository;

import com.portage.app.domain.entity.WalletMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletMovementRepository extends JpaRepository<WalletMovement, Long> {
    List<WalletMovement> findByWalletIdOrderByCreatedAtDesc(Long walletId);
    Page<WalletMovement> findByWalletId(Long walletId, Pageable pageable);
}
