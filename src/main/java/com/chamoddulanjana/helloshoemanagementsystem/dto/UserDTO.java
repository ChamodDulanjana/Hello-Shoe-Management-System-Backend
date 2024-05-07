package com.chamoddulanjana.helloshoemanagementsystem.dto;

import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    @Null(message = "User id is auto generated")
    private String userId;

    @NotNull(message = "email cannot be null")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "password cannot be null")
    @Length(min = 8, max = 16, message = "password is too short or too long")
    private String password;

    @NotNull(message = "role cannot be null")
    @Length(min = 4, max = 5, message = "Invalid role")
    private Role role;
}
