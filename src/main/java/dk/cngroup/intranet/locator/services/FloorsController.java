package dk.cngroup.intranet.locator.services;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.ArrayList;
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
    @RequestMapping("/floors")
    public Iterable<Floor> getCNFloors(){

        Iterable<Floor> floors = repository.findAllByOrderByFloorId();

        if (floors == null) {
            Application.getLogger().info("/floors failed, no floors found.");
            throw new ServiceNotFoundException();
        }
        return floors;

    }

    /**
     * Rest service to return data on a specific floor in the database based on the buildingId and the floor number.
     *
     * @return a Floor object
     */
    @RequestMapping("/floors/{building_id}/{floor_number}")
    public Floor getSingleCNFloor(@PathVariable(value="floor_number") String floorNumber,
                                  @PathVariable(value="building_id") String buildingId){

        Floor floor = null;
        List<Floor> floors = new ArrayList<Floor>();

        try {
            floorNumber = URLDecoder.decode(floorNumber, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            floors = repository.findByFloorNumberAndBuildingId(floorNumber, buildingId);

            if (floors.size() > 0)
                floor = floors.get(0);

        }
        catch(Exception e){
            Application.getLogger().info("/floors/{building_id}/{floor_number} failed, no floor found.");
            throw new ServiceNotFoundException();
        }

        return floor;

    }
}
