package devolon.fi.evcsms.model.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Alireza Ghasabeie, a.ghasabeh@gmail.com
 */
@Getter
@Setter
public class ResponseDto<T> {
    private ResponseType responseType = ResponseType.GENERAL;
    private T response;

    public ResponseDto(T response) {
        this.response = response;
    }
}
