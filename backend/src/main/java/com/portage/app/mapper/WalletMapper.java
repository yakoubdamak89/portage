package com.portage.app.mapper;

import com.portage.app.domain.entity.Wallet;
import com.portage.app.dto.response.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(wallet.getConsultant().getFirstName() + ' ' + wallet.getConsultant().getLastName())")
    WalletResponse toResponse(Wallet wallet);
}
