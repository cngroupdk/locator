package dk.cngroup.intranet.locator.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/rooms")
    public Iterable<Room> getCNRooms(){

        Iterable<Room> rooms = repository.findAllByOrderByRoomId();

        if(rooms == null) {
            Application.getLogger().info("/rooms failed, no rooms found.");
            throw new ServiceNotFoundException();
        }

        return rooms;

    }

    /**
     * Rest service to return data on a specific room in the database based on the buildingId and the room number.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/rooms/{building_id}/{room_name}")
    public Room getSingleCNRoom(@PathVariable(value="room_name") String room_name,
                                @PathVariable(value="building_id") String buildingId){

        Room room = null;

        try {

            room_name = URLDecoder.decode(room_name, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            List<Room> result = new ArrayList<Room>();

            result = repository.findByNameAndBuildingId(room_name, buildingId);

            if(result.size()>0)
                room = result.get(0);

        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id}/{room_name} failed, no rooms found.");
            throw new ServiceNotFoundException();
        }

        return room;

    }

    /**
     * Rest service to return data on rooms in the database based on the buildingId.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/rooms/{building_id}")
    public List<Room> getCNRoomsByBuilding(@PathVariable(value="building_id") String buildingId){

        List<Room> result = new ArrayList<Room>();

        try {

            buildingId = URLDecoder.decode(buildingId, "UTF-8");
            result = repository.findByBuildingId(buildingId);

        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id} failed, no rooms found.");
            throw new ServiceNotFoundException();
        }

        return result;

    }


    /**
     * Rest service to return data on rooms in the database based on the buildingId and the floorId.
     *
     * @return a Room object
     */
    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping("/rooms/byfloor/{building_id}/{floor_name}")
    public List<Room> getCNRoomsByBuildingAndFloor(@PathVariable(value="floor_name") String floor_name,
                                     @PathVariable(value="building_id") String buildingId){

        List<Room> result = new ArrayList<Room>();

        try {

            floor_name = URLDecoder.decode(floor_name, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            result = repository.findByFloorNameAndBuildingId(floor_name, buildingId);


        }catch(Exception e){
            Application.getLogger().info("/rooms/{building_id}/{floor_id} failed, no rooms found.");
            throw new ServiceNotFoundException();
        }

        return result;

    }


    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/rooms/newroom")
    @ResponseBody
    public String addSingleCNRoom(@RequestBody Room newRoom) {

        newRoom.setRoomId((int)repository.count());

        repository.save(newRoom);

        return "UpdateDone";
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
    @RequestMapping(method = RequestMethod.POST, path="/rooms/updateroom")
    @ResponseBody
    public String updateSingleCNRoom(@RequestBody Room utilityRoom) {

        Room updatedRoom = new Room();
        updatedRoom = getSingleCNRoom(utilityRoom.getName(),utilityRoom.getBuildingId());
        updatedRoom.setStyleTop(utilityRoom.getStyleTop());
        updatedRoom.setStyleLeft(utilityRoom.getStyleLeft());

        repository.save(updatedRoom);

        return "UpdateDone";
    }
}
