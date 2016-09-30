package realEstate;

/**
 * Created by cano on 29.9.2016.
 */
public class Room {

    private String name;
    private String location;
    private String type;
    private Integer capacity;
    private Integer assignedPeople;

    protected Room(String name, String location, String type,
                   Integer capacity, Integer assignedPeople){
        setName(name);
        setLocation(location);
        setType(type);
        setCapacity(capacity);
        setAssignedPeople(assignedPeople);
    }

    public Room(){
        name = new String();
        location = new String();
        type = new String();
        capacity = new Integer(0);
        assignedPeople = new Integer(0);
    }

    public String getName() {
        return new String(name);
    }

    public void setName(String name) {
        this.name = new String(name);
    }

    public String getLocation() {
        return new String(location);
    }

    public void setLocation(String location) {
        this.location = new String(location);
    }

    public String getType() {
        return new String(type);
    }

    public void setType(String type) {
        this.type = new String(type);
    }

    public Integer getCapacity() {
        return new Integer(capacity);
    }

    public void setCapacity(Integer capacity) {
        this.capacity = new Integer(capacity);
    }


    public Integer getAssignedPeople() {
        return new Integer(assignedPeople);
    }

    public void setAssignedPeople(Integer assignedPeople) {
        this.assignedPeople = new Integer(assignedPeople);
    }

}
