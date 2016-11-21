package dk.cngroup.intranet.locator.services;

import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.buildings.*;
import dk.cngroup.intranet.locator.repositories.BuildingsRepository;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
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

    @Autowired
    private FloorsRepository floorsRepository;


    @Autowired
    private RoomsRepository roomsRepository;

    /**
     * Rest service to return a list of all floors in all buildings in the database.
     *
     * @return an Iterable object of Floor objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/tree")
    public TreeRoot getCNTreeStructure(){

        TreeRoot tr = new TreeRoot();

        try{

            Iterable<Building> buildings = repository.findAllByOrderByBuildingGuid();
            Iterable<Floor> floors = floorsRepository.findAllByOrderByFloorId();
            Iterable<Room> rooms = roomsRepository.findAllByOrderByRoomId();

            for(Building b : buildings){

                TreeBuilding treeBuilding = new TreeBuilding();
                treeBuilding.setBuildingId(b.getBuildingId());
                treeBuilding.setBuildingGuid(b.getBuildingGuid());
                treeBuilding.setName(b.getName());

                tr.addBuilding(treeBuilding);

                for(Floor f : floors){

                    if(f.getBuildingId().equals(treeBuilding.getBuildingId()))
                    {
                        TreeFloor treeFloor = new TreeFloor();
                        treeFloor.setBuildingId(f.getBuildingId());
                        treeFloor.setFloorId(f.getFloorId());
                        treeFloor.setName(f.getFloorName());

                        treeBuilding.addFloor(treeFloor);

                        for(Room r : rooms){
                            if( treeFloor.getName().equals(r.getFloorName())
                             && treeFloor.getBuildingId().equals(r.getBuildingId())
                             ){
                                TreeRoom treeRoom = new TreeRoom();
                                treeRoom.setFloorName(r.getFloorName());
                                treeRoom.setBuildingId(r.getBuildingId());
                                treeRoom.setName(r.getName());
                                treeRoom.setRoomId(r.getRoomId());

                                treeFloor.addRoom(treeRoom);
                            }
                        }
                    }
                }
            }

        }catch(Exception e){
            Application.getLogger().info("/tree failed, CN Asset list could not be generated.");
            throw new BuildingServiceException();
        }

        return tr;

    }

    /**
     * Rest service to return a list of all buildings in the database.
     *
     * @return an Iterable object of Building objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
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
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
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


    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/buildings/new/building")
    @ResponseBody
    public String addSingleCNBuilding(@RequestBody Building inputBuilding) {
        try{

            Building newBuilding = new Building(inputBuilding);
            repository.save(newBuilding);

        }catch(Exception e){
            Application.getLogger().info("/buildings/new/building, Building not added.");
            throw new RoomsServiceException();
        }

        return "redirect:/";
    }

    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/buildings/delete/building")
    @ResponseBody
    public String deleteSingleCNBuilding(@RequestBody Building delBuilding) {
        try{
            repository.delete(delBuilding);
            floorsRepository.removeByBuildingId(delBuilding.getBuildingId());
            roomsRepository.removeByBuildingId(delBuilding.getBuildingId());
        }catch(Exception e){
            Application.getLogger().info("/buildings/delete/building, Building not added.");
            throw new RoomsServiceException();
        }

        return "redirect:/";
    }

}
