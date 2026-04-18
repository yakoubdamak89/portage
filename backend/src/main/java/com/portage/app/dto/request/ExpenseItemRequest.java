package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseItemRequest {
    @NotNull
    private Long expenseReportId;
    @NotNull
    private LocalDate date;
    private String category;
    private String description;
    private BigDecimal amount;
    private BigDecimal vatAmount;
    private String receiptPath;
}
