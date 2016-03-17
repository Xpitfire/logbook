package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
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
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private static final int WORK_DAYS_PER_YEAR = 220;
    private static final int HOURS_PER_DAY = 24;
    private static final double WEEKS_PER_MONTH = 4.33;

    public double convertHoursToDays(int hours) {
        return hours / HOURS_PER_DAY;
    }

    public double calculateHoursDifference(Date startDate, Date endDate) {
        long secs = (endDate.getTime() - startDate.getTime()) / 1000;
        double hours = secs / 3600;
        return hours;
    }

    public double calculateTotalCosts(Project project) {
        double totalCosts = 0.0;
        double ratePerHour;

        List<Employee> allEmployees = new ArrayList<>(project.getMembers());
        allEmployees.add(project.getLeader());

        for (Employee employee : allEmployees) {
            double hoursPerEmployee = 0.0;
            if (employee instanceof TemporaryEmployee) {
                TemporaryEmployee te = (TemporaryEmployee)employee;
                ratePerHour = te.getHourlyRate();
            } else {
                PermanentEmployee pe = (PermanentEmployee)employee;
                ratePerHour = pe.getSalary() / pe.getHoursPerWeek() / WEEKS_PER_MONTH;
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

    private double calculateActualTotalHours(Project project) {
        double hoursPerProject = 0.0;
        for (Employee e : project.getMembers()) {
            double hoursPerEmployee = 0.0;
            for (LogbookEntry l : e.getLogbookEntries()) {
                if (project.getId() == l.getTask().getRequirement().getProject().getId()) {
                    hoursPerEmployee += calculateHoursDifference(l.getStartTime(), l.getEndTime());
                }
            }
            hoursPerProject += hoursPerEmployee;
        }
        return hoursPerProject;
    }

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
    public JpaRepository<Project, Long> getRepository() {
        return projectRepository;
    }
}
