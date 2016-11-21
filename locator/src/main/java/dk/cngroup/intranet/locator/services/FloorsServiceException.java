package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * FloorsServiceException is an exception for Web API services related to the methods in the FloorsController class
 * @author Victor Cano
 */
@CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Floors Service Found.")  // 404
public class FloorsServiceException extends RuntimeException{

}
