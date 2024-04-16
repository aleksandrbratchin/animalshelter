package ru.teamfour.validators.roleuser;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.teamfour.dao.entity.user.RoleUser;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleUserValidatorTest {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private RoleUserValidator roleUserValidator;

    @Nested
    class RoleUserValid {
        @Test
        void testClient() {
            String validRole = RoleUser.CLIENT.name();

            boolean isValid = roleUserValidator.isValid(validRole, constraintValidatorContext);

            assertTrue(isValid);
        }

        @Test
        void testVolunteer() {
            String validRole = RoleUser.VOLUNTEER.name();

            boolean isValid = roleUserValidator.isValid(validRole, constraintValidatorContext);

            assertTrue(isValid);
        }
    }


    @Test
    void testInvalidRoleUser() {
        String invalidRole = "INVALID_ROLE";

        boolean isValid = roleUserValidator.isValid(invalidRole, constraintValidatorContext);

        assertFalse(isValid);
    }
}