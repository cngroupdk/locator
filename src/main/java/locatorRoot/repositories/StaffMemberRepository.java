package locatorRoot.repositories;

import locatorRoot.actors.StaffMemberFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cano on 11.10.2016.
 */

@Repository
public interface StaffMemberRepository extends CrudRepository <StaffMemberFactory, Long> {

    List<StaffMemberFactory> findByFirstNameAndLastName(String firstName, String lastName);

}
