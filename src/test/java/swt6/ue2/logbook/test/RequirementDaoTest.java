package swt6.ue2.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dal.DaoFactory;
import swt6.ue2.logbook.domain.Requirement;

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

    @After
    @Override
    public void complete() {
        super.complete();
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
        DaoFactory.commit();
        r = requirementDao.firstOrDefault();
        assertNotNull(r.getId());
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
