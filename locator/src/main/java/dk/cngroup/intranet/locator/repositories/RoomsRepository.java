package dk.cngroup.intranet.locator.repositories;

import dk.cngroup.intranet.locator.buildingcomponents.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RoomsRepository class that extends the CrudRepository interface.
 *
 * Will return a List of Room objects instanced from the database table linked through
 * the definition of the Room class.
 *
 * @author Victor
 */

@Repository
public interface RoomsRepository extends CrudRepository<Room, Long> {
    /**
     * JPA query to obtain a room by the Name and BuildingId fields
     * @param Name
     * @param BuildingId
     * @return
     */
    List<Room> findByNameAndBuildingId(String Name, String BuildingId);

    /**
     * JPA query to obtain all rooms and order by roomId
     * @return
     */
    Iterable<Room> findAllByOrderByRoomId();
}
