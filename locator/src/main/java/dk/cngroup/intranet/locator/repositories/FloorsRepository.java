package dk.cngroup.intranet.locator.repositories;

import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FloorsRepository class that extends the CrudRepository interface.
 *
 * Will return a List of Floor objects instanced from the database table linked through
 * the definition of the Floor class.
 *
 * @author Victor
 */

@Repository
public interface FloorsRepository extends CrudRepository<Floor, Long>{
    /**
     * JPA query to obtain a floor by the floorNumber and BuildingId fields
     * @param FloorName
     * @param BuildingId
     * @return
     */
    List<Floor> findByFloorNameAndBuildingId(String FloorName, String BuildingId);

    /**
     * JPA query to obtain a floor by the BuildingId field
     * @param BuildingId
     * @return
     */
    List<Floor> findByBuildingId(String BuildingId);


    /**
     * JPA query to obtain a floor by the BuildingId field
     * @param FloorId
     * @return
     */
    Floor findByFloorId(Integer FloorId);

    /**
     * JPA query to obtain all floors and order by floorId
     * @return
     */
    Iterable<Floor> findAllByOrderByFloorId();

    /**
     * JPA update to delete a floor by buildingId
     * @return
     */
    @Transactional
    List<Floor> removeByBuildingId(String BuildingId);
}
