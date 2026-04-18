package com.portage.app.mapper;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Mission;
import com.portage.app.dto.response.MissionResponse;
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
public class MissionMapperImpl implements MissionMapper {

    @Override
    public MissionResponse toResponse(Mission mission) {
        if ( mission == null ) {
            return null;
        }

        MissionResponse.MissionResponseBuilder missionResponse = MissionResponse.builder();

        missionResponse.consultantId( missionConsultantId( mission ) );
        missionResponse.clientCompanyId( missionClientCompanyId( mission ) );
        missionResponse.clientCompanyName( missionClientCompanyName( mission ) );
        missionResponse.id( mission.getId() );
        missionResponse.title( mission.getTitle() );
        missionResponse.description( mission.getDescription() );
        missionResponse.startDate( mission.getStartDate() );
        missionResponse.endDate( mission.getEndDate() );
        missionResponse.dailyRate( mission.getDailyRate() );
        missionResponse.estimatedDays( mission.getEstimatedDays() );
        missionResponse.status( mission.getStatus() );
        missionResponse.billingType( mission.getBillingType() );
        missionResponse.createdAt( mission.getCreatedAt() );
        missionResponse.updatedAt( mission.getUpdatedAt() );

        missionResponse.consultantName( mission.getConsultant().getFirstName() + ' ' + mission.getConsultant().getLastName() );

        return missionResponse.build();
    }

    @Override
    public List<MissionResponse> toResponseList(List<Mission> missions) {
        if ( missions == null ) {
            return null;
        }

        List<MissionResponse> list = new ArrayList<MissionResponse>( missions.size() );
        for ( Mission mission : missions ) {
            list.add( toResponse( mission ) );
        }

        return list;
    }

    private Long missionConsultantId(Mission mission) {
        if ( mission == null ) {
            return null;
        }
        Consultant consultant = mission.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long missionClientCompanyId(Mission mission) {
        if ( mission == null ) {
            return null;
        }
        ClientCompany clientCompany = mission.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        Long id = clientCompany.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String missionClientCompanyName(Mission mission) {
        if ( mission == null ) {
            return null;
        }
        ClientCompany clientCompany = mission.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        String name = clientCompany.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
