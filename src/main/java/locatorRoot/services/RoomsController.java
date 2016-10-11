package locatorRoot.services;

import locatorRoot.BuildingInformation.Room;
import locatorRoot.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cano on 11.10.2016.
 */

@RestController
public class RoomsController {

    @Autowired
    private RoomsRepository repository;


    @RequestMapping("/rooms")
    public List<Room> getCNRooms(){

        List<Room> rooms = new ArrayList<Room>();

        for(Room r : repository.findAll()){
            rooms.add(r);
        }

        return rooms;

    }

    @RequestMapping("/single_room")
    public Room getSingleCNRoom(@RequestParam(value="room_name") String room_name,
                                @RequestParam(value="building_id") String buildingId){

        Room room = null;

        try {

            room_name = URLDecoder.decode(room_name, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            List<Room> result = new ArrayList<Room>();
            for(Room r : repository.findByNameAndBuildingId(room_name, buildingId)){
                result.add(r);
            }

            if(result.size()>0)
                room = result.get(0);

        }catch(Exception e){
            //TODO: Error handling
        }

        return room;

    }
}
