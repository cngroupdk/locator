package dk.cngroup.intranet.locator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmployeesServiceException is an exception for Web API services related to the methods in the EmployeesController class
 * @author Victor Cano
 */
@CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Employees Service Found.")  // 404
public class EmployeesServiceException extends RuntimeException{

}
