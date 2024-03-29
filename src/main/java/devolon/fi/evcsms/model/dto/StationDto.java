package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.model.dto.validation.latitude.LatitudeValidator;
import devolon.fi.evcsms.model.dto.validation.longitude.LongitudeValidator;
import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Data
public class StationDto extends BaseDto {
    @NotBlank(message = "name can not be blank.", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;
    @LatitudeValidator(groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    @NotNull(message = "latitude can not be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String latitude;
    @LongitudeValidator(groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    @NotNull(message = "longitude can not be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String longitude;
    @NotNull(message = "company can not be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private CompanyDto company;
}
