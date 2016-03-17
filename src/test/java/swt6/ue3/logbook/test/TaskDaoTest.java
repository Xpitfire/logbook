package swt6.ue3.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.domain.Task;

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

    @Test
    public void testInsertTask() {
        Task t = taskRepo.save(task1);
        assertNotNull(t.getId());
    }

    @Test
    public void testUpdateTask() {
        Task t = taskRepo.save(task1);
        t.setDescription("hello test");
        t = taskRepo.save(t);
        assertEquals("hello test", t.getDescription());
    }

    @Test
    public void testFindAllRequirements() {
        taskRepo.save(task1);
        task2.attachRequirement(requirement2);
        taskRepo.save(task2);
        assertTrue(taskRepo.count() == 2);
        assertTrue(taskRepo.findAll().size() == 2);
    }

    @Test
    public void testRemoveTask() {
        Task t = taskRepo.save(task2);
        String id = t.getId();
        taskRepo.delete(t);
        t = taskRepo.findOne(id);
        assertNull(t);
    }

}
