package realEstate;

/**
 * Created by cano on 29.9.2016.
 */
public class DevRoom extends Room {
    public DevRoom()
    {
        super();
        this.setType("Developers Room");
    }

    public DevRoom(String name, String location, Integer capacity)
    {
        super(name, location, "Developers Room", capacity, 0);
    }
}
