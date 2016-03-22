package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.FindController;
import swt6.ue3.logbook.controller.PrintController;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("printStatisticController")
public class PrintControllerImpl implements PrintController {

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LogbookEntryService logbookEntryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RequirementService requirementService;
    @Autowired
    private SprintService sprintService;

    @Autowired
    private FindController findController;

    @Override
    public PrintController printStatsProjectTotalCosts() {
        viewWriter.println("*** PRINT PROJECT COSTS ***");
        viewWriter.setIndent(2);
        viewWriter.printTableHeader("Projects", "Total costs (€)");
        for (Project p : projectService.findAll()) {
            viewWriter.printTableRow(p.getName(), String.format("%.2f", projectService.calculateTotalCosts(p)));
        }
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printStatsBurnDownChartPerProjectSprint() throws CommandCanceledException {
        viewWriter.println("*** PRINT BURN-DOWN CHARTS ***");
        viewWriter.setIndent(2);
        Project project = findController.findProject();
        Sprint sprint = findController.findSprint(project);

        double estimatedTotalHours = projectService.calculateEstimatedTotalHours(sprint);
        viewWriter.printf("Estimated Work: %s%n", estimatedTotalHours);
        viewWriter.printTableHeader("Dates", "Actual Work (hrs)", "Remaining Work (hrs)");

        for (LogbookEntry logbookEntry : logbookEntryService.findAll()) {
            if (logbookEntry.getTask().getRequirement().getProject().getId().equals(project.getId())) {
                double actualWork = projectService.calculateHoursDifference(logbookEntry.getStartTime(), logbookEntry.getEndTime());
                estimatedTotalHours -= actualWork;
                viewWriter.printTableRow(logbookEntry.getEndTime(), String.format("%.0f", actualWork), String.format("%.0f", estimatedTotalHours));
            }
        }
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityAll() {
        printEntityEmployees();
        printEntityLogbookEntries();
        printEntityProjects();
        printEntityTasks();
        printEntitySprints();
        printEntityRequirements();
        return this;
    }

    @Override
    public PrintController printEntitySprints() {
        viewWriter.println("*** PRINT SPRINTS ***");
        viewWriter.setIndent(2);
        sprintService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityRequirements() {
        viewWriter.println("*** PRINT REQUIREMENTS ***");
        viewWriter.setIndent(2);
        requirementService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityTasks() {
        viewWriter.println("*** PRINT TASKS ***");
        viewWriter.setIndent(2);
        taskService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityProjects() {
        viewWriter.println("*** PRINT PROJECTS ***");
        viewWriter.setIndent(2);
        projectService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityLogbookEntries() {
        viewWriter.println("*** PRINT LOGBOOK ENTRIES ***");
        viewWriter.setIndent(2);
        logbookEntryService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityEmployees() {
        viewWriter.println("*** PRINT EMPLOYEES ***");
        viewWriter.setIndent(2);
        employeeService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityProject() {
        Project project = findController.findProject();
        viewWriter.println("*** PRINT SELECTED PROJECT ***");
        viewWriter.setIndent(2);
        viewWriter.println(project);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntitySprint() {
        Sprint sprint = findController.findSprint();
        viewWriter.println("*** PRINT SELECTED SPRINT ***");
        viewWriter.setIndent(2);
        viewWriter.println(sprint);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityRequirement() {
        Requirement requirement = findController.findRequirement();
        viewWriter.println("*** PRINT SELECTED REQUIREMENT ***");
        viewWriter.setIndent(2);
        viewWriter.println(requirement);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityTask() {
        Task task = findController.findTask();
        viewWriter.println("*** PRINT SELECTED TASK ***");
        viewWriter.setIndent(2);
        viewWriter.println(task);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityEmployee() {
        Employee employee = findController.findEmployee();
        viewWriter.println("*** PRINT SELECTED EMPLOYEE ***");
        viewWriter.setIndent(2);
        viewWriter.println(employee);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public PrintController printEntityLogbookEntry() {
        LogbookEntry logbookEntry = findController.findLogbookEntry();
        viewWriter.println("*** PRINT SELECTED LOGBOOK ENTRY ***");
        viewWriter.setIndent(2);
        viewWriter.println(logbookEntry);
        viewWriter.resetIndent();
        return this;
    }

}
