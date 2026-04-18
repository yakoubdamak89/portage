package com.portage.app.mapper;

import com.portage.app.domain.entity.TimesheetEntry;
import com.portage.app.dto.response.TimesheetEntryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimesheetEntryMapper {

    @Mapping(target = "timesheetId", source = "timesheet.id")
    TimesheetEntryResponse toResponse(TimesheetEntry entry);

    List<TimesheetEntryResponse> toResponseList(List<TimesheetEntry> entries);
}
