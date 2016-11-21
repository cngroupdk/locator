package dk.cngroup.intranet.locator.buildings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The TreeBuilding class is used to build the front end CN Asset tree structure and represents a building
 *
 * @author Victor Cano
 */
public class TreeBuilding {


    private Integer buildingGuid;
    private String name;
    private String buildingId;
    private boolean toggled = false;
    private List<TreeFloor> children;

    public TreeBuilding(){
        children = new ArrayList<TreeFloor>();
    }

    public void addFloor(TreeFloor t){
        children.add(t);
    }

    public List<TreeFloor> getChildren(){
        return children;
    }

    public Integer getBuildingGuid() {
        return buildingGuid;
    }

    public void setBuildingGuid(Integer buildingGuid) {
        this.buildingGuid = buildingGuid;
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
