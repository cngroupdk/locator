package locatorRoot.BuildingInformation;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cano on 29.9.2016.
 */
@Entity
@DiscriminatorValue("Development")
public class RoomDev extends RoomFactory implements Serializable {

    public RoomDev(){

    }

    public RoomDev(Room r){
        this.setFloorId(r.getFloorId());
        this.setBuildingId(r.getBuildingId());
        this.setAssignedPeople(r.getAssignedPeople());
        this.setCapacity(r.getCapacity());
        this.setName(r.getName());
    }

}
