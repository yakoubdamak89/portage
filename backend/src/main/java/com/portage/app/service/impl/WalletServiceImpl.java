package com.portage.app.service.impl;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Wallet;
import com.portage.app.domain.entity.WalletMovement;
import com.portage.app.domain.enums.WalletMovementType;
import com.portage.app.dto.response.WalletMovementResponse;
import com.portage.app.dto.response.WalletResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.WalletMapper;
import com.portage.app.mapper.WalletMovementMapper;
import com.portage.app.repository.ConsultantRepository;
import com.portage.app.repository.WalletMovementRepository;
import com.portage.app.repository.WalletRepository;
import com.portage.app.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMovementRepository walletMovementRepository;
    private final ConsultantRepository consultantRepository;
    private final WalletMapper walletMapper;
    private final WalletMovementMapper movementMapper;

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getWallet(Long consultantId) {
        Wallet wallet = walletRepository.findByConsultantId(consultantId)
                .orElseGet(() -> createWallet(consultantId));
        return walletMapper.toResponse(wallet);
    }

    @Override
    public WalletResponse addMovement(Long consultantId, WalletMovementType type, BigDecimal amount,
                                       String label, Long referenceId, String referenceType) {
        Wallet wallet = walletRepository.findByConsultantId(consultantId)
                .orElseGet(() -> createWallet(consultantId));

        if (type == WalletMovementType.CREDIT) {
            wallet.setBalance(wallet.getBalance().add(amount));
            wallet.setTotalEarned(wallet.getTotalEarned().add(amount));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(amount));
            wallet.setTotalWithdrawn(wallet.getTotalWithdrawn().add(amount));
        }

        wallet = walletRepository.save(wallet);

        WalletMovement movement = WalletMovement.builder()
                .wallet(wallet)
                .type(type)
                .amount(amount)
                .label(label)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .date(LocalDate.now())
                .balanceAfter(wallet.getBalance())
                .build();

        walletMovementRepository.save(movement);
        return walletMapper.toResponse(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletMovementResponse> getHistory(Long consultantId) {
        Wallet wallet = walletRepository.findByConsultantId(consultantId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for consultant: " + consultantId));
        return movementMapper.toResponseList(
                walletMovementRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateBalance(Long consultantId) {
        return walletRepository.findByConsultantId(consultantId)
                .map(Wallet::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    private Wallet createWallet(Long consultantId) {
        Consultant consultant = consultantRepository.findById(consultantId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultant", consultantId));
        Wallet wallet = Wallet.builder()
                .consultant(consultant)
                .balance(BigDecimal.ZERO)
                .totalEarned(BigDecimal.ZERO)
                .totalWithdrawn(BigDecimal.ZERO)
                .build();
        return walletRepository.save(wallet);
    }
}
