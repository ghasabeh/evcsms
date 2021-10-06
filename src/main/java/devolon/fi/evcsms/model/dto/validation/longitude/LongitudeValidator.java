package devolon.fi.evcsms.model.dto.validation.longitude;

import devolon.fi.evcsms.model.dto.validation.latitude.LatitudeValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LongitudeValidatorImpl.class)
@Documented
public @interface LongitudeValidator {
    String value() default "";

    String message() default "longitude must be between -180.000000 to +180.000000 with precision of 6 digits after decimal";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
