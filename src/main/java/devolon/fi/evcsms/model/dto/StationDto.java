package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.model.entity.CompanyEntity;
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
    @NotBlank(message = "error.validation.notBlank", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;
    @NotNull(message = "error.validation.notNull", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private Double latitude;
    @NotNull(message = "error.validation.notNull", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private Double longitude;
    @NotNull(message = "error.validation.notNull", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private CompanyEntity company;
}
