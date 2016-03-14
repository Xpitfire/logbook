package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.LogbookEntry;

import java.util.List;

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
    }

    @Test
    public void testCountLogbookEntry() {
        logbookEntryDao.safe(logbookEntry1);
        assertTrue(logbookEntryDao.count() == 1);
    }

    @Test
    public void testFirstOrDefaultLogbookEntry() {
        LogbookEntry l = logbookEntryDao.safe(logbookEntry1);
        Long id = l.getId();
        l = logbookEntryDao.firstOrDefault();
        assertTrue(id == l.getId());
    }

    @Test
    public void testInsertLogbookEntry() {
        LogbookEntry l = logbookEntryDao.safe(logbookEntry1);
        assertNotNull(l.getId());
    }

    @Test
    public void testFindAllLogbookEntries() {
        logbookEntryDao.safe(logbookEntry1);
        logbookEntryDao.safe(logbookEntry2);
        List<LogbookEntry> logbookEntries = logbookEntryDao.findAll();
        assertNotNull(logbookEntries);
        assertTrue(logbookEntries.size() == 2);
    }

    @Test
    public void testFindLogbookEntryById() {
        LogbookEntry l = logbookEntryDao.safe(logbookEntry1);
        l = logbookEntryDao.findById(l.getId());
        assertNotNull(l);
        assertNotNull(l.getId());
    }

    @Test
    public void testLogbookEntryRemove() {
        // TODO find out why it works with setEmployee but not with attachEmployee
        //logbookEntry1.setEmployee(permanentEmployee1);
        logbookEntry1.attachEmployee(permanentEmployee1);
        logbookEntry1 = logbookEntryDao.safe(logbookEntry1);
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
        //logbookEntry1.attachEmployee(permanentEmployee1);
        permanentEmployee1.addLogbookEntry(logbookEntry1);
        logbookEntry1 = logbookEntryDao.safe(logbookEntry1);
        Long employeeId = logbookEntry1.getEmployee().getId();
        Long logbookId = logbookEntry1.getId();
        employeeDao.remove(permanentEmployee1);
        assertNull(employeeDao.findById(employeeId));
        LogbookEntry entry = logbookEntryDao.findById(logbookId);
        assertNull(entry);
    }

}
