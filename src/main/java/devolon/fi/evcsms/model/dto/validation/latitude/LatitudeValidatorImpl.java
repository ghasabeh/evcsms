package devolon.fi.evcsms.model.dto.validation.latitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public class LatitudeValidatorImpl implements ConstraintValidator<LatitudeValidator, String> {
    private static final double MAX_VALUE = 90.000000;
    private static final double MIN_VALUE = -90.000000;
    private static final String LATITUDE_PATTERN = "^[+,-]?\\d{1,2}(\\.\\d{6})$";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            boolean isValid = value.matches(LATITUDE_PATTERN);
            if (!isValid) {
                return false;
            }
            double latitude = Double.parseDouble(value);
            if (latitude < MIN_VALUE || latitude > MAX_VALUE) {
                return false;
            }
        }
        return true;
    }
}
