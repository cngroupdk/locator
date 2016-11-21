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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rooms_room_id_seq")
    @SequenceGenerator(name = "rooms_room_id_seq", sequenceName = "rooms_room_id_seq", allocationSize=1)
    @Column(name = "room_id")
    private Integer roomId;
    private String name;
    private String type;
    @Column(name = "floor_name")
    private String floorName;
    @Column(name = "building_id")
    private String buildingId;
    @Column(name = "style_top")
    private String styleTop;
    @Column(name = "style_left")
    private String styleLeft;

    public Room(){

    }

    public Room(Room r){
        this.name = r.name;
        this.type = r.type;
        this.floorName = r.floorName;
        this.buildingId = r.buildingId;
        this.styleTop = r.styleTop;
        this.styleLeft = r.styleLeft;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if ((obj == null) || (getClass() != obj.getClass())) {
            result = false;
        } // end if
        else {
            Room otherPerson = (Room) obj;
            result = name.equals(otherPerson.name) && buildingId.equals(otherPerson.buildingId)
                    && floorName.equals(otherPerson.floorName) && type.equals(otherPerson.type)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode() {

        int result = 0;

        result = name.hashCode()
                * buildingId.hashCode()
                * floorName.hashCode()
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getStyleTop() {
        return styleTop;
    }

    public void setStyleTop(String styleTop) {
        this.styleTop = styleTop;
    }

    public String getStyleLeft() {
        return styleLeft;
    }

    public void setStyleLeft(String styleLeft) {
        this.styleLeft = styleLeft;
    }
}
