package BuildingInformation;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.ResultSet;

/**
 * Created by cano on 29.9.2016.
 */
@MappedSuperclass
public abstract class RoomFactory implements Room{

    @Id
    private String name;
    @Id
    @Column(name = "building_id")
    private String buildingId;
    @Id
    @Column(name = "floor_id")
    private Integer floorId;
    private String type;
    private Integer capacity;
    @Column(name = "assigned_people")
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
        buildingId = new String();
        floorId = new Integer(0);
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

    public static Room getRoom(Room r){

        Room room = null;

        try{
            String type = r.getType();

            switch(type){
                case "DevRoom":
                    room = new DevRoom(r);
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
        return new String(buildingId);
    }

    public void setBuildingId(String location) {
        this.buildingId = new String(location);
    }

    public Integer getFloorId() {
        return new Integer(floorId);
    }

    public void setFloorId(Integer location) {
        this.floorId = new Integer(location);
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
