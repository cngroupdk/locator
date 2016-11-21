package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RoomsServiceException is an exception for Web API services related to the methods in the RoomsController class
 * @author Victor Cano
 */
@CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Rooms Service Found.")  // 404
public class RoomsServiceException extends RuntimeException{

}
