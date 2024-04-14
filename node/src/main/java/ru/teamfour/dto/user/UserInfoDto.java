package ru.teamfour.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.validators.roleuser.ValidRoleUser;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {

    @NotNull(message = "UUID cannot be null")
    private UUID id;

    @NotNull(message = "ChatId cannot be null")
    private Long chatId;

    @ValidRoleUser
    private String role;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phoneNumber;

    @NotBlank
    private String nickName;

}
