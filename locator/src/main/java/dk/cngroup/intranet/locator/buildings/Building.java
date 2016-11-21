package dk.cngroup.intranet.locator.buildings;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buildings_building_guid_seq")
    @SequenceGenerator(name = "buildings_building_guid_seq", sequenceName = "buildings_building_guid_seq", allocationSize=1)
    @Column(name="building_guid")
    private Integer buildingGuid;
    @Column(name="building_id")
    private String buildingId;
    private String type;
    private String name;
    private String city;
    private String address;

    public Building(){

    }

    public Building(Building b){
        this.buildingId = b.getBuildingId();
        this.type = b.getType();
        this.name = b.getName();
        this.city = b.getCity();
        this.address = b.getAddress();
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}