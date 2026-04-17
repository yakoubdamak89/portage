package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.entity.Timesheet;
import com.portage.app.dto.response.TimesheetResponse;
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
public class TimesheetMapperImpl implements TimesheetMapper {

    @Override
    public TimesheetResponse toResponse(Timesheet timesheet) {
        if ( timesheet == null ) {
            return null;
        }

        TimesheetResponse.TimesheetResponseBuilder timesheetResponse = TimesheetResponse.builder();

        timesheetResponse.consultantId( timesheetConsultantId( timesheet ) );
        timesheetResponse.missionId( timesheetMissionId( timesheet ) );
        timesheetResponse.missionTitle( timesheetMissionTitle( timesheet ) );
        timesheetResponse.id( timesheet.getId() );
        timesheetResponse.month( timesheet.getMonth() );
        timesheetResponse.year( timesheet.getYear() );
        timesheetResponse.status( timesheet.getStatus() );
        timesheetResponse.totalDays( timesheet.getTotalDays() );
        timesheetResponse.totalHours( timesheet.getTotalHours() );
        timesheetResponse.submittedAt( timesheet.getSubmittedAt() );
        timesheetResponse.validatedAt( timesheet.getValidatedAt() );
        timesheetResponse.comments( timesheet.getComments() );
        timesheetResponse.createdAt( timesheet.getCreatedAt() );
        timesheetResponse.updatedAt( timesheet.getUpdatedAt() );

        timesheetResponse.consultantName( timesheet.getConsultant().getFirstName() + ' ' + timesheet.getConsultant().getLastName() );
        timesheetResponse.validatedByName( timesheet.getValidatedBy() != null ? timesheet.getValidatedBy().getFirstName() + ' ' + timesheet.getValidatedBy().getLastName() : null );

        return timesheetResponse.build();
    }

    @Override
    public List<TimesheetResponse> toResponseList(List<Timesheet> timesheets) {
        if ( timesheets == null ) {
            return null;
        }

        List<TimesheetResponse> list = new ArrayList<TimesheetResponse>( timesheets.size() );
        for ( Timesheet timesheet : timesheets ) {
            list.add( toResponse( timesheet ) );
        }

        return list;
    }

    private Long timesheetConsultantId(Timesheet timesheet) {
        if ( timesheet == null ) {
            return null;
        }
        Consultant consultant = timesheet.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long timesheetMissionId(Timesheet timesheet) {
        if ( timesheet == null ) {
            return null;
        }
        Mission mission = timesheet.getMission();
        if ( mission == null ) {
            return null;
        }
        Long id = mission.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String timesheetMissionTitle(Timesheet timesheet) {
        if ( timesheet == null ) {
            return null;
        }
        Mission mission = timesheet.getMission();
        if ( mission == null ) {
            return null;
        }
        String title = mission.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }
}
