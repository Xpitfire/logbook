package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.ProjectRepository;
import swt6.ue3.logbook.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private static final double WEEKS_PER_MONTH = 4.33;

    @Override
    public double calculateHoursDifference(Date startDate, Date endDate) {
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        double hours = secs / 3600;
        return hours;
    }

    @Override
    public double calculateTotalCosts(Project project) {
        double totalCosts = 0.0;
        double ratePerHour = 0.0;

        List<Employee> allEmployees = new ArrayList<>(project.getMembers());
        allEmployees.add(project.getLeader());

        for (Employee employee : allEmployees) {
            double hoursPerEmployee = 0.0;
            if (employee instanceof TemporaryEmployee) {
                TemporaryEmployee te = (TemporaryEmployee)employee;
                ratePerHour = te.getHourlyRate();
            } else {
                PermanentEmployee pe = (PermanentEmployee)employee;
                if (pe.getSalary() != null && pe.getHoursPerWeek() != null && pe.getHoursPerWeek() > 0)
                    ratePerHour = pe.getSalary() / pe.getHoursPerWeek();
                ratePerHour /= WEEKS_PER_MONTH;
            }
            for (LogbookEntry l : employee.getLogbookEntries()) {
                if (project.getId() == l.getTask().getRequirement().getProject().getId()) {
                    hoursPerEmployee += calculateHoursDifference(l.getStartTime(), l.getEndTime());
                }
            }
            totalCosts += hoursPerEmployee * ratePerHour;
        }
        return totalCosts;
    }

    @Override
    public double calculateEstimatedTotalHours(Project project) {
        double hoursPerProject = 0.0;
        for (Requirement requirement : project.getRequirements()) {
            for (Task task : requirement.getTasks()) {
                hoursPerProject += task.getEstimatedHours();
            }
        }
        return hoursPerProject;
    }

    @Override
    public double calculateEstimatedTotalHours(Sprint sprint) {
        double hoursPerProject = 0.0;
        for (Requirement requirement : sprint.getRequirements()) {
            for (Task task : requirement.getTasks()) {
                hoursPerProject += task.getEstimatedHours();
            }
        }
        return hoursPerProject;
    }

    @Override
    public void updateProjectLeader(Project project, Employee leader) {
        project.attachLeader(leader);
        project.setId(getRepository().save(project).getId());
    }

    @Override
    public void addProjectMember(Project project, Employee... members) {
        for (Employee e : members) {
            project.addMember(e);
        }
        project.setId(getRepository().save(project).getId());
    }

    @Override
    public void removeProjectMember(Project project, Employee... members) {
        for (Employee e : members) {
            project.removeMember(e);
        }
        project.setId(getRepository().save(project).getId());
    }

    @Override
    public JpaRepository<Project, Long> getRepository() {
        return projectRepository;
    }
}
