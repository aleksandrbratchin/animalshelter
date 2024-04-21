package ru.teamfour.textcommand.command.impl.volunteer.volunteerchat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.teamfour.textcommand.command.CommandContext;
import ru.teamfour.textcommand.command.impl.client.StartVolunteerCommand;
import ru.teamfour.textcommand.command.api.AbstractCommand;
import ru.teamfour.textcommand.command.api.MessageToTelegram;

@Component
public class BackToVolunteerMainMenuCommand extends AbstractCommand {
    @Value("${buttonName.backButton}")
    private String buttonName;

    private final StartVolunteerCommand command;

    public BackToVolunteerMainMenuCommand(StartVolunteerCommand command) {
        this.command = command;
    }

    @Override
    public boolean isCommand(String message) {
        return message.equals(buttonName);
    }

    @Override
    public MessageToTelegram execute(CommandContext commandContext) {
        return command.execute(commandContext);
    }

}
