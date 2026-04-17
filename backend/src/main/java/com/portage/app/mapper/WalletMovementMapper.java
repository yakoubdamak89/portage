package com.portage.app.mapper;

import com.portage.app.domain.entity.WalletMovement;
import com.portage.app.dto.response.WalletMovementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMovementMapper {

    @Mapping(target = "walletId", source = "wallet.id")
    WalletMovementResponse toResponse(WalletMovement movement);

    List<WalletMovementResponse> toResponseList(List<WalletMovement> movements);
}
