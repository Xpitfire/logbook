package swt6.ue2.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dal.DaoFactory;
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
        project1.attachLeader(permanentEmployee1);
        project2.attachLeader(permanentEmployee2);
        requirement1.attachProject(project1);
        requirement2.attachProject(project1);
        requirement3.attachProject(project2);
        task1.attachRequirement(requirement1);
        task2.attachRequirement(requirement2);
        task3.attachRequirement(requirement3);
    }

    @After
    @Override
    public void complete() {
        super.complete();
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
        DaoFactory.commit();
        t = taskDao.firstOrDefault();
        assertNotNull(t.getId());
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
