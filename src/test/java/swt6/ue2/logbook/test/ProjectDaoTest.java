package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Project;


import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class ProjectDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        project1.setLeader(permanentEmployee1);
    }

    @Test
    public void testInsertProject() {
        project1.addSprint(sprint1);
        project1.addRequirement(requirement1);
        Project p = projectDao.safe(project1);
        assertNotNull(p.getId());
    }

    @Test
    public void testUpdateProject() {
        Project p = projectDao.safe(project1);
        p.setName("AI");
        p = projectDao.safe(p);
        assertEquals("AI", p.getName());
    }

    @Test
    public void testFirstOrDefaultProject() {
        Project p = projectDao.safe(project1);
        Long id = p.getId();
        p = projectDao.firstOrDefault();
        assertEquals(id, p.getId());
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
    public void testCascadeRemoveProject() {
        Project p = projectDao.safe(project1);
        p.addSprint(sprint1);
        Long id = p.getId();
        projectDao.remove(p);
        p = projectDao.findById(id);
        assertNull(p);
    }

    @Test
    public void testFindAllProjects() {
        projectDao.safe(project1);
        projectDao.safe(project2);
        assertTrue(projectDao.count() == 2);
        assertTrue(projectDao.findAll().size() == 2);
    }

    @Test
    public void testFindProjectById() {
        Project p = projectDao.safe(project1);
        p = projectDao.findById(p.getId());
        assertNotNull(p.getId());
    }

}
