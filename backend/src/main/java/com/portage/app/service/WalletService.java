package com.portage.app.service;

import com.portage.app.domain.enums.WalletMovementType;
import com.portage.app.dto.response.WalletMovementResponse;
import com.portage.app.dto.response.WalletResponse;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    WalletResponse getWallet(Long consultantId);
    WalletResponse addMovement(Long consultantId, WalletMovementType type, BigDecimal amount, String label, Long referenceId, String referenceType);
    List<WalletMovementResponse> getHistory(Long consultantId);
    BigDecimal calculateBalance(Long consultantId);
}
