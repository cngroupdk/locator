package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BuildingServiceException is an exception for Web API services related to the methods in the BuildingsController class
 * @author Victor Cano
 */
@CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Buildings Service Found")  // 404
public class BuildingServiceException extends RuntimeException{

}
