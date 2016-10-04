package BuildingInformation;

import java.sql.ResultSet;

/**
 * Created by cano on 30.9.2016.
 */
public abstract class FloorFactory implements Floor {

    private Integer floorId;
    private String type;
    private String buildingId;
    private String floorNumber;
    private Integer roomsNumber;
    private String floorplanUrl;

    public static Floor getFloor(ResultSet resultSet){
        Floor floor = null;
        try {
            String type = resultSet.getString("type");

            switch(type){
                case "General":
                    floor = new GeneralFloor();
                    floor = setCommonProperties(resultSet, floor);
                    break;
            }

        }catch(Exception e){
            //TODO: Error handling
        }
        return floor;
    }

    public static Floor setCommonProperties(ResultSet resultSet, Floor floor){

        try {
            int floor_id = resultSet.getInt("floor_id");
            floor.setFloorId(resultSet.wasNull() ? null : floor_id);

            int rooms_number = resultSet.getInt("rooms_number");
            floor.setRoomsNumber(resultSet.wasNull() ? null : rooms_number);

            String type = resultSet.getString("type");
            floor.setType(type!= null ? type : null);

            String building_id = resultSet.getString("building_id");
            floor.setBuildingId(building_id!= null ? building_id : null);

            String floor_number = resultSet.getString("floor_number");
            floor.setFloorNumber(floor_number!= null ? floor_number : null);

            String floorplan_url = resultSet.getString("floorplan_url");
            floor.setFloorplanUrl(floorplan_url!= null ? floorplan_url : null);

        } catch (Exception e){
            // TODO: Error handling
        }

        return floor;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(Integer roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public String getFloorplanUrl() {
        return floorplanUrl;
    }

    public void setFloorplanUrl(String floorplanUrl) {
        this.floorplanUrl = floorplanUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
