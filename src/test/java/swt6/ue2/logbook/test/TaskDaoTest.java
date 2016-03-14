package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Task;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class TaskDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        task1.attachRequirement(requirement1);
    }

    @Test
    public void testInsertTask() {
        Task t = taskDao.safe(task1);
        assertNotNull(t.getId());
    }

    @Test
    public void testUpdateTask() {
        Task t = taskDao.safe(task1);
        t.setDescription("hello test");
        t = taskDao.safe(t);
        assertEquals("hello test", t.getDescription());
    }

    @Test
    public void testFirstOrDefaultTask() {
        Task t = taskDao.safe(task1);
        String id = t.getId();
        t = taskDao.firstOrDefault();
        assertNotNull(t.getId());
        assertEquals(id, t.getId());
    }

    @Test
    public void testFindAllRequirements() {
        taskDao.safe(task1);
        task2.attachRequirement(requirement2);
        taskDao.safe(task2);
        assertTrue(taskDao.count() == 2);
        assertTrue(taskDao.findAll().size() == 2);
    }

    @Test
    public void testRemoveTask() {
        Task t = taskDao.safe(task2);
        String id = t.getId();
        taskDao.remove(t);
        t = taskDao.findById(id);
        assertNull(t);
    }

}
