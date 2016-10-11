package locatorRoot.repositories;

import locatorRoot.Buildings.BuildingFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cano on 11.10.2016.
 */
@Repository
public interface BuildingsRepository extends CrudRepository<BuildingFactory, Long> {

    List<BuildingFactory> findByBuildingId(String buildingId);

}
