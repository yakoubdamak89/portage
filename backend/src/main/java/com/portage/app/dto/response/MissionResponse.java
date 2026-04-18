package com.portage.app.dto.response;

import com.portage.app.domain.enums.MissionStatus;
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
public class MissionResponse {
    private Long id;
    private String title;
    private String description;
    private Long consultantId;
    private String consultantName;
    private Long clientCompanyId;
    private String clientCompanyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal dailyRate;
    private BigDecimal estimatedDays;
    private MissionStatus status;
    private String billingType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
