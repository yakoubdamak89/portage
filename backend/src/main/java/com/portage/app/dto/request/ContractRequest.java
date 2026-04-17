package com.portage.app.dto.request;

import com.portage.app.domain.enums.ContractStatus;
import com.portage.app.domain.enums.ContractType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractRequest {
    @NotNull
    private ContractType type;
    private ContractStatus status;
    private Long consultantId;
    private Long clientCompanyId;
    private Long missionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filePath;
    private LocalDate signedDate;
}
