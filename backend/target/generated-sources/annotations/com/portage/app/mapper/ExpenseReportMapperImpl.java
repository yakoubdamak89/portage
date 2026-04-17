package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.ExpenseReport;
import com.portage.app.domain.entity.Mission;
import com.portage.app.dto.response.ExpenseReportResponse;
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
public class ExpenseReportMapperImpl implements ExpenseReportMapper {

    @Override
    public ExpenseReportResponse toResponse(ExpenseReport report) {
        if ( report == null ) {
            return null;
        }

        ExpenseReportResponse.ExpenseReportResponseBuilder expenseReportResponse = ExpenseReportResponse.builder();

        expenseReportResponse.consultantId( reportConsultantId( report ) );
        expenseReportResponse.missionId( reportMissionId( report ) );
        expenseReportResponse.missionTitle( reportMissionTitle( report ) );
        expenseReportResponse.id( report.getId() );
        expenseReportResponse.month( report.getMonth() );
        expenseReportResponse.year( report.getYear() );
        expenseReportResponse.status( report.getStatus() );
        expenseReportResponse.totalAmount( report.getTotalAmount() );
        expenseReportResponse.submittedAt( report.getSubmittedAt() );
        expenseReportResponse.validatedAt( report.getValidatedAt() );
        expenseReportResponse.createdAt( report.getCreatedAt() );
        expenseReportResponse.updatedAt( report.getUpdatedAt() );

        expenseReportResponse.consultantName( report.getConsultant().getFirstName() + ' ' + report.getConsultant().getLastName() );
        expenseReportResponse.validatedByName( report.getValidatedBy() != null ? report.getValidatedBy().getFirstName() + ' ' + report.getValidatedBy().getLastName() : null );

        return expenseReportResponse.build();
    }

    @Override
    public List<ExpenseReportResponse> toResponseList(List<ExpenseReport> reports) {
        if ( reports == null ) {
            return null;
        }

        List<ExpenseReportResponse> list = new ArrayList<ExpenseReportResponse>( reports.size() );
        for ( ExpenseReport expenseReport : reports ) {
            list.add( toResponse( expenseReport ) );
        }

        return list;
    }

    private Long reportConsultantId(ExpenseReport expenseReport) {
        if ( expenseReport == null ) {
            return null;
        }
        Consultant consultant = expenseReport.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long reportMissionId(ExpenseReport expenseReport) {
        if ( expenseReport == null ) {
            return null;
        }
        Mission mission = expenseReport.getMission();
        if ( mission == null ) {
            return null;
        }
        Long id = mission.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String reportMissionTitle(ExpenseReport expenseReport) {
        if ( expenseReport == null ) {
            return null;
        }
        Mission mission = expenseReport.getMission();
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
