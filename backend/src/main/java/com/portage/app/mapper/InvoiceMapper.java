package com.portage.app.mapper;

import com.portage.app.domain.entity.Invoice;
import com.portage.app.dto.response.InvoiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(invoice.getConsultant().getFirstName() + ' ' + invoice.getConsultant().getLastName())")
    @Mapping(target = "clientCompanyId", source = "clientCompany.id")
    @Mapping(target = "clientCompanyName", source = "clientCompany.name")
    @Mapping(target = "missionId", source = "mission.id")
    @Mapping(target = "missionTitle", source = "mission.title")
    @Mapping(target = "timesheetId", source = "timesheet.id")
    InvoiceResponse toResponse(Invoice invoice);

    List<InvoiceResponse> toResponseList(List<Invoice> invoices);
}
