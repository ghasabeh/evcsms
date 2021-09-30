package devolon.fi.evcsms.model.dto;

import devolon.fi.evcsms.utils.UpdateValidationGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Data
public class BaseDto implements Serializable {
    @NotNull(message = "error.validation.notNull", groups = UpdateValidationGroup.class)
    private Long id;
}
