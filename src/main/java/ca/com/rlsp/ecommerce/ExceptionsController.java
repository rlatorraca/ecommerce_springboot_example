package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.model.dto.ErrorObjectDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler {


    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class}) // Pegas as Exxceooes mais genericas do JAVA
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();

        String message = "";

        if(ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> errorsList = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError error : errorsList){
                message += error.getDefaultMessage() + "\n";
            }

        } else {
            message = ex.getMessage();
        }

        errorObjectDTO.setError(message);
        errorObjectDTO.setCode(status.value() + " - " + status.getReasonPhrase());

        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
