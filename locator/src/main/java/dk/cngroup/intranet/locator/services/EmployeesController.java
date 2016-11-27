package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.actors.Person;
import dk.cngroup.intranet.locator.repositories.StaffMemberRepository;
import dk.cngroup.intranet.locator.actors.StaffMember;
import dk.cngroup.intranet.locator.services.storage.StoragePhotoFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EmployeesController is a class implementing spring rest annotations and will provide REST services to return objects implementing the StaffMember interface
 * @author Victor Cano
 */

@RestController
public class EmployeesController {

    @Autowired
    private StaffMemberRepository repository;

    @Value("${intranet.photos.url}")
    private String photoUrl;

    /**
     * Rest service to return a list of all employees in the database.
     *
     * @return an Iterable object of StaffMember objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/employees")
    public List<StaffMember> getEmployeeList(){

        List<StaffMember> result = repository.findAllByOrderByEmployeeGuid();

        if (result == null) {
            Application.getLogger().info("/employees failed, no employees found.");
            throw new EmployeesServiceException();
        }
        return result;

    }

    /**
     * Rest service to return a list of all assigned employees in the database.
     *
     * @return an Iterable object of StaffMember objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/employees/assigned")
    public List<StaffMember> getAssignedEmployeeList(){

        List<StaffMember> employees = repository.findAllByLocationNot("undefined");

        if (employees == null) {
            Application.getLogger().info("/employees failed, no employees found.");
            throw new EmployeesServiceException();
        }
        return employees;
    }

    /**
     * Rest service to return a list of all unassigned employees in the database.
     *
     * @return an Iterable object of StaffMember objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/employees/unassigned")
    public List<StaffMember> getUnassignedEmployeeList(){

        List<StaffMember> employees = repository.findAllByLocation("undefined");

        if (employees == null) {
            Application.getLogger().info("/employees failed, no employees found.");
            throw new EmployeesServiceException();
        }
        return employees;
    }

    /**
     * Rest service to return information on a single employee.
     *
     * @return a StaffMember object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
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

    /**
     * Rest service to return location of employee photo folders.
     *
     * @return a StoragePhotoFolder object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/employees/photo/folder")
    public StoragePhotoFolder getEmployeePhotoFolder(){
        StoragePhotoFolder photoFolder = new StoragePhotoFolder();
        photoFolder.setUrl(photoUrl);
        return photoFolder;
    }

    /**
     * Rest service to update an employee in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/employees/update/employee")
    @ResponseBody
    public String updateSingleEmployee(@RequestBody StaffMember updatedEmployee) {
        try {
            repository.save(updatedEmployee);
        }catch(Exception e){
            Application.getLogger().info("/employees/update/employee, Employee not updated.");
            throw new EmployeesServiceException();
        }

        return "redirect:/";
    }

    public StaffMemberRepository getRepository(){
        return repository;
    }

    private static final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${timur.persons.url}")
    private String timurURL;

    /**
     * Scheduled service to update employees in the database from TIMUR
     *
     * @return a String object
     */
    @Scheduled(fixedDelayString = "${schedule.task.interval}")
    public void getTimurNames() {

        try {

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<List<Person>> rateResponse =
                    restTemplate.exchange(timurURL,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Person>>() {
                            }
                    );

            List<Person> persons = rateResponse.getBody();
            List<StaffMember> staffMembers = getEmployeeList();

            List<StaffMember> staffToDelete = new ArrayList<StaffMember>();

            for (StaffMember staffMember : staffMembers) {

                boolean found = false;
                Person personToRemove = new Person();

                for (Person person : persons) {
                    if (person.getId().equals(staffMember.getEmployeeGuid())) {
                        personToRemove = new Person(person);
                        found = true;
                        break;
                    }
                }

                if (found == true) {
                    persons.remove(personToRemove);
                } else {
                    staffToDelete.add(staffMember);
                }
            }

            for (StaffMember sm : staffToDelete) {
                repository.delete(sm);
            }

            for (Person p : persons) {

                String[] names = p.getName().split(" ");
                StaffMember newStaff = new StaffMember();

                newStaff.setEmployeeGuid(p.getId());
                newStaff.setEmployeeId(p.getLogin());
                newStaff.setFirstName(names[1]);
                newStaff.setLastName(names[0]);
                newStaff.setManagerId(p.getBossId());
                newStaff.setRole(p.getJobCategory());
                newStaff.setDepartment(p.getDepartment());
                newStaff.setEmail(p.getEmail());
                newStaff.setLocation("undefined");
                repository.save(newStaff);
            }

            log.info("Updated from TIMUR at " + dateFormat.format(new Date()));

        } catch (Exception e) {
            log.info("Unable to update from TIMUR");
        }
    }



}
