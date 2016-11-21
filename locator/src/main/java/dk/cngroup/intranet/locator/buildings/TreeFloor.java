package dk.cngroup.intranet.locator.buildings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The TreeFloor class is used to build the front end CN Asset tree structure and represents a floor
 *
 * @author Victor Cano
 */
public class TreeFloor {

    private Integer floorId;
    private String name;
    private String buildingId;

    private boolean toggled = false;
    private List<TreeRoom> children;

    public TreeFloor(){
        children = new ArrayList<TreeRoom>();
    }

    public void addRoom(TreeRoom t){
        children.add(t);
    }

    public List<TreeRoom> getChildren(){
        return children;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

}
