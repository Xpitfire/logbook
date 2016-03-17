package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.domain.LogbookEntry;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.view.console.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("printStatisticController")
public class PrintStatisticConsoleController extends AbstractConsoleController {

    @Autowired
    private FindEntityConsoleController findEntityConsoleController;

    @Override
    public String getTitle() {
        return "Print Statistics";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("a")) {
                    printAll();
                } else if (input.equalsIgnoreCase("e")) {
                    printEmployees();
                } else if (input.equalsIgnoreCase("l")) {
                    printLogbookEntries();
                } else if (input.equalsIgnoreCase("p")) {
                    printProjects();
                } else if (input.equalsIgnoreCase("r")) {
                    printRequirements();
                } else if (input.equalsIgnoreCase("s")) {
                    printSprints();
                } else if (input.equalsIgnoreCase("u")) {
                    printBurndownCharts();
                } else if (input.equalsIgnoreCase("c")) {
                    printProjectTotalCosts();
                } else if (input.equalsIgnoreCase("t")) {
                    printTasks();
                } else if (input.equalsIgnoreCase("b")) {
                    // skip
                } else {
                    printInvalidInput();
                }
            } catch (CommandCanceledException ex) {
                printUserCancelMessage();
                printEntranceInfo();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    public void printProjectTotalCosts() {
        viewWriter.println("*** PRINT PROJECT COSTS ***");
        viewWriter.setIndent(2);
        viewWriter.printTableHeader("Projects", "Total costs (€)");
        for (Project p : projectService.findAll()) {
            viewWriter.printTableRow(p.getName(), String.format("%.2f", projectService.calculateTotalCosts(p)));
        }
        viewWriter.resetIndent();
    }

    public void printBurndownCharts() throws CommandCanceledException {
        viewWriter.println("*** PRINT BURNDOWN CHARTS ***");
        viewWriter.setIndent(2);
        Project project = findEntityConsoleController.findProject();

        double remainingHours = projectService.calculateEstimatedTotalHours(project);
        viewWriter.printf("Estimated Work: %s%n", remainingHours);
        viewWriter.printTableHeader("Dates", "Actual Work (hrs)", "Remaining Work (hrs)");
        for (LogbookEntry logbookEntry : logbookEntryService.findAll()) {
            if (logbookEntry.getTask().getRequirement().getProject().getId() == project.getId()) {
                double actualWork = projectService.calculateHoursDifference(logbookEntry.getStartTime(), logbookEntry.getEndTime());
                remainingHours -= actualWork;
                viewWriter.printTableRow(logbookEntry.getEndTime(), String.format("%.0f", actualWork), String.format("%.0f", remainingHours));
            }
        }
        viewWriter.resetIndent();
    }

    public AbstractConsoleController printAll() {
        printEmployees();
        printLogbookEntries();
        printProjects();
        printTasks();
        printSprints();
        printRequirements();
        return this;
    }

    public AbstractConsoleController printSprints() {
        viewWriter.println("*** PRINT SPRINTS ***");
        viewWriter.setIndent(2);
        sprintService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    public AbstractConsoleController printRequirements() {
        viewWriter.println("*** PRINT REQUIREMENTS ***");
        viewWriter.setIndent(2);
        requirementService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    public AbstractConsoleController printTasks() {
        viewWriter.println("*** PRINT TASKS ***");
        viewWriter.setIndent(2);
        taskService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    public AbstractConsoleController printProjects() {
        viewWriter.println("*** PRINT PROJECTS ***");
        viewWriter.setIndent(2);
        projectService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    public AbstractConsoleController printLogbookEntries() {
        viewWriter.println("*** PRINT LOGBOOK ENTRIES ***");
        viewWriter.setIndent(2);
        logbookEntryService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    public AbstractConsoleController printEmployees() {
        viewWriter.println("*** PRINT EMPLOYEES ***");
        viewWriter.setIndent(2);
        employeeService.findAll().forEach(viewWriter::println);
        viewWriter.resetIndent();
        return this;
    }

    @Override
    public AbstractConsoleController printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[a] ... Print all");
        viewWriter.println("[u] ... Print burn down chart");
        viewWriter.println("[c] ... Print costs per project");
        viewWriter.newLine();
        viewWriter.println("[e] ... Print employees");
        viewWriter.println("[l] ... Print logbook entries");
        viewWriter.println("[p] ... Print projects");
        viewWriter.println("[r] ... Print requirements");
        viewWriter.println("[s] ... Print sprints");
        viewWriter.println("[t] ... Print tasks");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }
}
