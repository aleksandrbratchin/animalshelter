package ru.teamfour.textcommand.handler.annotations;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.teamfour.dao.entity.user.RoleUser;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface RoleUserQualifier {
    RoleUser value();
}
