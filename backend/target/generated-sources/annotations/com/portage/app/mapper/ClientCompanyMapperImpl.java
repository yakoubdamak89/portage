package com.portage.app.mapper;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.dto.response.ClientCompanyResponse;
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
public class ClientCompanyMapperImpl implements ClientCompanyMapper {

    @Override
    public ClientCompanyResponse toResponse(ClientCompany clientCompany) {
        if ( clientCompany == null ) {
            return null;
        }

        ClientCompanyResponse.ClientCompanyResponseBuilder clientCompanyResponse = ClientCompanyResponse.builder();

        clientCompanyResponse.id( clientCompany.getId() );
        clientCompanyResponse.name( clientCompany.getName() );
        clientCompanyResponse.siret( clientCompany.getSiret() );
        clientCompanyResponse.address( clientCompany.getAddress() );
        clientCompanyResponse.billingAddress( clientCompany.getBillingAddress() );
        clientCompanyResponse.city( clientCompany.getCity() );
        clientCompanyResponse.postalCode( clientCompany.getPostalCode() );
        clientCompanyResponse.country( clientCompany.getCountry() );
        clientCompanyResponse.contactName( clientCompany.getContactName() );
        clientCompanyResponse.contactEmail( clientCompany.getContactEmail() );
        clientCompanyResponse.contactPhone( clientCompany.getContactPhone() );
        clientCompanyResponse.paymentTermsDays( clientCompany.getPaymentTermsDays() );
        clientCompanyResponse.active( clientCompany.isActive() );
        clientCompanyResponse.vatNumber( clientCompany.getVatNumber() );
        clientCompanyResponse.createdAt( clientCompany.getCreatedAt() );
        clientCompanyResponse.updatedAt( clientCompany.getUpdatedAt() );

        return clientCompanyResponse.build();
    }

    @Override
    public List<ClientCompanyResponse> toResponseList(List<ClientCompany> companies) {
        if ( companies == null ) {
            return null;
        }

        List<ClientCompanyResponse> list = new ArrayList<ClientCompanyResponse>( companies.size() );
        for ( ClientCompany clientCompany : companies ) {
            list.add( toResponse( clientCompany ) );
        }

        return list;
    }
}
