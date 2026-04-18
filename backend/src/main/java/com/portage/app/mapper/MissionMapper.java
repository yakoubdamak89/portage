package com.portage.app.mapper;

import com.portage.app.domain.entity.Mission;
import com.portage.app.dto.response.MissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MissionMapper {

    @Mapping(target = "consultantId", source = "consultant.id")
    @Mapping(target = "consultantName", expression = "java(mission.getConsultant().getFirstName() + ' ' + mission.getConsultant().getLastName())")
    @Mapping(target = "clientCompanyId", source = "clientCompany.id")
    @Mapping(target = "clientCompanyName", source = "clientCompany.name")
    MissionResponse toResponse(Mission mission);

    List<MissionResponse> toResponseList(List<Mission> missions);
}
