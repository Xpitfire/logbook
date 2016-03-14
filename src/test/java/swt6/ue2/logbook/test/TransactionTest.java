package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dao.Dao;
import swt6.ue2.logbook.dao.DaoFactory;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.LogbookEntry;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class TransactionTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
    }

    @Test
    public void testExplicitDaoTransactionRollback() throws Exception {
        try (Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class, true);
             Dao<LogbookEntry> logbookEntryDao = DaoFactory.getDao(LogbookEntry.class, true)) {
            employeeDao.safe(permanentEmployee1);
            logbookEntry1.attachEmployee(permanentEmployee1);
            logbookEntryDao.safe(logbookEntry1);
            logbookEntryDao.rollback();
        }
        assertTrue(employeeDao.count() == 0);
        assertTrue(logbookEntryDao.count() == 0);
    }

    @Test
    public void testExplicitDaoTransactionCommit() throws Exception {
        try (Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class, true);
             Dao<LogbookEntry> logbookEntryDao = DaoFactory.getDao(LogbookEntry.class, true)) {
            employeeDao.safe(permanentEmployee1);
            permanentEmployee1.addLogbookEntry(logbookEntry1);
            logbookEntryDao.safe(logbookEntry1);
        }
        assertTrue(employeeDao.count() == 2);
        assertTrue(logbookEntryDao.count() == 1);
    }

}
