package co.com.template.Controllers;

import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
@RestControllerAdvice
public class ValidationHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO parameterExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                return new ResponseDTO(HttpStatus.BAD_REQUEST,fieldError.getDefaultMessage(),null);
            }
        }
        return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.ERROR_HANDLER_MESSAGE,null);
    }
}
