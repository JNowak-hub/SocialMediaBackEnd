package pl.surf.web.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LevelNotFoundException extends RuntimeException{
    public LevelNotFoundException(String message) {
        super(message);
    }
}
