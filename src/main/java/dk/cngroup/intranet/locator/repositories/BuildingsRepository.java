package dk.cngroup.intranet.locator.repositories;

import dk.cngroup.intranet.locator.buildings.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BuildingsRepository class that extends the CrudRepository interface.
 *
 * Will return a List of Building objects instanced from the database table linked through
 * the definition of the Building class.
 *
 * @author Victor Cano
 */
@Repository
public interface BuildingsRepository extends CrudRepository<Building, Long> {

    /**
     * JPA query to obtain a building by the buildingId field
     * @param buildingId
     * @return
     */
    List<Building> findByBuildingId(String buildingId);
    /**
     * JPA query to obtain all employees and order by employeeGuid
     * @return
     */
    Iterable<Building> findAllByOrderByBuildingGuid();

}
