package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayslipRequest {
    @NotNull
    private Long consultantId;
    @NotNull
    private Integer month;
    @NotNull
    private Integer year;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private BigDecimal employerCharges;
    private BigDecimal employeeCharges;
    private BigDecimal expenses;
    private String filePath;
}
