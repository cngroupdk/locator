package dk.cngroup.intranet.locator.buildingcomponents;

import javax.persistence.*;

/**
 * Floor class that represents such a component of a building.
 *
 * The Floor class implements JPA annotations and is mapped to the 'floors' database table
 *
 * @author Victor Cano
 */

@Entity
@Table(name="floors")
public class Floor {

    @Id
    @Column(name = "floor_id")
    private Integer floorId;
    @Column(name = "floor_name")
    private String floorName;
    @Column(name = "rooms_number")
    private Integer roomsNumber;
    @Column(name = "floorplan_url")
    private String floorplanUrl;
    private String type;
    @Column(name = "building_id")
    private String buildingId;

    public Floor(){

    }

    public Floor(Floor f){

        this.setBuildingId(f.getBuildingId());
        this.setFloorId(f.getFloorId());
        this.setFloorName(f.getFloorName());
        this.setFloorplanUrl(f.getFloorplanUrl());
        this.setRoomsNumber(f.getRoomsNumber());

    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            Floor otherPerson = (Floor)obj;
            result = buildingId.equals(otherPerson.buildingId)
                    && floorName.equals(otherPerson.floorName) && type.equals(otherPerson.type)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = buildingId.hashCode()
                * floorName.hashCode()
                * type.hashCode();

        return result;

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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
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
