package pt.ipleiria.estg.dei.ei.dae.prc.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class MyConstraintViolationException extends Exception {
    public MyConstraintViolationException(String messsage) {
        super(messsage);
    }
}

