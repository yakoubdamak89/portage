package com.portage.app.dto.request;

import com.portage.app.domain.enums.MissionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MissionRequest {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private Long consultantId;
    @NotNull
    private Long clientCompanyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal dailyRate;
    private BigDecimal estimatedDays;
    private MissionStatus status;
    private String billingType;
}
