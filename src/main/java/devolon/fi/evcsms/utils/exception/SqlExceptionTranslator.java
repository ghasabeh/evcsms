package devolon.fi.evcsms.utils.exception;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Component
@RequiredArgsConstructor
public class SqlExceptionTranslator {
    private final Environment environment;

    public String translate(ConstraintViolationException ex) {

        return switch (ex.getSQLException().getSQLState()) {
            case "23503" -> replacingInputs(environment.getProperty("error.orm.sql.state.23503"),
                    environment.getProperty(ex.getConstraintName()));
            case "23505" -> replacingInputs(environment.getProperty("error.orm.sql.state.23505"),
                    environment.getProperty(ex.getConstraintName()));
            /* todo
             * All ORM code must be inserted here
             */
            default -> environment.getProperty("error.orm.general", ex.getSQLException().getSQLState());
        };
    }

    private static String replacingInputs(String value, String... arguments) {
        if (value == null || arguments == null) return null;
        for (String argument : arguments) {
            value = String.format(value, argument);
        }
        return value;
    }
}
