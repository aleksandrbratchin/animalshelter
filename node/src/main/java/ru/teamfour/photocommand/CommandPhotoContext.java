package ru.teamfour.photocommand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.teamfour.dao.entity.user.User;
import ru.teamfour.photocommand.api.PhotoCommand;
import transfer.TransferByteObject;

/**
 * Параметры необходимые для выполнения команд по обработке фото {@link PhotoCommand}
 */
@Getter
@Setter
@AllArgsConstructor
public class CommandPhotoContext {

    private TransferByteObject transferByteObject;

    private User user;

}
