package com.portage.app.dto.response;

import com.portage.app.domain.enums.InvoiceStatus;
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
public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private Long consultantId;
    private String consultantName;
    private Long clientCompanyId;
    private String clientCompanyName;
    private Long missionId;
    private String missionTitle;
    private Long timesheetId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal totalHT;
    private BigDecimal vatRate;
    private BigDecimal totalTTC;
    private InvoiceStatus status;
    private LocalDateTime paidAt;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
