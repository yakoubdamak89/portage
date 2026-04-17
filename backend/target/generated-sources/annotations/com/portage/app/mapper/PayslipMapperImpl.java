package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Payslip;
import com.portage.app.dto.response.PayslipResponse;
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
public class PayslipMapperImpl implements PayslipMapper {

    @Override
    public PayslipResponse toResponse(Payslip payslip) {
        if ( payslip == null ) {
            return null;
        }

        PayslipResponse.PayslipResponseBuilder payslipResponse = PayslipResponse.builder();

        payslipResponse.consultantId( payslipConsultantId( payslip ) );
        payslipResponse.id( payslip.getId() );
        payslipResponse.month( payslip.getMonth() );
        payslipResponse.year( payslip.getYear() );
        payslipResponse.grossSalary( payslip.getGrossSalary() );
        payslipResponse.netSalary( payslip.getNetSalary() );
        payslipResponse.employerCharges( payslip.getEmployerCharges() );
        payslipResponse.employeeCharges( payslip.getEmployeeCharges() );
        payslipResponse.expenses( payslip.getExpenses() );
        payslipResponse.filePath( payslip.getFilePath() );
        payslipResponse.generatedAt( payslip.getGeneratedAt() );
        payslipResponse.createdAt( payslip.getCreatedAt() );

        payslipResponse.consultantName( payslip.getConsultant().getFirstName() + ' ' + payslip.getConsultant().getLastName() );

        return payslipResponse.build();
    }

    @Override
    public List<PayslipResponse> toResponseList(List<Payslip> payslips) {
        if ( payslips == null ) {
            return null;
        }

        List<PayslipResponse> list = new ArrayList<PayslipResponse>( payslips.size() );
        for ( Payslip payslip : payslips ) {
            list.add( toResponse( payslip ) );
        }

        return list;
    }

    private Long payslipConsultantId(Payslip payslip) {
        if ( payslip == null ) {
            return null;
        }
        Consultant consultant = payslip.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
