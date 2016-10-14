package dk.cngroup.intranet.locator.services;

import dk.cngroup.intranet.locator.Application;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/rooms")
    public Iterable<Room> getCNRooms(){

        Iterable<Room> rooms = repository.findAll();

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
}
