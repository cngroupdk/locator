package BuildingInformation;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cano on 29.9.2016.
 */
@Entity
@Table(name="rooms")
public class DevRoom extends RoomFactory implements Serializable {

    public DevRoom(){

    }

    public DevRoom(Room r){
        this.setFloorId(r.getFloorId());
        this.setBuildingId(r.getBuildingId());
        this.setAssignedPeople(r.getAssignedPeople());
        this.setCapacity(r.getCapacity());
        this.setName(r.getName());
        this.setType(r.getType());
    }

}
