package ru.teamfour.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.validators.roleuser.RoleUserValid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserDto {

    @NotNull(message = "ChatId cannot be null")
    private Long chatId;

    @RoleUserValid
    private String role;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phoneNumber;

    @NotBlank
    private String nickName;

}
