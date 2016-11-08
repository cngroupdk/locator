package dk.cngroup.intranet.locator.buildings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cano on 8.11.2016.
 */
public class TreeRoot {

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

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

}
