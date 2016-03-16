package swt6.ue3.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.domain.Project;


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
        project2.setLeader(permanentEmployee1);
    }

    @After
    @Override
    public void complete() {
        super.complete();
    }

    @Test
    public void testInsertProject() {
        project1.addSprint(sprint1);
        project1.addRequirement(requirement1);
        Project p = projectRepo.save(project1);
        assertNotNull(p.getId());
    }

    @Test
    public void testUpdateProject() {
        Project p = projectRepo.save(project1);
        p.setName("AI");
        p = projectRepo.save(p);
        assertEquals("AI", p.getName());
    }

    @Test
    public void testRemoveProject() {
        Project p = projectRepo.save(project1);
        Long id = p.getId();
        projectRepo.delete(p);
        p = projectRepo.findOne(id);
        assertNull(p);
    }

    @Test
    public void testCascadeRemoveProject() {
        Project p = projectRepo.save(project1);
        p.addSprint(sprint1);
        Long id = p.getId();
        projectRepo.delete(p);
        p = projectRepo.findOne(id);
        assertNull(p);
    }

    @Test
    public void testFindAllProjects() {
        projectRepo.save(project1);
        projectRepo.save(project2);
        assertTrue(projectRepo.count() == 2);
        assertTrue(projectRepo.findAll().size() == 2);
    }

    @Test
    public void testFindProjectById() {
        Project p = projectRepo.save(project1);
        p = projectRepo.findOne(p.getId());
        assertNotNull(p.getId());
    }

}
