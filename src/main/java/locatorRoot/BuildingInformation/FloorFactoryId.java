package locatorRoot.BuildingInformation;

import java.io.Serializable;

/**
 * Created by cano on 11.10.2016.
 */
public abstract class FloorFactoryId implements Floor, Serializable{

    private Integer floorId;
    private String buildingId;


    protected FloorFactoryId(){

    }

    @Override
    public boolean equals(Object obj){
        boolean result;
        if((obj == null) || (getClass() != obj.getClass())){
            result = false;
        } // end if
        else{
            FloorFactoryId otherPerson = (FloorFactoryId)obj;
            result = buildingId.equals(otherPerson.buildingId) && floorId.equals(otherPerson.floorId);
        } // end else

        return result;
    }

    @Override
    public int hashCode(){

        int result = 0;

        result = buildingId.hashCode()
                * floorId.hashCode();

        return result;

    }


    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

}
