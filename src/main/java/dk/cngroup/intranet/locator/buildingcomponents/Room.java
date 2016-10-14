package dk.cngroup.intranet.locator.buildingcomponents;

import javax.persistence.*;

/**
 * Room class that represents such a component of a building.
 *
 * The Room class implements JPA annotations and is mapped to the 'rooms' database table
 *
 * @author Victor Cano
 */
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_id")
    private Integer roomId;
    private String name;
    @Column(updatable = false, insertable = false)
    private String type;
    private Integer capacity;
    @Column(name = "assigned_people")
    private Integer assignedPeople;
    @Column(name = "floor_id")
    private Integer floorId;
    @Column(name = "building_id")
    private String buildingId;

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if ((obj == null) || (getClass() != obj.getClass())) {
            result = false;
        } // end if
        else {
            Room otherPerson = (Room) obj;
            result = name.equals(otherPerson.name) && buildingId.equals(otherPerson.buildingId)
                    && floorId.equals(otherPerson.floorId) && type.equals(otherPerson.type)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode() {

        int result = 0;

        result = name.hashCode()
                * buildingId.hashCode()
                * floorId.hashCode()
                * type.hashCode();

        return result;

    }


    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAssignedPeople() {
        return assignedPeople;
    }

    public void setAssignedPeople(Integer assignedPeople) {
        this.assignedPeople = assignedPeople;
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

}
