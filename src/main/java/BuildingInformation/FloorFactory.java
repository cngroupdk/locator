package BuildingInformation;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Created by cano on 30.9.2016.
 */

@Entity
@Inheritance
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Table(name="floors")
public abstract class FloorFactory implements Floor, Serializable {

    @Id
    @Column(name = "floor_id")
    private Integer floorId;
    @Column(updatable = false, insertable = false)
    private String type;
    @Id
    @Column(name = "building_id")
    private String buildingId;
    @Column(name = "floor_number")
    private String floorNumber;
    @Column(name = "rooms_number")
    private Integer roomsNumber;
    @Column(name = "floorplan_url")
    private String floorplanUrl;

    public static Floor getFloor(Floor f){

        Floor floor = null;
        try {
            String type = f.getType();

            switch(type){
                case "General":
                    floor = new FloorGeneral(f);
                    break;
            }

        }catch(Exception e){
            //TODO: Error handling
        }
        return floor;
    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            FloorFactory otherPerson = (FloorFactory)obj;
            result = buildingId.equals(otherPerson.buildingId)
                    && floorNumber.equals(otherPerson.floorNumber) && type.equals(otherPerson.type)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = buildingId.hashCode()
                * floorNumber.hashCode()
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
