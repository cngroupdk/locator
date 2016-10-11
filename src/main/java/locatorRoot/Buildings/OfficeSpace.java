package locatorRoot.Buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by cano on 3.10.2016.
 */
@Entity
@DiscriminatorValue("Office Space")
public class OfficeSpace extends BuildingFactory {

    public OfficeSpace(){

    }

    public OfficeSpace(Building b){
        this.setBuildingId(b.getBuildingId());
        this.setCity(b.getCity());
        this.setName(b.getName());
        this.setPostalCode(b.getPostalCode());
        this.setStreetName(b.getStreetName());
        this.setStreetNumber(b.getStreetNumber());
    }

}
