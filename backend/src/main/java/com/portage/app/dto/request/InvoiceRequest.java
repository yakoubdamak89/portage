package com.portage.app.dto.request;

import com.portage.app.domain.enums.InvoiceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceRequest {
    private String invoiceNumber;
    @NotNull
    private Long consultantId;
    @NotNull
    private Long clientCompanyId;
    private Long missionId;
    private Long timesheetId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalHT;
    private BigDecimal vatRate;
    private BigDecimal totalTTC;
    private InvoiceStatus status;
}
