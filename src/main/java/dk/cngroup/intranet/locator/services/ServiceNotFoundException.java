package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by cano on 14.10.2016.
 */

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Service")  // 404
public class ServiceNotFoundException extends RuntimeException{

}
