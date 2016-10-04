package Buildings;

import java.sql.ResultSet;

/**
 * Created by cano on 30.9.2016.
 */
public class BuildingFactory implements Building {

    private String buildingId;
    private String type;
    private String name;
    private String city;
    private String postalCode;
    private String streetName;
    private String streetNumber;

    public static Building getBuilding(ResultSet resultSet){

        Building building = null;

        try{
            String type = resultSet.getString("type");

            switch(type){
                case "Headquarters":
                    building = new Headquarters();
                    building = setCommonProperties(resultSet, building);
                    //capacity to add role specific logic here
                    break;
                case "Office Space":
                    building = new OfficeSpace();
                    building = setCommonProperties(resultSet, building);
                    //capacity to add role specific logic here
                    break;
            }
        }
        catch ( Exception e){
            //TO DO: error handling
        }

        return building;

    }

    public static Building setCommonProperties(ResultSet resultSet, Building building){

        try {

            String type = resultSet.getString("type");
            building.setType(type!= null ? type : null);

            String building_id = resultSet.getString("building_id");
            building.setBuildingId(building_id!= null ? building_id : null);

            String name = resultSet.getString("name");
            building.setName(name!= null ? name : null);

            String city = resultSet.getString("city");
            building.setCity(city != null ? city : null);

            String postal_code = resultSet.getString("postal_code");
            building.setPostalCode(postal_code!= null ? postal_code : null);

            String street_name = resultSet.getString("street_name");
            building.setStreetName(street_name!= null ? street_name : null);

            String street_number = resultSet.getString("street_number");
            building.setStreetNumber(street_number != null ? street_number : null);


        } catch( Exception e){
            // TO DO: error handling
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
