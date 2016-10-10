package BuildingInformation;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Created by cano on 29.9.2016.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Table(name="rooms")
public abstract class RoomFactory implements Room, Serializable{

    @Id
    private String name;
    @Column(updatable = false, insertable = false)
    private String type;
    private Integer capacity;
    @Column(name = "assigned_people")
    private Integer assignedPeople;
    @Id
    @Column(name = "floor_id")
    private Integer floorId;
    @Id
    @Column(name = "building_id")
    private String buildingId;

    public RoomFactory(){

    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            RoomFactory otherPerson = (RoomFactory)obj;
            result = name.equals(otherPerson.name) && buildingId.equals(otherPerson.buildingId)
                    && floorId.equals(otherPerson.floorId) && type.equals(otherPerson.type)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = name.hashCode() * buildingId.hashCode() * buildingId.hashCode() * floorId.hashCode()
                * type.hashCode();

        return result;

    }
    public static Room getRoom(ResultSet resultSet){

        Room room = null;

        try{
            String type = resultSet.getString("type");

            switch(type){
                case "Development":
                    room = new RoomDev();
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
                case "Development":
                    room = new RoomDev(r);
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
