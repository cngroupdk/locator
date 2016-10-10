package BuildingInformation;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by cano on 3.10.2016.
 */
@Entity
@Table(name="floors")
public class GeneralFloor extends FloorFactory implements Serializable{

    public GeneralFloor(){

    }

    public GeneralFloor(Floor f){

        this.setType(f.getType());
        this.setBuildingId(f.getBuildingId());
        this.setFloorId(f.getFloorId());
        this.setFloorNumber(f.getFloorNumber());
        this.setFloorplanUrl(f.getFloorplanUrl());
        this.setRoomsNumber(f.getRoomsNumber());

    }

}
