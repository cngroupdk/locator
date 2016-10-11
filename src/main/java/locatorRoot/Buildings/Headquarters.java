package locatorRoot.Buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by cano on 3.10.2016.
 */
@Entity
@DiscriminatorValue("Headquarters")
public class Headquarters extends BuildingFactory {

    public Headquarters(){

    }

    public Headquarters(Building b){
        this.setBuildingId(b.getBuildingId());
        this.setCity(b.getCity());
        this.setName(b.getName());
        this.setPostalCode(b.getPostalCode());
        this.setStreetName(b.getStreetName());
        this.setStreetNumber(b.getStreetNumber());
    }

}
