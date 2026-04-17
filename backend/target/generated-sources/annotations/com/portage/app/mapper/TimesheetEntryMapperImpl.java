package com.portage.app.mapper;

import com.portage.app.domain.entity.Timesheet;
import com.portage.app.domain.entity.TimesheetEntry;
import com.portage.app.dto.response.TimesheetEntryResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T23:32:19+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class TimesheetEntryMapperImpl implements TimesheetEntryMapper {

    @Override
    public TimesheetEntryResponse toResponse(TimesheetEntry entry) {
        if ( entry == null ) {
            return null;
        }

        TimesheetEntryResponse.TimesheetEntryResponseBuilder timesheetEntryResponse = TimesheetEntryResponse.builder();

        timesheetEntryResponse.timesheetId( entryTimesheetId( entry ) );
        timesheetEntryResponse.id( entry.getId() );
        timesheetEntryResponse.date( entry.getDate() );
        timesheetEntryResponse.hoursWorked( entry.getHoursWorked() );
        timesheetEntryResponse.daysWorked( entry.getDaysWorked() );
        timesheetEntryResponse.entryType( entry.getEntryType() );
        timesheetEntryResponse.comment( entry.getComment() );
        timesheetEntryResponse.createdAt( entry.getCreatedAt() );

        return timesheetEntryResponse.build();
    }

    @Override
    public List<TimesheetEntryResponse> toResponseList(List<TimesheetEntry> entries) {
        if ( entries == null ) {
            return null;
        }

        List<TimesheetEntryResponse> list = new ArrayList<TimesheetEntryResponse>( entries.size() );
        for ( TimesheetEntry timesheetEntry : entries ) {
            list.add( toResponse( timesheetEntry ) );
        }

        return list;
    }

    private Long entryTimesheetId(TimesheetEntry timesheetEntry) {
        if ( timesheetEntry == null ) {
            return null;
        }
        Timesheet timesheet = timesheetEntry.getTimesheet();
        if ( timesheet == null ) {
            return null;
        }
        Long id = timesheet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
