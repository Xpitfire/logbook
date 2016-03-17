package swt6.ue3.logbook.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.PermanentEmployee;


import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/swt6/ue3/logbook/test/applicationContext-test.xml"})
@Transactional(transactionManager = "transactionManagerJpa")
@Rollback
public class EmployeeDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
    }

    @Test
    public void testCountEmployee() {
        employeeRepo.save(permanentEmployee1);
        employeeRepo.save(permanentEmployee2);
        employeeRepo.save(temporaryEmployee1);
        assertTrue(employeeRepo.count() == 3);
    }

    @Test
    public void testInsertPermanentEmployee() {
        Employee e = employeeRepo.save(permanentEmployee1);
        assertNotNull(e.getId());
    }

    @Test
    public void testUpdatePermanentEmployee() {
        Employee e = employeeRepo.save(permanentEmployee1);
        e.setLastName("Jerry");
        e = employeeRepo.save(e);
        assertNotNull(e.getId());
        assertEquals(e.getLastName(), "Jerry");
    }

    @Test
    public void testInsertTemporaryEmployee() {
        Employee e = employeeRepo.save(temporaryEmployee1);
        assertNotNull(e.getId());
    }

    @Test
    public void testUpdateTemporaryEmployee() {
        Employee e = employeeRepo.save(temporaryEmployee1);
        e.setLastName("Larry");
        e = employeeRepo.save(e);
        assertNotNull(e.getId());
        assertEquals(e.getLastName(), "Larry");
    }

    @Test
    public void testFindAllEmployees() {
        employeeRepo.save(permanentEmployee1);
        employeeRepo.save(temporaryEmployee1);
        List<Employee> employees = employeeRepo.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() == 2);
    }

    @Test
    public void testFindEmployeeById() {
        Employee e = employeeRepo.save(permanentEmployee1);
        e = employeeRepo.findOne(e.getId());
        assertNotNull(e);
        assertNotNull(e.getId());
    }

    @Test
    public void testEmployeeEagerAddressLoading() {
        Employee e = employeeRepo.save(permanentEmployee1);
        assertNotNull(e);
        assertNotNull(e.getAddress());
        assertNotNull(e.getAddress().getCity());
        assertNotNull(e.getAddress().getStreet());
        assertNotNull(e.getAddress().getZipCode());
    }

    @Test
    public void testEmployeeRemove() {
        permanentEmployee1 = employeeRepo.save(permanentEmployee1);
        employeeRepo.delete(permanentEmployee1);
        List<Employee> employees = employeeRepo.findAll();
        assertTrue(employees.size() == 0);
    }

    @Test(expected = Exception.class)
    public void testInvalidEmployeeInsert() {
        Employee e = new PermanentEmployee();
        e = employeeRepo.save(e);
        assertNull(e);
    }

}
