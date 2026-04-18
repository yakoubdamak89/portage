package com.portage.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {
    private Long id;
    private Long consultantId;
    private String consultantName;
    private BigDecimal balance;
    private BigDecimal totalEarned;
    private BigDecimal totalWithdrawn;
    private LocalDateTime updatedAt;
}
