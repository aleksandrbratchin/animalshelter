package ru.teamfour.mappers.user;

import jakarta.validation.constraints.NotBlank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.dto.user.UserInfoDto;

@Mapper(componentModel = "spring")
public interface UserInfoMapperMS {

    @Mappings({
            @Mapping(target="firstName", source="user.userInfo.firstName"),
            @Mapping(target="lastName", source="user.userInfo.lastName"),
            @Mapping(target="phoneNumber", source="user.userInfo.phoneNumber"),
            @Mapping(target="nickName", source="user.userInfo.nickName")
    })
    UserInfoDto toUserDto(User user);

    @Mappings({
            @Mapping(source="firstName", target="userInfo.firstName"),
            @Mapping(source="lastName", target="userInfo.lastName"),
            @Mapping(source="phoneNumber", target="userInfo.phoneNumber"),
            @Mapping(source="nickName", target="userInfo.nickName")
    })
    User toUser(UserInfoDto userDto);

}
