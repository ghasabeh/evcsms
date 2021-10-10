package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.model.dto.validation.latitude.LatitudeValidator;
import devolon.fi.evcsms.model.dto.validation.longitude.LongitudeValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @NotNull(message = "latitude can not be null")
    @LatitudeValidator
    String latitude;
    @NotNull(message = "longitude can not be null")
    @LongitudeValidator
    String longitude;
    @NotNull(message = "distance can not be null")
    @Positive(message = "distance must be greater than 0")
    Double radius;
}
