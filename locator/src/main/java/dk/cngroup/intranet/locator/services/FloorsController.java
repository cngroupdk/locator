package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * FloorsController is a class implementing spring rest annotations and will provide REST services to return Floor objects.
 * @author Victor Cano
 */

@RestController
public class FloorsController {

    @Value("${internal.resources.files.location}")
    private String floorplanLocation;

    @Autowired
    private FloorsRepository repository;

    @Autowired
    private RoomsRepository roomRepository;

    /**
     * Rest service to return a list of all floors in all buildings in the database.
     *
     * @return an Iterable object of Floor objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/floors")
    public Iterable<Floor> getCNFloors(){

        Iterable<Floor> floors = repository.findAllByOrderByFloorId();

        if (floors == null) {
            Application.getLogger().info("/floors failed, no floors found.");
            throw new FloorsServiceException();
        }
        return floors;

    }


    /**
     * Rest service to return data on a specific floor in the database based on the buildingId and the floor number.
     *
     * @return a Floor object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/floors/{building_id}")
    public List<Floor> getCNFloorsByBuilding(@PathVariable(value="building_id") String buildingId){

        List<Floor> floors;

        try {

            floors = repository.findByBuildingId(buildingId);

        }
        catch(Exception e){
            Application.getLogger().info("/floors/{building_id}/{floor_name} failed, no floor found.");
            throw new FloorsServiceException();
        }

        return floors;

    }

    /**
     * Rest service to return data on a specific floor in the database based on the buildingId and the floor number.
     *
     * @return a Floor object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/floors/{building_id}/{floor_name}")
    public Floor getSingleCNFloor(@PathVariable(value="floor_name") String floorName,
                                  @PathVariable(value="building_id") String buildingId){

        Floor floor = null;

        try {

            List<Floor> floors = repository.findByFloorNameAndBuildingId(floorName, buildingId);

            if (floors.size() > 0)
                floor = floors.get(0);

        }
        catch(Exception e){
            Application.getLogger().info("/floors/{building_id}/{floor_name} failed, no floor found.");
            throw new FloorsServiceException();
        }

        return floor;

    }

    /**
     * Rest service to add a floor in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/new/floor")
    @ResponseBody
    public String addSingleCNFloor(@RequestBody Floor inputFloor) {
        try{

            Floor newFloor = new Floor(inputFloor);
            repository.save(newFloor);

        }catch(Exception e){
            Application.getLogger().info("/floors/new/floor, Floor not added.");
            throw new FloorsServiceException();
        }

        return "redirect:/";
    }

    /**
     * Rest service to delete a floor in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/delete/floor")
    @ResponseBody
    public String deleteSingleCNFloor(@RequestBody Floor deleteFloor) {
        try{

            deletePicture(deleteFloor);
            repository.delete(deleteFloor);
            roomRepository.removeByFloorName(deleteFloor.getFloorName());

        }catch(Exception e){
            Application.getLogger().info("/floors/delete/floor, Floor not added.");
            throw new FloorsServiceException();
        }

        return "redirect:/";
    }

    /**
     * Rest service to update a floor in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/update/floor")
    @ResponseBody
    public String updateSingleCNFloor(@RequestBody Floor updateFloor) {

        try{
            Floor update = repository.findByFloorId(updateFloor.getFloorId());
            deletePicture(update);

            repository.save(updateFloor);
        }catch(Exception e){
            Application.getLogger().info("/floors/update/floor, Floor not updated.");
            throw new FloorsServiceException();
        }

        return "redirect:/";
    }

    /**
     * Check if File exists
     *
     * @return a StoragePhotoFolder object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/floors/img/{floor_file}/{format}")
    public ImageProperties getIfFloorplanExists(@PathVariable(value="floor_file") String fileName,
                                                @PathVariable(value="format") String format){

        Path rootLocation = Paths.get(floorplanLocation);
        Path target = rootLocation.resolve(fileName + "." + format);
        String reply = new String("empty");

        if(Files.exists(target)) {
            reply = "exists";
        }

        ImageProperties floor = new ImageProperties();
        floor.setExistence(reply);
        floor.setFilename(fileName);
        floor.setExtension(format);
        return floor;
    }

    public boolean deletePicture(Floor floor){

        boolean pictureDeleted = false;

        try{

            Path rootLocation = Paths.get(floorplanLocation);
            Path target = rootLocation.resolve(floor.getFloorplanUrl().substring(1));
            Files.delete(target);

            pictureDeleted = true;

        }catch(Exception e){
            Application.getLogger().info("/floors/update/floor, Floor image not eliminated.");
        }

        return pictureDeleted;
    }

    public FloorsRepository getRepository(){
        return repository;
    }
}
