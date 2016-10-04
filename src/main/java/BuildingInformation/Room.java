package BuildingInformation;

/**
 * Created by cano on 3.10.2016.
 */
public interface Room {

    public String getName();

    public void setName(String name);

    public String getBuildingId();

    public void setBuildingId(String location);

    public Integer getFloorId();

    public void setFloorId(Integer location);

    public String getType();

    public void setType(String type);

    public Integer getCapacity();

    public void setCapacity(Integer capacity);

    public Integer getAssignedPeople();

    public void setAssignedPeople(Integer assignedPeople);

}
