package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.LogbookEntry;
import swt6.ue2.logbook.domain.Project;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class ProjectDaoTest extends CommonTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
    }

    @Test
    public void testInsertProject() {
        project1.addSprint(sprint1);
        project1.addRequirement(requirement1);
        project1.addMember(permanentEmployee1);
        Project p = projectDao.safe(project1);
        assertNotNull(p.getId());
    }

    @Test
    public void testFirstOrDefaultProject() {
        Project p = projectDao.safe(project1);
        Long id = p.getId();
        p = projectDao.firstOrDefault();
        assertTrue(id == p.getId());
    }

    @Test
    public void testRemoveProject() {
        Project p = projectDao.safe(project1);
        Long id = p.getId();
        projectDao.remove(p);
        p = projectDao.findById(id);
        assertNull(p);
    }

    @Test
    public void testCascadeRemove() {
        Project p = projectDao.safe(project1);
        p.addSprint(sprint1);
        Long id = p.getId();
        projectDao.remove(p);
        p = projectDao.findById(id);
        assertNull(p);
    }

}
