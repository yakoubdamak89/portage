package com.portage.app.mapper;

import com.portage.app.domain.entity.Timesheet;
import com.portage.app.dto.response.TimesheetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimesheetMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(timesheet.getConsultant().getFirstName() + ' ' + timesheet.getConsultant().getLastName())")
    @Mapping(target = "missionId", source = "mission.id")
    @Mapping(target = "missionTitle", source = "mission.title")
    @Mapping(target = "validatedByName", expression = "java(timesheet.getValidatedBy() != null ? timesheet.getValidatedBy().getFirstName() + ' ' + timesheet.getValidatedBy().getLastName() : null)")
    TimesheetResponse toResponse(Timesheet timesheet);

    List<TimesheetResponse> toResponseList(List<Timesheet> timesheets);
}
