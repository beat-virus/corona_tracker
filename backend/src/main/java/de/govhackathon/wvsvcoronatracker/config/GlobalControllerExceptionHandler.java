package de.govhackathon.wvsvcoronatracker.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    // SECURITY ACCESS HANDLING

    /* TODO Security
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<?> handleSecurityErrors(AccessDeniedException ex) {
        String errorID = UUID.randomUUID().toString();
        LOG.error("Got unknown error with id {} ", errorID, ex);
        // fallback to server error
        return new ResponseEntity<>(new String("Not allowed (ID: " + errorID + ")"), HttpStatus.FORBIDDEN);
    }
*/
    // GENERAL ERROR HANDLING

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleErrors(Exception ex) {
        if (ex.getCause() instanceof RollbackException
                && ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause().getCause();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            String errorID = UUID.randomUUID().toString();
            LOG.error("Got unknown error with id {} ", errorID, ex);
            // fallback to server error
            return new ResponseEntity<>("An unknown error occurred (ID: " + errorID + ")", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // VALIDATION ERROR HANDLING

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationError(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        //TODO add id here
        return new ResponseEntity<>("Could not find entity by id", HttpStatus.NOT_FOUND);
    }



}
