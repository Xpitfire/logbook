package swt6.ue2.logbook.test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.CompositeOperation;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dao.Dao;
import swt6.ue2.logbook.dao.DaoFactory;
import swt6.ue2.logbook.domain.Address;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.PermanentEmployee;
import swt6.ue2.logbook.domain.TemporaryEmployee;
import swt6.ue2.util.DateUtil;


import java.util.List;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class EmployeeDaoTest {

    private static DriverManagerDestination dbDestination =
            new DriverManagerDestination("jdbc:derby://localhost:1527/WorkLogDb", "APP", "APP");

    @Before
    public void prepare() {
        Operation operation = sequenceOf(
                CommonOperations.DELETE_ALL,
                CommonOperations.INSERT_TEMPORARY_EMPLOYEE_DATA,
                CommonOperations.INSERTT_PERMANENT_EMPLOYEE_DATA);
        DbSetup dbSetup = new DbSetup(dbDestination, operation);
        dbSetup.launch();
    }

    @Test
    public void testInsertPermanentEmployee() {
        Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class);
        Employee e = new PermanentEmployee("Jack", "Black", DateUtil.getDate(1960, 7, 3));
        e.setAddress(new Address("1010", "Wien", "Hauptstraße 1"));
        e = employeeDao.merge(e);
        assertNotNull(e.getId());
    }

    @Test
    public void testInsertTemporaryEmployee() {
        Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class);
        Employee e = new TemporaryEmployee("Johnny", "Cockburn", DateUtil.getDate(1979, 3, 12));
        e = employeeDao.merge(e);
        assertNotNull(e.getId());
    }

    @Test
    public void testFindAllEmployee() {
        Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class);
        //employeeDao.merge(new PermanentEmployee("John", "Bon", DateUtil.getDate(1990, 3, 4)));
        //employeeDao.merge(new TemporaryEmployee("Jack", "Black", DateUtil.getDate(1992, 6, 4)));
        List<Employee> employees = employeeDao.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
        System.out.println("empl: " + employees.size());
    }

}
