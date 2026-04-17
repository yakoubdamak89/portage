package com.portage.app.dto.request;

import com.portage.app.domain.enums.ContractType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConsultantRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank @Email
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String socialSecurityNumber;
    private ContractType contractType;
    private BigDecimal dailyRate;
    private BigDecimal hourlyRate;
    private BigDecimal managementFeeRate;
    private String status;
    private String ribIban;
    private String ribBic;
    private Long managerId;
    private Long userId;
}
