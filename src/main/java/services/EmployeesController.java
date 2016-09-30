package services;

import actors.Person;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utilities.dbConnector;

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
    public List<Person> getEmployeeList(){

        List<Person> employees = new ArrayList<Person>();

        try {

            employees = dbConnector.getDefaultJdbcTemplate().query(
                "SELECT * FROM employee",
                 new RowMapper<Person>() {
                    @Override
                    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

                        Person employee = new Person();
                        employee.setId(resultSet.getString("employee_id"));
                        employee.setManagerId(resultSet.getString("manager_id"));
                        employee.setFirstName(resultSet.getString("first_name"));
                        employee.setLastName(resultSet.getString("last_name"));
                        employee.setAbbreviation(resultSet.getString("abbreviation"));
                        employee.setRole(resultSet.getString("role"));
                        employee.setEmail(resultSet.getString("email"));
                        employee.setLocation(resultSet.getString("location"));
                        employee.setDetail(resultSet.getString("detail"));
                        return employee;
                    }
                }
            );

        }catch(Exception e){
            //TO DO: Error handling
        }

        return employees;

    }

}
