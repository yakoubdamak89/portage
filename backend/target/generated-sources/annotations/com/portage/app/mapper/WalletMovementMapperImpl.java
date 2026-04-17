package com.portage.app.mapper;

import com.portage.app.domain.entity.Wallet;
import com.portage.app.domain.entity.WalletMovement;
import com.portage.app.dto.response.WalletMovementResponse;
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
public class WalletMovementMapperImpl implements WalletMovementMapper {

    @Override
    public WalletMovementResponse toResponse(WalletMovement movement) {
        if ( movement == null ) {
            return null;
        }

        WalletMovementResponse.WalletMovementResponseBuilder walletMovementResponse = WalletMovementResponse.builder();

        walletMovementResponse.walletId( movementWalletId( movement ) );
        walletMovementResponse.id( movement.getId() );
        walletMovementResponse.type( movement.getType() );
        walletMovementResponse.amount( movement.getAmount() );
        walletMovementResponse.label( movement.getLabel() );
        walletMovementResponse.referenceId( movement.getReferenceId() );
        walletMovementResponse.referenceType( movement.getReferenceType() );
        walletMovementResponse.date( movement.getDate() );
        walletMovementResponse.balanceAfter( movement.getBalanceAfter() );
        walletMovementResponse.createdAt( movement.getCreatedAt() );

        return walletMovementResponse.build();
    }

    @Override
    public List<WalletMovementResponse> toResponseList(List<WalletMovement> movements) {
        if ( movements == null ) {
            return null;
        }

        List<WalletMovementResponse> list = new ArrayList<WalletMovementResponse>( movements.size() );
        for ( WalletMovement walletMovement : movements ) {
            list.add( toResponse( walletMovement ) );
        }

        return list;
    }

    private Long movementWalletId(WalletMovement walletMovement) {
        if ( walletMovement == null ) {
            return null;
        }
        Wallet wallet = walletMovement.getWallet();
        if ( wallet == null ) {
            return null;
        }
        Long id = wallet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
