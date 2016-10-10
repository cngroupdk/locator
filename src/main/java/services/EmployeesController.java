package services;

import actors.StaffMember;

import actors.StaffMemberFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbUtilities;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cano on 29.9.2016.
 */

@RestController
public class EmployeesController {

    @RequestMapping("/employees")
    public List<StaffMember> getEmployeeList(){

        List<StaffMember> employees = new ArrayList<StaffMember>();

        try {

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM actors.StaffMemberFactory";
            Query query = session.createQuery(hql);
            List<StaffMember> result = query.list();

            for(Object element : result){

                employees.add(StaffMemberFactory.getStaffMember((StaffMember) element));
            }

        }catch(Exception e){
            //TO DO: Error handling
            e.printStackTrace();
        }

        return employees;

    }

    @RequestMapping("/single_employee")
    public StaffMember getSingleEmployee(@RequestParam(value="first_name") String firstName,
                                         @RequestParam(value="last_name") String lastName){

        StaffMember employee = null;

        try {
            firstName = URLDecoder.decode(firstName, "UTF-8");
            lastName = URLDecoder.decode(lastName, "UTF-8");

            SessionFactory sessionFactory = dbUtilities.getSessionFactory();
            Session session = sessionFactory.openSession();

            String hql = "FROM actors.StaffMemberFactory WHERE first_name = " + firstName + " AND last_name = " + lastName;
            Query query = session.createQuery(hql);
            List<StaffMember> result = query.list();

            if (result.size() > 0)
                employee = StaffMemberFactory.getStaffMember( result.get(0));

        }catch(Exception e){
            //TO DO: Error handling
        }

        return employee;

    }

}
