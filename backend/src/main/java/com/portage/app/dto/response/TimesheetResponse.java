package com.portage.app.dto.response;

import com.portage.app.domain.enums.TimesheetStatus;
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
public class TimesheetResponse {
    private Long id;
    private Long consultantId;
    private String consultantName;
    private Long missionId;
    private String missionTitle;
    private Integer month;
    private Integer year;
    private TimesheetStatus status;
    private BigDecimal totalDays;
    private BigDecimal totalHours;
    private LocalDateTime submittedAt;
    private LocalDateTime validatedAt;
    private String validatedByName;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
