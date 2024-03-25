package ru.teamfour.textcommand.command.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.teamfour.myutils.MessageUtils;

@Component
public abstract class AbstractTextCommand implements TextCommand {
    @Autowired
    protected MessageUtils messageUtils;
}
