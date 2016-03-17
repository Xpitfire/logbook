package swt6.ue3.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.domain.Sprint;

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

    @Test
    public void testInsertSprint() {
        Sprint s = sprintRepo.save(sprint1);
        assertNotNull(s.getId());
    }

    @Test
    public void testFindAllSprints() {
        sprintRepo.save(sprint1);
        sprint2.attachProject(project2);
        sprintRepo.save(sprint2);
        assertTrue(sprintRepo.count() == 2);
        assertTrue(sprintRepo.findAll().size() == 2);
    }

    @Test
    public void testRemoveSprint() {
        Sprint s = sprintRepo.save(sprint2);
        Long id = s.getId();
        sprintRepo.delete(s);
        s = sprintRepo.findOne(id);
        assertNull(s);
    }

}
