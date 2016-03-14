package swt6.ue2.logbook.test;

import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.domain.Requirement;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class RequirementDaoTest extends CommonTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        requirement1.attachProject(project1);
    }

    @Test
    public void testInsertRequirement() {
        Requirement r = requirementDao.safe(requirement1);
        assertNotNull(r.getId());
    }

    @Test
    public void testUpdateRequirement() {
        Requirement r = requirementDao.safe(requirement1);
        r.setDescription("test bla bla");
        r = requirementDao.safe(r);
        assertEquals("test bla bla", r.getDescription());
    }

    @Test
    public void testFirstOrDefaultRequirement() {
        Requirement r = requirementDao.safe(requirement1);
        String id = r.getId();
        r = requirementDao.firstOrDefault();
        assertNotNull(r.getId());
        assertEquals(id, r.getId());
    }

    @Test
    public void testFindAllRequirements() {
        requirementDao.safe(requirement1);
        requirement2.attachProject(project2);
        requirementDao.safe(requirement2);
        requirement3.attachProject(project2);
        requirementDao.safe(requirement3);
        assertTrue(requirementDao.count() == 3);
        assertTrue(requirementDao.findAll().size() == 3);
    }

    @Test
    public void testRemoveRequirement() {
        Requirement r = requirementDao.safe(requirement2);
        String id = r.getId();
        requirementDao.remove(r);
        r = requirementDao.findById(id);
        assertNull(r);
    }

}
