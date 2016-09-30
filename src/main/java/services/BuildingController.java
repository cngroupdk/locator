package services;

import org.springframework.jdbc.core.RowMapper;
import realEstate.Building;
import realEstate.Floor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbConnector;

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

                        Building building = new Building();
                        building.setBuildingId(resultSet.getString("building_id"));
                        building.setName(resultSet.getString("name"));
                        building.setCity(resultSet.getString("city"));
                        building.setPostalCode(resultSet.getString("postal_code"));
                        building.setStreetName(resultSet.getString("street_name"));
                        building.setStreetNumber(resultSet.getString("street_number"));
                        return building;
                    }
                }
        );

        return buildings;

    }

    @RequestMapping("/floors")
    public List<Floor> getCNFloors(){

        List<Floor> floors = new ArrayList<Floor>();

        floors = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM floors",
                new RowMapper<Floor>() {
                    @Override
                    public Floor mapRow(ResultSet resultSet, int i) throws SQLException {

                        Floor floor = new Floor();
                        floor.setFloorId(resultSet.getInt("floor_id"));
                        floor.setBuildingId(resultSet.getInt("building_id"));
                        floor.setFloorNumber(resultSet.getString("floor_number"));
                        floor.setRoomsNumber(resultSet.getString("rooms_number"));
                        floor.setFloorplanUrl(resultSet.getString("floorplan_url"));

                        return floor;
                    }
                }
        );

        return floors;

    }

}
