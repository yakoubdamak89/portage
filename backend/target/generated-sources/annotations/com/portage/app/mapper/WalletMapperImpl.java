package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.domain.entity.Wallet;
import com.portage.app.dto.response.WalletResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-17T23:32:19+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletResponse toResponse(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletResponse.WalletResponseBuilder walletResponse = WalletResponse.builder();

        walletResponse.consultantId( walletConsultantId( wallet ) );
        walletResponse.id( wallet.getId() );
        walletResponse.balance( wallet.getBalance() );
        walletResponse.totalEarned( wallet.getTotalEarned() );
        walletResponse.totalWithdrawn( wallet.getTotalWithdrawn() );
        walletResponse.updatedAt( wallet.getUpdatedAt() );

        walletResponse.consultantName( wallet.getConsultant().getFirstName() + ' ' + wallet.getConsultant().getLastName() );

        return walletResponse.build();
    }

    private Long walletConsultantId(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }
        Consultant consultant = wallet.getConsultant();
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
