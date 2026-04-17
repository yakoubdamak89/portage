package com.portage.app.dto.response;

import com.portage.app.domain.enums.ContractStatus;
import com.portage.app.domain.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {
    private Long id;
    private ContractType type;
    private ContractStatus status;
    private Long consultantId;
    private String consultantName;
    private Long clientCompanyId;
    private String clientCompanyName;
    private Long missionId;
    private String missionTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String filePath;
    private LocalDate signedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
