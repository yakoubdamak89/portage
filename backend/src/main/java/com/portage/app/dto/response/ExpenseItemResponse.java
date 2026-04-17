package com.portage.app.dto.response;

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
public class ExpenseItemResponse {
    private Long id;
    private Long expenseReportId;
    private LocalDate date;
    private String category;
    private String description;
    private BigDecimal amount;
    private BigDecimal vatAmount;
    private String receiptPath;
    private LocalDateTime createdAt;
}
