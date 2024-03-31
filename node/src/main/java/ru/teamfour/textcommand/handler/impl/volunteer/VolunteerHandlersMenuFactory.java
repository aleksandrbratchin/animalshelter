package ru.teamfour.textcommand.handler.impl.volunteer;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.user.RoleUser;
import ru.teamfour.textcommand.handler.annotations.RoleUserQualifier;
import ru.teamfour.textcommand.handler.api.AbstractHandlersStateFactory;
import ru.teamfour.textcommand.handler.api.HandlersState;

import java.util.List;

@Component
public class VolunteerHandlersMenuFactory extends AbstractHandlersStateFactory {
    public VolunteerHandlersMenuFactory(@RoleUserQualifier(RoleUser.VOLUNTEER) List<HandlersState> handlersList) {
        super(handlersList);
    }
    @Override
    public boolean isRole(RoleUser roleUser){
        return roleUser == RoleUser.VOLUNTEER;
    }
}
