package locatorRoot.repositories;

import locatorRoot.BuildingInformation.RoomFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import locatorRoot.BuildingInformation.Room;

import java.util.List;

/**
 * Created by cano on 11.10.2016.
 */
@Repository
public interface RoomsRepository extends CrudRepository<RoomFactory, Long> {
    List<RoomFactory> findByNameAndBuildingId(String Name, String BuildingId);
}
