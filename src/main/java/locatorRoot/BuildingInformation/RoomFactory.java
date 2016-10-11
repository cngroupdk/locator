package locatorRoot.BuildingInformation;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cano on 29.9.2016.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Table(name="rooms")
@IdClass(RoomFactoryId.class)
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

        result = name.hashCode()
                * buildingId.hashCode()
                * floorId.hashCode()
                * type.hashCode();

        return result;

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
