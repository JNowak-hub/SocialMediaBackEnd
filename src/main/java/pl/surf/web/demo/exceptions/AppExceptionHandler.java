package pl.surf.web.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage()).collect(Collectors.toList());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handelConstraintViolationException(ConstraintViolationException ex) {
        final List<String> errors = new ArrayList<>();

        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWithUsernameAlreadyExistException.class)
    public final ResponseEntity<Object> handelUserWithUsernameAlreadyExist(UserWithUsernameAlreadyExistException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailIsAlreadyInUseException.class)
    public final ResponseEntity<Object> handelEmailIsAlreadyInUseException(EmailIsAlreadyInUseException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handelBadCredentialsException(BadCredentialsException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EventIsFullException.class)
    public final ResponseEntity<Object> handelEventIsFullException(EventIsFullException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyAssigned.class)
    public final ResponseEntity<Object> handelUserAlreadyAssigned(UserAlreadyAssigned ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LevelNotFoundException.class)
    public final ResponseEntity<Object> handelLevelNotFoundException(LevelNotFoundException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public final ResponseEntity<Object> handelCategoryNotFoundException(CategoryNotFoundException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExists.class)
    public final ResponseEntity<Object> handelCategoryAlreadyExists(CategoryAlreadyExists ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public final ResponseEntity<Object> handelEventNotFoundException(EventNotFoundException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VideoException.class)
    public final ResponseEntity<Object> CategoryNotFoundException(VideoException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handelDataIntegrityViolationException(DataIntegrityViolationException ex) {
        final List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorDetails errorDetails = new ErrorDetails(new Date(), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
