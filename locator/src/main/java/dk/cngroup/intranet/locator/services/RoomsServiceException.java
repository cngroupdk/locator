package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cano on 14.10.2016.
 */

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Rooms Service Found.")  // 404
public class RoomsServiceException extends RuntimeException{

}
