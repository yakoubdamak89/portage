package com.portage.app.dto.response;

import com.portage.app.domain.enums.WalletMovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletMovementResponse {
    private Long id;
    private Long walletId;
    private WalletMovementType type;
    private BigDecimal amount;
    private String label;
    private Long referenceId;
    private String referenceType;
    private LocalDate date;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;
}
