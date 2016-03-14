package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.LogbookEntry;
import swt6.ue2.util.DateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class LogbookDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        logbookEntry1.attachEmployee(permanentEmployee1);
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
        assertEquals(id, l.getId());
    }

    @Test
    public void testInsertLogbookEntry() {
        LogbookEntry l = logbookEntryDao.safe(logbookEntry1);
        assertNotNull(l.getId());
    }

    @Test
    public void testProtectedUpdateLogbookEntry() {
        LogbookEntry l = logbookEntryDao.safe(logbookEntry1);
        Date startDate = DateUtil.getTime(1988, 7, 1, 10, 30);
        Date endDate = DateUtil.getTime(1988, 7, 1, 11, 30);
        l.setActivity("test activity");
        l = logbookEntryDao.safe(logbookEntry1);
        assertNotEquals("test activity", l.getActivity());
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
        LogbookEntry l = logbookEntryDao.safe(logbookEntry2);
        Long logbookId = l.getId();
        logbookEntryDao.remove(l);
        LogbookEntry entry = logbookEntryDao.findById(logbookId);
        assertNull(entry);
    }

    /*
    @Test
    public void testCascadeRemoveLogbookEntry() {
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
    */

}
