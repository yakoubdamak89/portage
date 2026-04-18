package com.portage.app.mapper;

import com.portage.app.domain.entity.Consultant;
import com.portage.app.dto.response.ConsultantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultantMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", expression = "java(consultant.getManager() != null ? consultant.getManager().getFirstName() + ' ' + consultant.getManager().getLastName() : null)")
    ConsultantResponse toResponse(Consultant consultant);

    List<ConsultantResponse> toResponseList(List<Consultant> consultants);
}
