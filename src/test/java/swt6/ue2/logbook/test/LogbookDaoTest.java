package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.LogbookEntry;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class LogbookDaoTest extends CommonTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        logbookEntry1.attachEmployee(permanentEmployee1);
        logbookEntry1 = logbookEntryDao.safe(logbookEntry1);
    }

    @Test
    public void testLogbookEntryInsert() {
        assertNotNull(logbookEntry1.getId());
    }

    @Test
    public void testLogbookEntryRemove() {
        Long employeeId = logbookEntry1.getEmployee().getId();
        Long logbookId = logbookEntry1.getId();
        logbookEntryDao.remove(logbookEntry1);
        LogbookEntry entry = logbookEntryDao.findById(logbookId);
        assertNull(entry);
        Employee e = employeeDao.findById(employeeId);
        assertNotNull(e.getId());
    }

    @Test
    public void testEmployeeCascadeRemove() {
        // TODO: find out why the cascade delete does not work
        Long employeeId = logbookEntry1.getEmployee().getId();
        Long logbookId = logbookEntry1.getId();
        employeeDao.remove(permanentEmployee1);
        assertNull(employeeDao.findById(employeeId));
        LogbookEntry entry = logbookEntryDao.findById(logbookId);
        assertNull(entry);
    }

}
