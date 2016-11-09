package dk.cngroup.intranet.locator.buildings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cano on 8.11.2016.
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
