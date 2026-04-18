package com.portage.app.mapper;

import com.portage.app.domain.entity.User;
import com.portage.app.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "consultantId", expression = "java(user.getConsultant() != null ? user.getConsultant().getId() : null)")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
