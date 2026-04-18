package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequest {
    @NotNull
    private Long invoiceId;
    @NotNull
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String reference;
    private String notes;
}
