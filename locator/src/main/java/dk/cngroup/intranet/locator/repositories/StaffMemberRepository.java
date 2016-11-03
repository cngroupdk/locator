package dk.cngroup.intranet.locator.repositories;

import dk.cngroup.intranet.locator.actors.StaffMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StaffMemberRepository class that extends the CrudRepository interface.
 *
 * Will return a List of StaffMember objects instanced from the database table linked through
 * the definition of the StaffMember class.
 *
 * @author Victor
 */

@Repository
public interface StaffMemberRepository extends CrudRepository <StaffMember, Long> {

    /**
     * JPA query to obtain an employee by the firstName and lastName fields
     * @param firstName
     * @param lastName
     * @return
     */
    List<StaffMember> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * JPA query to obtain all employees and order by employeeGuid
     * @return
     */
    List<StaffMember> findAllByOrderByEmployeeGuid();
}
