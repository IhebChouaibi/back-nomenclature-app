package eng.bns.nomenclature.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class CodeNotFoundException extends RuntimeException {
    public CodeNotFoundException(String message) {
        super(message);
    }
}
