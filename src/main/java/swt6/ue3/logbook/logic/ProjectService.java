package swt6.ue3.logbook.logic;

import swt6.ue3.logbook.domain.Project;

import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 16.03.2016
 */
public interface ProjectService extends AppService<Project, Long> {
    double calculateHoursDifference(Date startDate, Date endDate);
    double calculateTotalCosts(Project project);
    double calculateEstimatedTotalHours(Project project);
}
