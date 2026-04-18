package com.portage.app.mapper;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Contract;
import com.portage.app.domain.entity.Mission;
import com.portage.app.dto.response.ContractResponse;
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
public class ContractMapperImpl implements ContractMapper {

    @Override
    public ContractResponse toResponse(Contract contract) {
        if ( contract == null ) {
            return null;
        }

        ContractResponse.ContractResponseBuilder contractResponse = ContractResponse.builder();

        contractResponse.consultantId( contractConsultantId( contract ) );
        contractResponse.clientCompanyId( contractClientCompanyId( contract ) );
        contractResponse.clientCompanyName( contractClientCompanyName( contract ) );
        contractResponse.missionId( contractMissionId( contract ) );
        contractResponse.missionTitle( contractMissionTitle( contract ) );
        contractResponse.id( contract.getId() );
        contractResponse.type( contract.getType() );
        contractResponse.status( contract.getStatus() );
        contractResponse.startDate( contract.getStartDate() );
        contractResponse.endDate( contract.getEndDate() );
        contractResponse.filePath( contract.getFilePath() );
        contractResponse.signedDate( contract.getSignedDate() );
        contractResponse.createdAt( contract.getCreatedAt() );
        contractResponse.updatedAt( contract.getUpdatedAt() );

        contractResponse.consultantName( contract.getConsultant() != null ? contract.getConsultant().getFirstName() + ' ' + contract.getConsultant().getLastName() : null );

        return contractResponse.build();
    }

    @Override
    public List<ContractResponse> toResponseList(List<Contract> contracts) {
        if ( contracts == null ) {
            return null;
        }

        List<ContractResponse> list = new ArrayList<ContractResponse>( contracts.size() );
        for ( Contract contract : contracts ) {
            list.add( toResponse( contract ) );
        }

        return list;
    }

    private Long contractConsultantId(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        Consultant consultant = contract.getConsultant();
        if ( consultant == null ) {
            return null;
        }
        Long id = consultant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long contractClientCompanyId(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        ClientCompany clientCompany = contract.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        Long id = clientCompany.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String contractClientCompanyName(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        ClientCompany clientCompany = contract.getClientCompany();
        if ( clientCompany == null ) {
            return null;
        }
        String name = clientCompany.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long contractMissionId(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        Mission mission = contract.getMission();
        if ( mission == null ) {
            return null;
        }
        Long id = mission.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String contractMissionTitle(Contract contract) {
        if ( contract == null ) {
            return null;
        }
        Mission mission = contract.getMission();
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
