package com.portage.app.dto.response;

import com.portage.app.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean enabled;
    private Long consultantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
