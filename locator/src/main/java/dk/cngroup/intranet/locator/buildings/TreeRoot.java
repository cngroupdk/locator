package dk.cngroup.intranet.locator.buildings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The TreeRoot class is used as a starting point to build the front end CN Asset tree structure
 *
 * @author Victor Cano
 */
public class TreeRoot {

    private String name = "CN Group";
    private boolean toggled = true;

    private List<TreeBuilding> children;

    public TreeRoot(){
        children = new ArrayList<TreeBuilding>();
    }

    public void addBuilding(TreeBuilding t){
        children.add(t);
    }

    public List<TreeBuilding> getChildren(){
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

}
