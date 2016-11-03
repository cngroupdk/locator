package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.repositories.StaffMemberRepository;
import dk.cngroup.intranet.locator.actors.StaffMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * EmployeesController is a class implementing spring rest annotations and will provide REST services to return objects implementing the StaffMember interface
 * @author Victor Cano
 */

@RestController
public class EmployeesController {

    @Autowired
    private StaffMemberRepository repository;

    /**
     * Rest service to return a list of all employees in the database.
     *
     * @return an Iterable object of StaffMemberCreate objects
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/employees")
    public List<StaffMember> getEmployeeList(){

        Iterable<StaffMember> employees = repository.findAll();
        List<StaffMember> result = repository.findAllByOrderByEmployeeGuid();

        if (employees == null) {
            Application.getLogger().info("/employees failed, no employees found.");
            throw new EmployeesServiceException();
        }
        return result;

    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/employees/{first_name}/{last_name}")
    public StaffMember getSingleEmployee(@PathVariable(value="first_name") String firstName,
                                         @PathVariable(value="last_name") String lastName){

        StaffMember employee = null;

        try {

            List<StaffMember> result = repository.findByFirstNameAndLastName(firstName, lastName);

            if (result.size() > 0)
                employee =  result.get(0);

        }catch(Exception e){
            Application.getLogger().info("/employees/{first_name}/{last_name}, no employee found.");
            throw new EmployeesServiceException();
        }

        return employee;

    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/employees/update/employee")
    @ResponseBody
    public String updateSingleEmployee(@RequestBody StaffMember updatedEmployee) {
        try {
            repository.save(updatedEmployee);
        }catch(Exception e){
            Application.getLogger().info("/employees/update/employee, Employee not updated.");
            throw new EmployeesServiceException();
        }

        return "UpdateDone";
    }

}
