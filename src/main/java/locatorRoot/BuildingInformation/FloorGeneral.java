package locatorRoot.BuildingInformation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by cano on 3.10.2016.
 */
@Entity
@DiscriminatorValue("General")
public class FloorGeneral extends FloorFactory implements Serializable{

    public FloorGeneral(){

    }

    public FloorGeneral(Floor f){

        this.setBuildingId(f.getBuildingId());
        this.setFloorId(f.getFloorId());
        this.setFloorNumber(f.getFloorNumber());
        this.setFloorplanUrl(f.getFloorplanUrl());
        this.setRoomsNumber(f.getRoomsNumber());

    }

}
