package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dao.DaoFactory;
import swt6.ue2.logbook.dao.EmployeeDao;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.PermanentEmployee;
import swt6.ue2.logbook.domain.TemporaryEmployee;


import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class EmployeeDaoTest extends CommonTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
    }

    @Test
    public void testCountEmployee() {
        employeeDao.safe(permanentEmployee1);
        employeeDao.safe(permanentEmployee2);
        employeeDao.safe(temporaryEmployee1);
        assertTrue(employeeDao.count() == 3);
    }

    @Test
    public void testFirstOrDefaultLogbookEntry() {
        // TODO find out why the id is in inverse order
        Employee e = employeeDao.safe(temporaryEmployee1);
        Long id = e.getId();
        employeeDao.safe(permanentEmployee1);
        e = employeeDao.firstOrDefault();
        assertTrue(id == e.getId());
    }

    @Test
    public void testInsertPermanentEmployee() {
        Employee e = employeeDao.safe(permanentEmployee1);
        assertNotNull(e.getId());
    }

    @Test
    public void testInsertTemporaryEmployee() {
        Employee e = employeeDao.safe(temporaryEmployee1);
        assertNotNull(e.getId());
    }

    @Test
    public void testFindAllEmployees() {
        employeeDao.safe(permanentEmployee1);
        employeeDao.safe(temporaryEmployee1);
        List<Employee> employees = employeeDao.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() == 2);
    }

    @Test
    public void testFindEmployeeById() {
        Employee e = employeeDao.safe(permanentEmployee1);
        e = employeeDao.findById(e.getId());
        assertNotNull(e);
        assertNotNull(e.getId());
    }

    @Test
    public void testEmployeeEagerAddressLoading() {
        employeeDao.safe(permanentEmployee1);
        Employee e = employeeDao.firstOrDefault();
        assertNotNull(e);
        assertNotNull(e.getAddress());
        assertNotNull(e.getAddress().getCity());
        assertNotNull(e.getAddress().getStreet());
        assertNotNull(e.getAddress().getZipCode());
    }

    @Test
    public void testEmployeeRemove() {
        permanentEmployee1 = employeeDao.safe(permanentEmployee1);
        employeeDao.remove(permanentEmployee1);
        List<Employee> employees = employeeDao.findAll();
        assertTrue(employees.size() == 0);
    }

    @Test
    public void testTypedEmployee() {
        EmployeeDao employeeDao = DaoFactory.getDao(Employee.class);
        employeeDao.safe(permanentEmployee1);
        employeeDao.safe(temporaryEmployee1);
        List<TemporaryEmployee> employees = employeeDao.findAll(TemporaryEmployee.class);
        assertTrue(employees.size() == 1);
    }

    @Test(expected = Exception.class)
    public void testInvalidEmployeeInsert() {
        // TODO: solve the nullable annotation issue
        Employee e = new PermanentEmployee();
        e = employeeDao.safe(e);
        assertNull(e);
    }

}
