package pl.surf.web.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailIsAlreadyInUseException extends RuntimeException {
    public EmailIsAlreadyInUseException(String exception) {
        super(exception);
    }
}
