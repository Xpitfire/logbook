package swt6.ue3.logbook.test.service;

import org.junit.Before;
import org.junit.Test;
import swt6.ue3.logbook.test.BaseTest;
import swt6.ue3.util.DateUtil;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 22.03.2016
 */
public class IntegrationTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
    }

    @Test
    public void testAddProjectMember() {
        project1.attachLeader(permanentEmployee1);
        projectService.addProjectMember(project1, permanentEmployee2);
        assertNotNull(projectService.findOne(project1.getId()));
    }

    @Test
    public void testProjectLeaderUpdate() {
        project1.attachLeader(permanentEmployee1);
        project1 = projectService.save(project1);
        projectService.updateProjectLeader(project1, permanentEmployee2);
        assertEquals(projectService.findOne(project1.getId()).getLeader().getLastName(), permanentEmployee2.getLastName());
    }

    @Test
    public void testLazyLoading() {
        project1.attachLeader(permanentEmployee1);
        project1.addMember(permanentEmployee2);
        project1.addMember(temporaryEmployee1);
        project1 = projectService.save(project1);
        project1 = projectService.findOne(project1.getId());
        project1.getMembers().forEach(employee -> assertNotNull(employee.getId()));
    }

    @Test
    public void testRemoveProjectMember() {
        project1.attachLeader(permanentEmployee1);
        projectService.addProjectMember(project1, permanentEmployee2);
        projectService.removeProjectMember(project1, permanentEmployee2);
        assertTrue(projectService.findOne(project1.getId()).getMembers().size() == 0);
    }

    @Test
    public void testCalculateHoursDifference() {
        assertTrue(1.0 == projectService.calculateHoursDifference(
                DateUtil.getTime(2016, 3, 22, 1, 0), DateUtil.getTime(2016, 3, 22, 2, 0)));
    }

    @Test
    public void testCalculateTotalCosts() {
        permanentEmployee1.setSalary(5000.0);
        permanentEmployee1.setHoursPerWeek(40);
        project1.attachLeader(permanentEmployee1);
        project1.addRequirement(requirement1);
        requirement1.attachSprint(sprint1);
        sprint1.attachProject(project1);
        logbookEntry1.attachEmployee(permanentEmployee1);
        requirement1.addTask(task1);
        task1.addLogbookEntries(logbookEntry1);
        project1 = projectService.save(project1);
        double val = projectService.calculateTotalCosts(project1);
        assertTrue(val > 28.8 && val < 28.9);
    }

    @Test
    public void testCalculateEstimatedTotalHoursPerProject() {
        project1.attachLeader(permanentEmployee1);
        project1.addRequirement(requirement1);
        requirement1.attachSprint(sprint1);
        sprint1.attachProject(project1);
        requirement1.addTask(task1);
        requirement1.addTask(task2);
        requirement1.addTask(task3);
        project1 = projectService.save(project1);
        double val = projectService.calculateEstimatedTotalHours(project1);
        assertTrue(35.0 == val);
    }

    @Test
    public void testCalculateEstimatedTotalHoursPerSprint() {
        project1.attachLeader(permanentEmployee1);
        project1.addRequirement(requirement1);
        project1.addRequirement(requirement2);

        requirement1.attachSprint(sprint1);
        sprint1.attachProject(project1);
        requirement1.addTask(task1);

        requirement2.attachSprint(sprint2);
        sprint2.attachProject(project1);
        requirement2.addTask(task2);

        project1 = projectService.save(project1);
        double val = projectService.calculateEstimatedTotalHours(sprint1);
        System.out.println(val);
        assertTrue(20.0 == val);
    }



}
