package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TimesheetRequest {
    @NotNull
    private Long consultantId;
    private Long missionId;
    @NotNull
    private Integer month;
    @NotNull
    private Integer year;
    private BigDecimal totalDays;
    private BigDecimal totalHours;
    private String comments;
}
