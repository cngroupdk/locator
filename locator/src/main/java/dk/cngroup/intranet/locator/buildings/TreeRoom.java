package dk.cngroup.intranet.locator.buildings;

/**
 *
 * The TreeRoom class is used to build the front end CN Asset tree structure and represents a room
 *
 * @author Victor Cano
 */
public class TreeRoom {

    private String name;
    private Integer roomId;
    private String buildingId;
    private String floorName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

}
