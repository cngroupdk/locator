package dk.cngroup.intranet.locator;

import dk.cngroup.intranet.locator.services.BuildingController;
import dk.cngroup.intranet.locator.services.EmployeesController;
import dk.cngroup.intranet.locator.services.FloorsController;
import dk.cngroup.intranet.locator.services.RoomsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cano on 20.10.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllersTest {

    @Autowired
    private BuildingController buildingController;
    @Autowired
    private EmployeesController employeesController;
    @Autowired
    private FloorsController floorsController;
    @Autowired
    private RoomsController roomsController;

    @Test
    public void buildingContextLoads() throws Exception{
        assertThat(buildingController).isNotNull();
    }

    @Test
    public void employeeContextLoads() throws Exception{
        assertThat(employeesController).isNotNull();
    }

    @Test
    public void floorsContextLoads() throws Exception{
        assertThat(floorsController).isNotNull();
    }

    @Test
    public void roomsContextLoads() throws Exception{
        assertThat(roomsController).isNotNull();
    }
}
