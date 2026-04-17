package com.portage.app.mapper;

import com.portage.app.domain.entity.Contract;
import com.portage.app.dto.response.ContractResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(contract.getConsultant() != null ? contract.getConsultant().getFirstName() + ' ' + contract.getConsultant().getLastName() : null)")
    @Mapping(target = "clientCompanyId", source = "clientCompany.id")
    @Mapping(target = "clientCompanyName", source = "clientCompany.name")
    @Mapping(target = "missionId", source = "mission.id")
    @Mapping(target = "missionTitle", source = "mission.title")
    ContractResponse toResponse(Contract contract);

    List<ContractResponse> toResponseList(List<Contract> contracts);
}
