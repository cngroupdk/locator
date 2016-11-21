package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cano on 14.10.2016.
 */

@CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Buildings Service Found")  // 404
public class BuildingServiceException extends RuntimeException{

}
