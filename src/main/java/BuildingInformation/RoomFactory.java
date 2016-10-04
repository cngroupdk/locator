package BuildingInformation;

import java.sql.ResultSet;

/**
 * Created by cano on 29.9.2016.
 */
public abstract class RoomFactory implements Room{

    private String name;
    private String building_id;
    private Integer floor_id;
    private String type;
    private Integer capacity;
    private Integer assignedPeople;

    protected RoomFactory(String name, String building_id, Integer floor_id, String type,
                          Integer capacity, Integer assignedPeople){
        setName(name);
        setBuildingId(building_id);
        setFloorId(floor_id);
        setType(type);
        setCapacity(capacity);
        setAssignedPeople(assignedPeople);
    }

    public RoomFactory(){
        name = new String();
        building_id = new String();
        floor_id = new Integer(0);
        type = new String();
        capacity = new Integer(0);
        assignedPeople = new Integer(0);
    }

    public static Room getRoom(ResultSet resultSet){

        Room room = null;

        try{
            String type = resultSet.getString("type");

            switch(type){
                case "DevRoom":
                    room = new DevRoom();
                    room = setCommonProperties(resultSet, room);
                    //capacity to add role specific logic here
                    break;
            }
        }
        catch ( Exception e){
            //TO DO: error handling
        }

        return room;

    }

    public static Room setCommonProperties(ResultSet resultSet, Room room){

        try {
            String name = resultSet.getString("name");
            room.setName(name!= null ? name : null);

            String type = resultSet.getString("type");
            room.setType(type!= null ? type : null);

            String building_id = resultSet.getString("building_id");
            room.setBuildingId(building_id!= null ? building_id : null);

            int floor_id = resultSet.getInt("floor_id");
            room.setFloorId(resultSet.wasNull() ? null : floor_id);

            int capacity = resultSet.getInt("capacity");
            room.setCapacity(resultSet.wasNull() ? null : capacity);

            int assigned_people = resultSet.getInt("assigned_people");
            room.setAssignedPeople(resultSet.wasNull() ? null : assigned_people);

        } catch( Exception e){
            // TO DO: error handling
        }

        return room;
    }

    public String getName() {
        return new String(name);
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public String getBuildingId() {
        return new String(building_id);
    }

    public void setBuildingId(String location) {
        this.building_id = new String(location);
    }

    public Integer getFloorId() {
        return new Integer(floor_id);
    }

    public void setFloorId(Integer location) {
        this.floor_id = new Integer(location);
    }

    public String getType() {
        return new String(type);
    }

    public void setType(String type) {
        this.type = new String(type);
    }

    public Integer getCapacity() {
        return new Integer(capacity);
    }

    public void setCapacity(Integer capacity) {
        this.capacity = new Integer(capacity);
    }


    public Integer getAssignedPeople() {
        return new Integer(assignedPeople);
    }

    public void setAssignedPeople(Integer assignedPeople) {
        this.assignedPeople = new Integer(assignedPeople);
    }

}
