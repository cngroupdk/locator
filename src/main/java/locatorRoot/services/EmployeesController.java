package locatorRoot.services;

import locatorRoot.actors.StaffMember;

import locatorRoot.actors.StaffMemberFactory;
import locatorRoot.repositories.StaffMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cano on 29.9.2016.
 */

@RestController
public class EmployeesController {

    @Autowired
    private StaffMemberRepository repository;

    @RequestMapping("/employees")
    public List<StaffMember> getEmployeeList(){

        List<StaffMember> employees = new ArrayList<StaffMember>();

        try {

            for(StaffMember sm : repository.findAll()){
                employees.add(sm);
            }

        }catch(Exception e){
            //TO DO: Error handling
            e.printStackTrace();
        }

        return employees;

    }

    @RequestMapping("/single_employee")
    public StaffMember getSingleEmployee(@RequestParam(value="first_name") String firstName,
                                         @RequestParam(value="last_name") String lastName){

        StaffMember employee = null;

        try {
            firstName = URLDecoder.decode(firstName, "UTF-8");
            lastName = URLDecoder.decode(lastName, "UTF-8");

            List<StaffMember> result = new ArrayList<StaffMember>();

            for(StaffMember sm : repository.findByFirstNameAndLastName(firstName, lastName)){
                result.add(sm);
            }

            if (result.size() > 0)
                employee =  result.get(0);

        }catch(Exception e){
            //TO DO: Error handling
        }

        return employee;

    }

}
