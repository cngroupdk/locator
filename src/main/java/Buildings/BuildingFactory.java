package Buildings;

import javax.persistence.*;
import java.sql.ResultSet;

/**
 * Created by cano on 30.9.2016.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Table(name="building")
public class BuildingFactory implements Building {

    @Id
    @Column(name="building_id")
    private String buildingId;
    @Column(updatable = false, insertable = false)
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
            BuildingFactory otherPerson = (BuildingFactory)obj;
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

    public static Building getBuilding(Building b){

        Building building = null;

        try{
            String type = b.getType();

            switch(type){
                case "Headquarters":
                    building = new Headquarters(b);
                    break;
                case "Office Space":
                    building = new OfficeSpace(b);
                    break;
            }
        }
        catch ( Exception e){
            //TO DO: error handling
        }

        return building;

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
