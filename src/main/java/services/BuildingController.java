package services;

import Buildings.Building;
import org.springframework.jdbc.core.RowMapper;
import Buildings.BuildingFactory;
import BuildingInformation.Floor;
import BuildingInformation.FloorFactory;
import BuildingInformation.Room;
import BuildingInformation.RoomFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbConnector;

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

        buildings = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM building",
                new RowMapper<Building>() {
                    @Override
                    public Building mapRow(ResultSet resultSet, int i) throws SQLException {

                        Building building = BuildingFactory.getBuilding(resultSet);

                        return building;
                    }
                }
        );

        return buildings;

    }

    @RequestMapping("/single_building")
    public Building getSingleCNBuilding(@RequestParam(value="building_id") String buildingId){

        Building building = null;
        List<Building> buildings = new ArrayList<Building>();

        try {
            buildingId = URLDecoder.decode(buildingId, "UTF-8");
            buildings = dbConnector.getDefaultJdbcTemplate().query(
                    "SELECT * FROM building WHERE building_id =" + buildingId,
                    new RowMapper<Building>() {
                        @Override
                        public Building mapRow(ResultSet resultSet, int i) throws SQLException {

                            Building building = BuildingFactory.getBuilding(resultSet);

                            return building;
                        }
                    }
            );
        }catch(Exception e){
            //TODO:Exception Handling
        }

        if(buildings.size() > 0){
            building = buildings.get(0);
        }

        return building;

    }



    @RequestMapping("/floors")
    public List<Floor> getCNFloors(){

        List<Floor> floors = new ArrayList<Floor>();

        floors = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM floors",
                new RowMapper<Floor>() {
                    @Override
                    public Floor mapRow(ResultSet resultSet, int i) throws SQLException {

                        Floor floor = FloorFactory.getFloor(resultSet);

                        return floor;
                    }
                }
        );

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
            floors = dbConnector.getDefaultJdbcTemplate().query(
                    "SELECT * FROM floors WHERE floor_number =" + floorNumber + "AND building_id =" + buildingId,
                    new RowMapper<Floor>() {
                        @Override
                        public Floor mapRow(ResultSet resultSet, int i) throws SQLException {

                            Floor floor = FloorFactory.getFloor(resultSet);

                            return floor;
                        }
                    }
            );
        }
        catch(Exception e){
            //TODO exception handling
        }

        if(floors.size() > 0){
            floor = floors.get(0);
        }

        return floor;

    }

    @RequestMapping("/rooms")
    public List<Room> getCNRooms(){

        List<Room> rooms = new ArrayList<Room>();

        rooms = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM rooms",
                new RowMapper<Room>() {
                    @Override
                    public Room mapRow(ResultSet resultSet, int i) throws SQLException {

                        Room room = RoomFactory.getRoom(resultSet);

                        return room;
                    }
                }
        );

        return rooms;

    }

    @RequestMapping("/single_room")
    public Room getSingleCNRoom(@RequestParam(value="room_name") String room_name,
                                @RequestParam(value="building_id") String buildingId){

        Room room = null;
        List<Room> rooms = new ArrayList<Room>();

        try {

            room_name = URLDecoder.decode(room_name, "UTF-8");
            buildingId = URLDecoder.decode(buildingId, "UTF-8");
            rooms = dbConnector.getDefaultJdbcTemplate().query(
                    "SELECT * FROM rooms WHERE name =" + room_name  + "AND building_id =" + buildingId,
                    new RowMapper<Room>() {
                        @Override
                        public Room mapRow(ResultSet resultSet, int i) throws SQLException {

                            Room room = RoomFactory.getRoom(resultSet);

                            return room;
                        }
                    }
            );
        }catch(Exception e){

        }

        if(rooms.size() > 0){
            room = rooms.get(0);
        }

        return room;

    }

}
