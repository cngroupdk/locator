package services;

import Buildings.Building;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.RowMapper;
import Buildings.BuildingFactory;
import BuildingInformation.Floor;
import BuildingInformation.FloorFactory;
import BuildingInformation.Room;
import BuildingInformation.RoomFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbUtilities;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by cano on 30.9.2016.
 */
@RestController
public class BuildingController {

    @RequestMapping("/buildings")
    public List<Building> getCNBuildings(){

        List<Building> buildings = new ArrayList<Building>();

        try {

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM OfficeSpace";
            Query query = session.createQuery(hql);
            List<?> result = query.list();

            for(Object element : result){

                buildings.add(BuildingFactory.getBuilding((Building) element));
            }

        }catch(Exception e){
            //TO DO: Error handling
            e.printStackTrace();
        }

        return buildings;

    }

    @RequestMapping("/single_building")
    public Building getSingleCNBuilding(@RequestParam(value="building_id") String buildingId){

        Building building = null;
        List<Building> buildings = new ArrayList<Building>();

        try {
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM OfficeSpace WHERE building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<?> result = query.list();

            if (result.size() > 0)
                building = BuildingFactory.getBuilding((Building) result.get(0));

        }catch(Exception e){
            //TODO:Exception Handling
        }

        return building;

    }



    @RequestMapping("/floors")
    public List<Floor> getCNFloors(){

        List<Floor> floors = new ArrayList<Floor>();

        SessionFactory sessionFactory = dbUtilities.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM GeneralFloor";
        Query query = session.createQuery(hql);
        List<?> result = query.list();

        for(Object element : result){

            floors.add(FloorFactory.getFloor((Floor) element));
        }

        return floors;

    }

    @RequestMapping("/single_floor")
    public Floor getSingleCNFloor(@RequestParam(value="floor_number") String floorNumber,
                                  @RequestParam(value="building_id") String buildingId){

        List<Floor> floors = new ArrayList<Floor>();
        Floor floor = null;

        try {
            floorNumber = URLDecoder.decode(floorNumber, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM GeneralFloor WHERE floor_number =" + floorNumber + " AND building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<?> result = query.list();


            if (result.size() > 0)
                floor = FloorFactory.getFloor((Floor) result.get(0));

        }
        catch(Exception e){
            //TODO exception handling
        }

        return floor;

    }

    @RequestMapping("/rooms")
    public List<Room> getCNRooms(){

        List<Room> rooms = new ArrayList<Room>();

        SessionFactory sessionFactory = dbUtilities.getSessionFactory();
        Session session = sessionFactory.openSession();

        String hql = "FROM DevRoom";
        Query query = session.createQuery(hql);
        List<?> result = query.list();

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

            String hql = "FROM DevRoom WHERE name =" + room_name  + " AND building_id =" + buildingId;
            Query query = session.createQuery(hql);
            List<?> result = query.list();

            if(result.size()>0)
                room = RoomFactory.getRoom((Room) result.get(0));

        }catch(Exception e){
            //TODO: Error handling
        }

        return room;

    }

}
