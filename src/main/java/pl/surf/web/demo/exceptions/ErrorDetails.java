package pl.surf.web.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class ErrorDetails {
    private List<String> message;
    private Date date;
    private static final Logger log = LoggerFactory.getLogger(ErrorDetails.class);

    public ErrorDetails(Date date, List<String> message) {
        log.info(message.iterator().next());
        this.date = date;
        this.message = message;

    }

    public List<String> getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
