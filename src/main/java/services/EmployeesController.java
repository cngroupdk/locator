package services;

import actors.StaffMember;

import actors.StaffMemberFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbConnector;

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

            employees = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM employee",
                 new RowMapper<StaffMember>() {
                    @Override
                    public StaffMember mapRow(ResultSet resultSet, int i) throws SQLException {

                        StaffMember employee = StaffMemberFactory.getStaffMember(resultSet);

                        return employee;
                    }
                }
            );

        }catch(Exception e){
            //TO DO: Error handling
        }

        return employees;

    }

    @RequestMapping("/single_employee")
    public StaffMember getSingleEmployee(@RequestParam(value="first_name") String firstName,
                                         @RequestParam(value="last_name") String lastName){

        StaffMember employee = null;
        List<StaffMember> employees = new ArrayList<StaffMember>();



        try {
            firstName = URLDecoder.decode(firstName, "UTF-8");
            lastName = URLDecoder.decode(lastName, "UTF-8");
            employees = dbConnector.getDefaultJdbcTemplate().query(
                    "SELECT * FROM employee WHERE first_name = " + firstName + "AND last_name = " + lastName,
                    new RowMapper<StaffMember>() {
                        @Override
                        public StaffMember mapRow(ResultSet resultSet, int i) throws SQLException {

                            StaffMember employee = StaffMemberFactory.getStaffMember(resultSet);

                            return employee;
                        }
                    }
            );

        }catch(Exception e){
            //TO DO: Error handling
        }

        if(employees.size() > 0){
            employee = employees.get(0);
        }

        return employee;

    }

}
