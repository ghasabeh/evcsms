package devolon.fi.evcsms.utils.exception;

import devolon.fi.evcsms.model.dto.response.ResponseDto;
import devolon.fi.evcsms.model.dto.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    private final SqlExceptionTranslator translator;

    @ExceptionHandler(value = {EntityNotFoundException.class, CustomEntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDto<ErrorMessage> resourceNotFoundException(Exception ex, WebRequest request) {
        ResponseDto<ErrorMessage> errorMessageResponseDto = new ResponseDto<>(ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(((ServletWebRequest) request).getRequest().getMethod() + " " + request.getDescription(false))
                .build());
        errorMessageResponseDto.setResponseType(ResponseType.EXCEPTION);
        return errorMessageResponseDto;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDto<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ResponseDto<ErrorMessage> errorMessageResponseDto = new ResponseDto<>(ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date())
                .message(translator.translate(ex))
                .description(((ServletWebRequest) request).getRequest().getMethod() + " " + request.getDescription(false))
                .build());
        errorMessageResponseDto.setResponseType(ResponseType.EXCEPTION);
        return errorMessageResponseDto;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorMessage> handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest request) {
        ResponseDto<ErrorMessage> errorMessageResponseDto = new ResponseDto<>(ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" , ")))
                .description(((ServletWebRequest) request).getRequest().getMethod() + " " + request.getDescription(false))
                .build());
        errorMessageResponseDto.setResponseType(ResponseType.EXCEPTION);
        return errorMessageResponseDto;
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDto<ErrorMessage> handleConstraintViolationException(javax.validation.ConstraintViolationException ex, WebRequest request) {
        ResponseDto<ErrorMessage> errorMessageResponseDto = new ResponseDto<>(ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(((ServletWebRequest) request).getRequest().getMethod() + " " + request.getDescription(false))
                .build());
        errorMessageResponseDto.setResponseType(ResponseType.EXCEPTION);
        return errorMessageResponseDto;
    }
}