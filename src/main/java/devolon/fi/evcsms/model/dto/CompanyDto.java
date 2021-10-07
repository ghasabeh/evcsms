package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.utils.CreateValidationGroup;
import devolon.fi.evcsms.utils.UpdateValidationGroup;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDto extends BaseDto {
    @NotBlank(message = "name can not be blank", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    private String name;
    private String path;
    private CompanyDto parent;
}
