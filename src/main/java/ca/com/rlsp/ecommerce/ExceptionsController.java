package ca.com.rlsp.ecommerce;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.dto.ErrorObjectDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler {


    private static final String DB_INTEGRATY_ERROR = "Database Integraty Error";
    private static final String DB_CONSTRAINT_VIOLATION_ERROR = "Database Constrant Violation Error (Foreeign key error)";
    private static final String DB_SQL_ERROR = "Database SQL Error";
    public static final String NO_MESSAGE_SENT_INTO_THE_REQUEST_BODY = "No message sent into the Request Body [RLSP]";

    /* Captura a EXCECAO Customizada EcommerceException */
    @ExceptionHandler(EcommerceException.class)
    public ResponseEntity<Object> handleEcommerceException(EcommerceException ecommerceException) {

        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();

        errorObjectDTO.setError(ecommerceException.getMessage());
        errorObjectDTO.setCode(HttpStatus.OK.toString());

        return new ResponseEntity<>(errorObjectDTO, HttpStatus.OK);
    }

    /* Captura Excecoes Genericas do Projeto */
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

        } else if(ex instanceof HttpMessageNotReadableException) {
            message = NO_MESSAGE_SENT_INTO_THE_REQUEST_BODY;
        }else {
            message = ex.getMessage();
        }

        errorObjectDTO.setError(message);
        errorObjectDTO.setCode(status.value() + " - " + status.getReasonPhrase());

        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /* Captura ERRORS do Database*/
    @ExceptionHandler({DataIntegrityViolationException.class,
                       ConstraintViolationException.class,
                       SQLException.class})
    protected ResponseEntity<Object> handleExceptionDatabaseIntegraty(Exception ex, Object body, HttpHeaders headers,
                                                                      HttpStatus status, WebRequest request)  {

        ErrorObjectDTO errorObjectDTO = new ErrorObjectDTO();

        String message = "";

        if(ex instanceof DataIntegrityViolationException ) {
            message = DB_INTEGRATY_ERROR + ((DataIntegrityViolationException) ex).getCause().getMessage();
        } else if (ex instanceof ConstraintViolationException){
            message = DB_CONSTRAINT_VIOLATION_ERROR + ((ConstraintViolationException) ex).getCause().getMessage();
        } else if (ex instanceof  SQLException) {
            message = DB_SQL_ERROR + ((SQLException) ex).getCause().getMessage();
        } else {
            message = ex.getMessage();
        }

        errorObjectDTO.setError(message);
        errorObjectDTO.setCode(status.value() + " - " + status.getReasonPhrase());

        return new ResponseEntity<Object>(errorObjectDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
