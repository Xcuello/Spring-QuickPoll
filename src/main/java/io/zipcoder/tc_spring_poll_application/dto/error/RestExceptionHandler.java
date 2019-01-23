package io.zipcoder.tc_spring_poll_application.dto.error;

import io.zipcoder.tc_spring_poll_application.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setDeveloperMessage(rnfe.getMessage());

        errorDetail.setTimeStamp(new Date().getTime());

        return new ResponseEntity<ErrorDetail>(HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?>
    handleValidationError(MethodArgumentNotValidException manve,
                          HttpServletRequest request){

        ErrorDetail errorDetail = new ErrorDetail();

        List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
        for(FieldError fe : fieldErrors) {

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
            if(validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(fe.getField(), validationErrorList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);

        }

        return new ResponseEntity<ErrorDetail>(HttpStatus.BAD_REQUEST);

    }
}
