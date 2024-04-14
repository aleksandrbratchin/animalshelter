package mappers.user;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.dao.entity.user.UserInfo;
import ru.teamfour.dto.user.UserInfoDto;

@Component
public class UserInfoMapper {

    public static UserInfoDto toUserDto(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .role(user.getRole().name())
                .chatId(user.getChatId())
                .firstName(user.getUserInfo().getFirstName())
                .lastName(user.getUserInfo().getLastName())
                .nickName(user.getUserInfo().getNickName())
                .phoneNumber(user.getUserInfo().getPhoneNumber())
                .build();
    }

    public static User toUser(UserInfoDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .role(RoleUser.valueOf(userDto.getRole()))
                .chatId(userDto.getChatId())
                .userInfo(
                        UserInfo.builder()
                                .phoneNumber(userDto.getPhoneNumber())
                                .nickName(userDto.getNickName())
                                .lastName(userDto.getLastName())
                                .firstName(userDto.getFirstName())
                                .build()
                )
                .build();
    }

}
