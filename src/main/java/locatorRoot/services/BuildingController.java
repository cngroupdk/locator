package locatorRoot.services;

import locatorRoot.Buildings.Building;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import locatorRoot.Buildings.BuildingFactory;
import locatorRoot.repositories.BuildingsRepository;
import locatorRoot.BuildingInformation.Floor;
import locatorRoot.BuildingInformation.FloorFactory;
import locatorRoot.BuildingInformation.Room;
import locatorRoot.BuildingInformation.RoomFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import locatorRoot.utilities.dbUtilities;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by cano on 30.9.2016.
 */
@RestController
public class BuildingController {

    @Autowired
    private BuildingsRepository repository;

    @RequestMapping("/buildings")
    public List<Building> getCNBuildings(){

        List<Building> buildings = new ArrayList<Building>();

        try {

            /*SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM Buildings.BuildingFactory";
            Query query = session.createQuery(hql);
            List<BuildingFactory> result = query.list();*/

            for(Building b : repository.findAll()){
                buildings.add(b);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return buildings;

    }

    @RequestMapping("/single_building")
    public Building getSingleCNBuilding(@RequestParam(value="building_id") String buildingId){

        Building building = null;

        try {
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            /*SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM Buildings.BuildingFactory WHERE building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<Building> result = query.list();*/

            List<Building> buildings = new ArrayList<Building>();
            for(Building b : repository.findByBuildingId(buildingId)){
                buildings.add(b);
            }

            if (buildings.size() > 0)
                building = buildings.get(0);

        }catch(Exception e){
            //TODO:Exception Handling
        }

        return building;

    }

    /*
    @RequestMapping("/floors")
    public List<Floor> getCNFloors(){

        List<Floor> floors = new ArrayList<Floor>();

        SessionFactory sessionFactory = dbUtilities.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM BuildingInformation.FloorFactory";
        Query query = session.createQuery(hql);
        List<FloorFactory> result = query.list();

        for(Object element : result){

            floors.add(FloorFactory.getFloor((Floor) element));
        }

        return floors;

    }

    @RequestMapping("/single_floor")
    public Floor getSingleCNFloor(@RequestParam(value="floor_number") String floorNumber,
                                  @RequestParam(value="building_id") String buildingId){

        Floor floor = null;

        try {
            floorNumber = URLDecoder.decode(floorNumber, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM BuildingInformation.FloorFactory WHERE floor_number =" + floorNumber + " AND building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<Floor> result = query.list();

            if (result.size() > 0)
                floor = FloorFactory.getFloor( result.get(0));

        }
        catch(Exception e){
            //TODO exception handling
        }

        return floor;

    }*/

    /*
    @RequestMapping("/rooms")
    public List<Room> getCNRooms(){

        List<Room> rooms = new ArrayList<Room>();

        SessionFactory sessionFactory = dbUtilities.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM BuildingInformation.RoomFactory";
        Query query = session.createQuery(hql);
        List<RoomFactory> result = query.list();

        for(Object element : result){

            rooms.add(RoomFactory.getRoom((Room) element));
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

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM BuildingInformation.RoomFactory WHERE name =" + room_name  + " AND building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<Room> result = query.list();

            if(result.size()>0)
                room = RoomFactory.getRoom(result.get(0));

        }catch(Exception e){
            //TODO: Error handling
        }

        return room;

    }*/

}
