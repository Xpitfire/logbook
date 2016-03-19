package swt6.ue3.logbook.test.dal;

import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.annotation.SessionExtended;
import swt6.ue3.logbook.domain.LogbookEntry;
import swt6.ue3.logbook.test.BaseTest;
import swt6.ue3.util.DateUtil;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class LogbookRepositoryTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        logbookEntry1.attachEmployee(permanentEmployee1);
        logbookEntry2.attachEmployee(permanentEmployee2);
        logbookEntry3.attachEmployee(permanentEmployee1);
    }

    @SessionExtended
    @Test
    public void testCountLogbookEntry() {
        logbookEntryRepo.save(logbookEntry2);
        assertTrue(logbookEntryRepo.count() == 1);
    }

    @SessionExtended
    @Test
    public void testInsertLogbookEntry() {
        LogbookEntry l = logbookEntryRepo.save(logbookEntry1);
        assertNotNull(l.getId());
    }

    @SessionExtended
    @Test
    public void testProtectedUpdateLogbookEntry() {
        LogbookEntry l = logbookEntryRepo.save(logbookEntry1);
        Date startDate = DateUtil.getTime(1988, 7, 1, 10, 30);
        Date endDate = DateUtil.getTime(1988, 7, 1, 11, 30);
        l.setActivity("test activity");
        l = logbookEntryRepo.save(logbookEntry1);
        assertEquals("test activity", l.getActivity());
    }

    @Test
    public void testFindAllLogbookEntries() {
        logbookEntryRepo.save(logbookEntry2);
        List<LogbookEntry> logbookEntries = logbookEntryRepo.findAll();
        assertNotNull(logbookEntries);
        assertTrue(logbookEntries.size() == 1);
    }

    @Test
    public void testFindLogbookEntryById() {
        LogbookEntry l = logbookEntryRepo.save(logbookEntry1);
        l = logbookEntryRepo.findOne(l.getId());
        assertNotNull(l);
        assertNotNull(l.getId());
    }

    @Test
    public void testLogbookEntryRemove() {
        LogbookEntry l = logbookEntryRepo.save(logbookEntry2);
        Long logbookId = l.getId();
        logbookEntryRepo.delete(l);
        LogbookEntry entry = logbookEntryRepo.findOne(logbookId);
        assertNull(entry);
    }

}
