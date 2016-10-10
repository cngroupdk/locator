package Buildings;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by cano on 3.10.2016.
 */
@Entity
@Table(name="building")
public class Headquarters extends BuildingFactory {

    public Headquarters(){

    }

    public Headquarters(Building b){
        this.setBuildingId(b.getBuildingId());
        this.setCity(b.getCity());
        this.setName(b.getName());
        this.setPostalCode(b.getPostalCode());
        this.setType(b.getType());
        this.setStreetName(b.getStreetName());
        this.setStreetNumber(b.getStreetNumber());
    }

}
