package devolon.fi.evcsms.model.dto.validation.longitude;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
public class LongitudeValidatorImpl implements ConstraintValidator<LongitudeValidator, String> {
    private static final double MAX_VALUE = 180.000000;
    private static final double MIN_VALUE = -180.000000;
    private static final String LONGITUDE_PATTERN = "^[+,-]?\\d{1,3}(\\.\\d{6})$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            boolean isLongitudeValid = value.matches(LONGITUDE_PATTERN);
            if (!isLongitudeValid) {
                return false;
            }
            double longitude = Double.parseDouble(value);
            if (longitude < MIN_VALUE || longitude > MAX_VALUE) {
                return false;
            }
        }
        return true;
    }
}
