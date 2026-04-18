package com.portage.app.mapper;

import com.portage.app.domain.entity.ExpenseReport;
import com.portage.app.dto.response.ExpenseReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseReportMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(report.getConsultant().getFirstName() + ' ' + report.getConsultant().getLastName())")
    @Mapping(target = "missionId", source = "mission.id")
    @Mapping(target = "missionTitle", source = "mission.title")
    @Mapping(target = "validatedByName", expression = "java(report.getValidatedBy() != null ? report.getValidatedBy().getFirstName() + ' ' + report.getValidatedBy().getLastName() : null)")
    ExpenseReportResponse toResponse(ExpenseReport report);

    List<ExpenseReportResponse> toResponseList(List<ExpenseReport> reports);
}
