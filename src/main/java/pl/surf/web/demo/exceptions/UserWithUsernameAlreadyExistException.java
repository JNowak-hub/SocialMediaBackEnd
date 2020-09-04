package pl.surf.web.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithUsernameAlreadyExistException extends RuntimeException {
    public UserWithUsernameAlreadyExistException(String exception) {
        super(exception);
    }
}
