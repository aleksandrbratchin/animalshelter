package ru.teamfour.photocommand.api;

import ru.teamfour.photocommand.CommandPhotoContext;
import ru.teamfour.textcommand.command.api.MessageToTelegram;
import ru.teamfour.textcommand.command.api.ParentCommand;

/***
 * Основной интерфейс для команд обрабатывающих фото
 */
public interface PhotoCommand extends ParentCommand<CommandPhotoContext, MessageToTelegram> {
    /***
     * Принимает фото в качестве
     * @param commandContext данные для сохранения фото {@link CommandPhotoContext}
     * @return ответ в телеграм {@link  MessageToTelegram}
     */
    @Override
    MessageToTelegram execute(CommandPhotoContext commandContext);
}