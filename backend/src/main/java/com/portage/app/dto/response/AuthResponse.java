package com.portage.app.dto.response;

import com.portage.app.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Long consultantId;
}
