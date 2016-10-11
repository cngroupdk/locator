package locatorRoot.repositories;

import locatorRoot.BuildingInformation.FloorFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cano on 11.10.2016.
 */

@Repository
public interface FloorsRepository extends CrudRepository<FloorFactory, Long>{
    List<FloorFactory> findByFloorNumberAndBuildingId(String FloorNumber, String BuildingId);
}
