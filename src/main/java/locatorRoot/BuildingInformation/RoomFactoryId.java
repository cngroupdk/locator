package locatorRoot.BuildingInformation;

import java.io.Serializable;

/**
 * Created by cano on 11.10.2016.
 */
public abstract class RoomFactoryId implements Room, Serializable{

    private String name;
    private String buildingId;
    private Integer floorId;
    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            RoomFactoryId otherPerson = (RoomFactoryId)obj;
            result = name.equals(otherPerson.name) && buildingId.equals(otherPerson.buildingId)
                    && floorId.equals(otherPerson.floorId)
            ;
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = name.hashCode()
                * buildingId.hashCode()
                * floorId.hashCode();

        return result;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Integer getFloorId() {
        return floorId;
    }

    @Override
    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    @Override
    public String getBuildingId() {
        return buildingId;
    }

    @Override
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }


}
