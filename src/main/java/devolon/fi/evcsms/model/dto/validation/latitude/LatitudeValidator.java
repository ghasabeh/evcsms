package devolon.fi.evcsms.model.dto.validation.latitude;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LatitudeValidatorImpl.class)
@Documented
public @interface LatitudeValidator {
        String value() default "";

        String message() default "latitude must be between -90.000000 to +90.000000 with precision of 6 digits after decimal";

        Class<?>[] groups() default { };

        Class<? extends Payload>[] payload() default { };
}
