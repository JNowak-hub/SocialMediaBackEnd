package pl.surf.web.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EventIsFullException extends RuntimeException {

    public EventIsFullException(String message) {
        super(message);
    }
}
