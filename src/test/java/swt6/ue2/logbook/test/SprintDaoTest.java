package swt6.ue2.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Sprint;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class SprintDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        project1.attachLeader(permanentEmployee1);
        project2.attachLeader(permanentEmployee2);
        sprint1.attachProject(project1);
        sprint2.attachProject(project2);
    }

    @After
    @Override
    public void complete() {
        super.complete();
    }

    @Test
    public void testInsertSprint() {
        Sprint s = sprintDao.safe(sprint1);
        assertNotNull(s.getId());
    }

    @Test
    public void testFirstOrDefaultSprint() {
        Sprint s = sprintDao.safe(sprint1);
        Long id = s.getId();
        s = sprintDao.firstOrDefault();
        assertNotNull(s.getId());
        assertEquals(id, s.getId());
    }

    @Test
    public void testFindAllSprints() {
        sprintDao.safe(sprint1);
        sprint2.attachProject(project2);
        sprintDao.safe(sprint2);
        assertTrue(sprintDao.count() == 2);
        assertTrue(sprintDao.findAll().size() == 2);
    }

    @Test
    public void testRemoveSprint() {
        Sprint s = sprintDao.safe(sprint2);
        Long id = s.getId();
        sprintDao.remove(s);
        s = sprintDao.findById(id);
        assertNull(s);
    }

}
