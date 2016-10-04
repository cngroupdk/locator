package BuildingInformation;

/**
 * Created by cano on 3.10.2016.
 */
public interface Floor {


    public Integer getFloorId();

    public void setFloorId(Integer floorId);

    public String getBuildingId();

    public void setBuildingId(String buildingId);

    public String getFloorNumber();

    public void setFloorNumber(String floorNumber);

    public Integer getRoomsNumber();

    public void setRoomsNumber(Integer roomsNumber);

    public String getFloorplanUrl();

    public void setFloorplanUrl(String floorplanUrl);

    public String getType();

    public void setType(String type);
}
