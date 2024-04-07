package ru.teamfour.textcommand.command.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import transfer.TransferByteObject;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MessageToTelegram {
    private List<SendMessage> sendMessages;
    private List<TransferByteObject> transferByteObjects;
}
