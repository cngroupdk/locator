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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
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
    @RequestMapping("/employees/photo/folder")
    public StoragePhotoFolder getEmployeePhotoFolder(){
        StoragePhotoFolder photoFolder = new StoragePhotoFolder();
        photoFolder.setUrl(photoUrl);
        return photoFolder;
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

        return "redirect:/";
    }

    public StaffMemberRepository getRepository(){
        return repository;
    }

    private static final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Value("${timur.persons.url}")
    private String timurURL;

    @Scheduled(fixedDelayString = "${schedule.task.interval}")
    public void getTimurNames(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Person>> rateResponse =
                restTemplate.exchange(  timurURL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {}
                );

        List<Person> employees = rateResponse.getBody();

        log.info("Connected to Persons at " + dateFormat.format(new Date()));

    }

}
