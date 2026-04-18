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
public class TimesheetEntryResponse {
    private Long id;
    private Long timesheetId;
    private LocalDate date;
    private BigDecimal hoursWorked;
    private BigDecimal daysWorked;
    private String entryType;
    private String comment;
    private LocalDateTime createdAt;
}
