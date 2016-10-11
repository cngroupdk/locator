package locatorRoot.services;
import locatorRoot.repositories.FloorsRepository;
import locatorRoot.BuildingInformation.Floor;
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
public class FloorsController {

    @Autowired
    private FloorsRepository repository;

    @RequestMapping("/floors")
    public List<Floor> getCNFloors(){

        List<Floor> floors = new ArrayList<Floor>();

        for(Floor b : repository.findAll()){
            floors.add(b);
        }

        return floors;

    }

    @RequestMapping("/single_floor")
    public Floor getSingleCNFloor(@RequestParam(value="floor_number") String floorNumber,
                                  @RequestParam(value="building_id") String buildingId){

        Floor floor = null;
        List<Floor> floors = new ArrayList<Floor>();

        try {
            floorNumber = URLDecoder.decode(floorNumber, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            for(Floor b : repository.findByFloorNumberAndBuildingId(floorNumber, buildingId)){
                floors.add(b);
            }

            if (floors.size() > 0)
                floor = floors.get(0);

        }
        catch(Exception e){
            //TODO exception handling
        }

        return floor;

    }
}
