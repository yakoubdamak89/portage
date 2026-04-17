package com.portage.app.mapper;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Invoice;
import com.portage.app.domain.entity.Mission;
import com.portage.app.domain.entity.Timesheet;
import com.portage.app.dto.response.InvoiceResponse;
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
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public InvoiceResponse toResponse(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceResponse.InvoiceResponseBuilder invoiceResponse = InvoiceResponse.builder();

        invoiceResponse.consultantId( invoiceConsultantId( invoice ) );
        invoiceResponse.clientCompanyId( invoiceClientCompanyId( invoice ) );
        invoiceResponse.clientCompanyName( invoiceClientCompanyName( invoice ) );
        invoiceResponse.missionId( invoiceMissionId( invoice ) );
        invoiceResponse.missionTitle( invoiceMissionTitle( invoice ) );
        invoiceResponse.timesheetId( invoiceTimesheetId( invoice ) );
        invoiceResponse.id( invoice.getId() );
        invoiceResponse.invoiceNumber( invoice.getInvoiceNumber() );
        invoiceResponse.issueDate( invoice.getIssueDate() );
        invoiceResponse.dueDate( invoice.getDueDate() );
        invoiceResponse.totalHT( invoice.getTotalHT() );
        invoiceResponse.vatRate( invoice.getVatRate() );
        invoiceResponse.totalTTC( invoice.getTotalTTC() );
        invoiceResponse.status( invoice.getStatus() );
        invoiceResponse.paidAt( invoice.getPaidAt() );
        invoiceResponse.filePath( invoice.getFilePath() );
        invoiceResponse.createdAt( invoice.getCreatedAt() );
        invoiceResponse.updatedAt( invoice.getUpdatedAt() );

        invoiceResponse.consultantName( invoice.getConsultant().getFirstName() + ' ' + invoice.getConsultant().getLastName() );

        return invoiceResponse.build();
    }

    @Override
    public List<InvoiceResponse> toResponseList(List<Invoice> invoices) {
        if ( invoices == null ) {
            return null;
        }

        List<InvoiceResponse> list = new ArrayList<InvoiceResponse>( invoices.size() );
        for ( Invoice invoice : invoices ) {
            list.add( toResponse( invoice ) );
        }

        return list;
    }

    private Long invoiceConsultantId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Consultant consultant = invoice.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long invoiceClientCompanyId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        ClientCompany clientCompany = invoice.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        Long id = clientCompany.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String invoiceClientCompanyName(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        ClientCompany clientCompany = invoice.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        String name = clientCompany.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long invoiceMissionId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Mission mission = invoice.getMission();
        if ( mission == null ) {
            return null;
        }
        Long id = mission.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String invoiceMissionTitle(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Mission mission = invoice.getMission();
        if ( mission == null ) {
            return null;
        }
        String title = mission.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private Long invoiceTimesheetId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Timesheet timesheet = invoice.getTimesheet();
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
