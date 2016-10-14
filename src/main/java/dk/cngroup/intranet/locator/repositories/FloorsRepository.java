package dk.cngroup.intranet.locator.repositories;

import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
     * @param FloorNumber
     * @param BuildingId
     * @return
     */
    List<Floor> findByFloorNumberAndBuildingId(String FloorNumber, String BuildingId);
}
