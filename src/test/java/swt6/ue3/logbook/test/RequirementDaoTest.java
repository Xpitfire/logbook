package swt6.ue3.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.domain.Requirement;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class RequirementDaoTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        project1.attachLeader(permanentEmployee1);
        project2.attachLeader(permanentEmployee2);
        requirement1.attachProject(project1);
        requirement2.attachProject(project1);
        requirement3.attachProject(project2);
    }

    @Test
    public void testInsertRequirement() {
        Requirement r = requirementRepo.save(requirement1);
        assertNotNull(r.getId());
    }

    @Test
    public void testUpdateRequirement() {
        Requirement r = requirementRepo.save(requirement1);
        r.setDescription("test bla bla");
        r = requirementRepo.save(r);
        assertEquals("test bla bla", r.getDescription());
    }

    @Test
    public void testFindAllRequirements() {
        requirementRepo.save(requirement1);
        requirement2.attachProject(project2);
        requirementRepo.save(requirement2);
        requirement3.attachProject(project2);
        requirementRepo.save(requirement3);
        assertTrue(requirementRepo.count() == 3);
        assertTrue(requirementRepo.findAll().size() == 3);
    }

    @Test
    public void testRemoveRequirement() {
        Requirement r = requirementRepo.save(requirement2);
        String id = r.getId();
        requirementRepo.delete(r);
        r = requirementRepo.findOne(id);
        assertNull(r);
    }

}
