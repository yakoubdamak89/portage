package com.portage.app.mapper;

import com.portage.app.domain.entity.ClientCompany;
import com.portage.app.dto.response.ClientCompanyResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientCompanyMapper {
    ClientCompanyResponse toResponse(ClientCompany clientCompany);
    List<ClientCompanyResponse> toResponseList(List<ClientCompany> companies);
}
