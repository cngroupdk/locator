package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FloorsController is a class implementing spring rest annotations and will provide REST services to return Floor objects.
 * @author Victor Cano
 */

@RestController
public class FloorsController {

    @Autowired
    private FloorsRepository repository;

    /**
     * Rest service to return a list of all floors in all buildings in the database.
     *
     * @return an Iterable object of Floor objects
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
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

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/new/floor")
    @ResponseBody
    public String addSingleCNFloor(@RequestBody Floor newFloor) {
        try{
            newFloor.setFloorId((int)repository.count());
            repository.save(newFloor);
        }catch(Exception e){
            Application.getLogger().info("/floors/new/floor, Floor not added.");
            throw new FloorsServiceException();
        }

        return "redirect:/";
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/delete/floor")
    @ResponseBody
    public String deleteSingleCNFloor(@RequestBody Floor newFloor) {
        try{
            repository.delete(newFloor);
        }catch(Exception e){
            Application.getLogger().info("/floors/delete/floor, Floor not added.");
            throw new FloorsServiceException();
        }

        return "redirect:/";
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/floors/update/floor")
    @ResponseBody
    public String updateSingleCNFloor(@RequestBody List<Floor> utilityFloors) {

        try{
            Floor oldRoom = utilityFloors.get(0);
            Floor newRoom = utilityFloors.get(1);

            Floor dbRoom = getSingleCNFloor(oldRoom.getFloorName(),oldRoom.getBuildingId());
            dbRoom.setFloorName(newRoom.getFloorName());
            dbRoom.setRoomsNumber(newRoom.getRoomsNumber());
            dbRoom.setFloorplanUrl(newRoom.getFloorplanUrl());

            repository.save(dbRoom);
        }catch(Exception e){
            Application.getLogger().info("/floors/update/floor, Floor not updated.");
            throw new FloorsServiceException();
        }
        return "redirect:/";
    }

    public FloorsRepository getRepository(){
        return repository;
    }
}
