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
public class PayslipResponse {
    private Long id;
    private Long consultantId;
    private String consultantName;
    private Integer month;
    private Integer year;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private BigDecimal employerCharges;
    private BigDecimal employeeCharges;
    private BigDecimal expenses;
    private String filePath;
    private LocalDateTime generatedAt;
    private LocalDateTime createdAt;
}
