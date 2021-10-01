package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Data
public class CompanyDto extends BaseDto {
    @NotBlank(message = "error.validation.notBlank", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;
    private CompanyDto parent;
}
