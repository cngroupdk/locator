package dk.cngroup.intranet.locator.services;

import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildings.Building;
import dk.cngroup.intranet.locator.repositories.BuildingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * BuildingController is a class implementing spring rest annotations and will provide REST services to return Building objects
 * @author Victor Cano
 */

@RestController
public class BuildingController {

    @Autowired
    private BuildingsRepository repository;

    /**
     * Rest service to return a list of all buildings in the database.
     *
     * @return an Iterable object of Building objects
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/buildings")
    public Iterable<Building> getCNBuildings(){

        Iterable<Building> buildings = repository.findAllByOrderByBuildingGuid();

        if (buildings == null) {
            Application.getLogger().info("/buildings failed, no buildings found.");
            throw new BuildingServiceException();
        }
        return buildings;

    }

    /**
     * Rest service to return data on a specific building in the database based on the buildingId.
     *
     * @return a Building object
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("buildings/{building_id}")
    public Building getSingleCNBuilding(@PathVariable("building_id") String buildingId){

        Building building = null;

        try {

            List<Building> buildings = repository.findByBuildingId(buildingId);

            if (buildings.size() > 0)
                building = buildings.get(0);

        }catch(Exception e){
            Application.getLogger().info("/buildings/{building_id} failed, no building found.");
            throw new BuildingServiceException();
        }

        return building;

    }

}
