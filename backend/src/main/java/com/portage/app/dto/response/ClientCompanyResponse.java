package com.portage.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientCompanyResponse {
    private Long id;
    private String name;
    private String siret;
    private String address;
    private String billingAddress;
    private String city;
    private String postalCode;
    private String country;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private Integer paymentTermsDays;
    private boolean active;
    private String vatNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
