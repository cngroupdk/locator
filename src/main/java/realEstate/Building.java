package realEstate;

/**
 * Created by cano on 30.9.2016.
 */
public class Building {

    private String buildingId;
    private String name;
    private String city;
    private String postalCode;
    private String streetName;
    private String streetNumber;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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
