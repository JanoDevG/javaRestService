package cl.janodevg.restService.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailNotValidException extends RuntimeException{

    public EmailNotValidException(String message) {
        super(message);
    }
}
