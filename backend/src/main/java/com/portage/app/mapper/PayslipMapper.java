package com.portage.app.mapper;

import com.portage.app.domain.entity.Payslip;
import com.portage.app.dto.response.PayslipResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PayslipMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(payslip.getConsultant().getFirstName() + ' ' + payslip.getConsultant().getLastName())")
    PayslipResponse toResponse(Payslip payslip);

    List<PayslipResponse> toResponseList(List<Payslip> payslips);
}
