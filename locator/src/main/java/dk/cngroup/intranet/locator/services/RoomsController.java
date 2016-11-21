package dk.cngroup.intranet.locator.services;

import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RoomsController is a class implementing spring rest annotations and will provide REST services to return Room objects.
 * @author Victor Cano
 */
@RestController
public class RoomsController {

    @Autowired
    private RoomsRepository repository;

    /**
     * Rest service to return a list of all rooms in the database.
     *
     * @return an Iterable object of Room objects
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/rooms")
    public Iterable<Room> getCNRooms(){

        Iterable<Room> rooms = repository.findAllByOrderByRoomId();

        if(rooms == null) {
            Application.getLogger().info("/rooms failed, no rooms found.");
            throw new RoomsServiceException();
        }

        return rooms;

    }

    /**
     * Rest service to return data on a specific room in the database based on the buildingId and the room number.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/rooms/{building_id}/{room_name}")
    public Room getSingleCNRoom(@PathVariable(value="room_name") String room_name,
                                @PathVariable(value="building_id") String buildingId){

        Room room = null;

        try {

            List<Room> result = repository.findByNameAndBuildingId(room_name, buildingId);

            if(result.size()>0)
                room = result.get(0);

        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id}/{room_name} failed, no rooms found.");
            throw new RoomsServiceException();
        }

        return room;

    }

    /**
     * Rest service to return data on rooms in the database based on the buildingId.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/rooms/{building_id}")
    public List<Room> getCNRoomsByBuilding(@PathVariable(value="building_id") String buildingId){

        List<Room> result;

        try {

            result = repository.findByBuildingId(buildingId);

        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id} failed, no rooms found.");
            throw new RoomsServiceException();
        }

        return result;

    }


    /**
     * Rest service to return data on rooms in the database based on the buildingId and the floorId.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping("/rooms/byfloor/{building_id}/{floor_name}")
    public List<Room> getCNRoomsByBuildingAndFloor(@PathVariable(value="floor_name") String floor_name,
                                     @PathVariable(value="building_id") String buildingId){

        List<Room> result;

        try {

            result = repository.findByFloorNameAndBuildingId(floor_name, buildingId);


        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id}/{floor_id} failed, no rooms found.");
            throw new RoomsServiceException();
        }

        return result;

    }

    /**
     * Rest service to add a room in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/rooms/new/room")
    @ResponseBody
    public String addSingleCNRoom(@RequestBody Room inputRoom) {
        try{

            Room newRoom = new Room(inputRoom);
            repository.save(newRoom);

        }catch(Exception e){
            Application.getLogger().info("/rooms/new/room, Room not added.");
            throw new RoomsServiceException();
        }

        return "redirect:/";
    }

    /**
     * Rest service to delete a room in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/rooms/delete/room")
    @ResponseBody
    public String deleteSingleCNRoom(@RequestBody Room newRoom) {
        try{
            repository.delete(newRoom);
        }catch(Exception e){
            Application.getLogger().info("/rooms/delete/room, Room not added.");
            throw new RoomsServiceException();
        }

        return "redirect:/";
    }

    /**
     * Rest service to update a room in the database
     *
     * @return a String object
     */
    @CrossOrigin(origins = {"${origin.locator.ui}", "${origin.locator.ui.tool}"})
    @RequestMapping(method = RequestMethod.POST, path="/rooms/update/room")
    @ResponseBody
    public String updateSingleCNRoom(@RequestBody Room utilityRoom) {
        try{
            Room updatedRoom = new Room();
            updatedRoom = getSingleCNRoom(utilityRoom.getName(),utilityRoom.getBuildingId());
            updatedRoom.setStyleTop(utilityRoom.getStyleTop());
            updatedRoom.setStyleLeft(utilityRoom.getStyleLeft());

            repository.save(updatedRoom);

        }catch(Exception e){
            Application.getLogger().info("/rooms/update/room, Room not updated.");
            throw new RoomsServiceException();
        }
        return "redirect:/";
    }

    public RoomsRepository getRepository(){
        return repository;
    }
}
