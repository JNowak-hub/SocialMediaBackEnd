package pl.surf.web.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VideoException extends RuntimeException{
    public VideoException(String message) {
        super(message);
    }
}
