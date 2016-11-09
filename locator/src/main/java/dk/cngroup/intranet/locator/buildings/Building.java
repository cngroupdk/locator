package dk.cngroup.intranet.locator.buildings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Building class that represents such an entity.
 *
 * The Building class implements JPA annotations and is mapped to the 'building' database table
 *
 * @author Victor Cano
 */
@Entity
@Table(name="buildings")
public class Building {

    @Id
    @Column(name="building_guid")
    private Integer buildingGuid;
    @Column(name="building_id")
    private String buildingId;
    private String type;
    private String name;
    private String city;
    @Column(name="postal_code")
    private String postalCode;
    @Column(name="street_name")
    private String streetName;
    @Column(name="street_number")
    private String streetNumber;

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            Building otherPerson = (Building)obj;
            result = name.equals(otherPerson.name) && buildingId.equals(otherPerson.buildingId)
                    && type.equals(otherPerson.type) && city.equals(otherPerson.city)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = name.hashCode()
                * buildingId.hashCode()
                * type.hashCode()
                * city.hashCode();

        return result;

    }

    public Integer getBuildingGuid() {
        return buildingGuid;
    }

    public void setBuildingGuid(Integer buildingGuid) {
        this.buildingGuid = buildingGuid;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String street) {
        this.streetName = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String number) {
        this.streetNumber = number;
    }

}