package com.portage.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExpenseReportRequest {
    @NotNull
    private Long consultantId;
    private Long missionId;
    @NotNull
    private Integer month;
    @NotNull
    private Integer year;
}
