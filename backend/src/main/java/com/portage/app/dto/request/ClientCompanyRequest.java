package com.portage.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientCompanyRequest {
    @NotBlank
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
    private boolean active = true;
    private String vatNumber;
}
