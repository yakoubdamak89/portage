package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TimesheetEntryRequest {
    @NotNull
    private Long timesheetId;
    @NotNull
    private LocalDate date;
    private BigDecimal hoursWorked;
    private BigDecimal daysWorked;
    private String entryType;
    private String comment;
}
