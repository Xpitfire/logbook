package swt6.ue3.logbook.logic;

import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Sprint;

import java.util.Date;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 16.03.2016
 */
public interface ProjectService extends AppService<Project, Long> {
    double calculateHoursDifference(Date startDate, Date endDate);
    double calculateTotalCosts(Project project);
    double calculateEstimatedTotalHours(Project project);
    double calculateEstimatedTotalHours(Sprint sprint);
    void updateProjectLeader(Project project, Employee leader);
    void addProjectMember(Project project, Employee... members);
    void removeProjectMember(Project project, Employee... members);
}
