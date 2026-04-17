package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.User;
import com.portage.app.dto.response.ConsultantResponse;
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
public class ConsultantMapperImpl implements ConsultantMapper {

    @Override
    public ConsultantResponse toResponse(Consultant consultant) {
        if ( consultant == null ) {
            return null;
        }

        ConsultantResponse.ConsultantResponseBuilder consultantResponse = ConsultantResponse.builder();

        consultantResponse.userId( consultantUserId( consultant ) );
        consultantResponse.managerId( consultantManagerId( consultant ) );
        consultantResponse.id( consultant.getId() );
        consultantResponse.firstName( consultant.getFirstName() );
        consultantResponse.lastName( consultant.getLastName() );
        consultantResponse.email( consultant.getEmail() );
        consultantResponse.phone( consultant.getPhone() );
        consultantResponse.address( consultant.getAddress() );
        consultantResponse.dateOfBirth( consultant.getDateOfBirth() );
        consultantResponse.contractType( consultant.getContractType() );
        consultantResponse.dailyRate( consultant.getDailyRate() );
        consultantResponse.hourlyRate( consultant.getHourlyRate() );
        consultantResponse.managementFeeRate( consultant.getManagementFeeRate() );
        consultantResponse.status( consultant.getStatus() );
        consultantResponse.ribIban( consultant.getRibIban() );
        consultantResponse.ribBic( consultant.getRibBic() );
        consultantResponse.createdAt( consultant.getCreatedAt() );
        consultantResponse.updatedAt( consultant.getUpdatedAt() );

        consultantResponse.managerName( consultant.getManager() != null ? consultant.getManager().getFirstName() + ' ' + consultant.getManager().getLastName() : null );

        return consultantResponse.build();
    }

    @Override
    public List<ConsultantResponse> toResponseList(List<Consultant> consultants) {
        if ( consultants == null ) {
            return null;
        }

        List<ConsultantResponse> list = new ArrayList<ConsultantResponse>( consultants.size() );
        for ( Consultant consultant : consultants ) {
            list.add( toResponse( consultant ) );
        }

        return list;
    }

    private Long consultantUserId(Consultant consultant) {
        if ( consultant == null ) {
            return null;
        }
        User user = consultant.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long consultantManagerId(Consultant consultant) {
        if ( consultant == null ) {
            return null;
        }
        User manager = consultant.getManager();
        if ( manager == null ) {
            return null;
        }
        Long id = manager.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
