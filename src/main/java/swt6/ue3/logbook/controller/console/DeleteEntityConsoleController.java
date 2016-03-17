package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.view.console.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("deleteEntityController")
public class DeleteEntityConsoleController extends AbstractConsoleController {

    @Autowired
    private FindEntityConsoleController findEntityConsoleController;

    @Override
    public String getTitle() {
        return "Delete Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    deleteEmployee();
                } else if (input.equalsIgnoreCase("l")) {
                    deleteLogbookEntry();
                } else if (input.equalsIgnoreCase("p")) {
                    deleteProject();
                } else if (input.equalsIgnoreCase("r")) {
                    deleteRequirement();
                } else if (input.equalsIgnoreCase("s")) {
                    deleteSprint();
                } else if (input.equalsIgnoreCase("t")) {
                    deleteTask();
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

    public void deleteTask() throws CommandCanceledException {
        Task task = findEntityConsoleController.findTask();
        showConfirmationMessage();
        taskService.remove(task);
        viewWriter.println("Successfully deleted!");
    }

    public void deleteSprint() throws CommandCanceledException {
        Sprint sprint = findEntityConsoleController.findSprint();
        showConfirmationMessage();
        sprintService.remove(sprint);
        viewWriter.println("Successfully deleted!");
    }

    public void deleteRequirement() throws CommandCanceledException {
        Requirement requirement = findEntityConsoleController.findRequirement();
        showConfirmationMessage();
        requirementService.remove(requirement);
        viewWriter.println("Successfully deleted!");
    }

    public void deleteProject() throws CommandCanceledException {
        Project project = findEntityConsoleController.findProject();
        showConfirmationMessage();
        projectService.remove(project);
        viewWriter.println("Successfully deleted!");
    }

    public void deleteLogbookEntry() throws CommandCanceledException {
        LogbookEntry logbookEntry = findEntityConsoleController.findLogbookEntry();
        showConfirmationMessage();
        logbookEntryService.remove(logbookEntry);
        viewWriter.println("Successfully deleted!");
    }

    public void deleteEmployee() throws CommandCanceledException {
        Employee employee = findEntityConsoleController.findEmployee();
        showConfirmationMessage();
        employeeService.remove(employee);
        viewWriter.println("Successfully deleted!");
    }

    @Override
    public AbstractConsoleController printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[e] ... Delete employee");
        viewWriter.println("[l] ... Delete logbook entry");
        viewWriter.println("[p] ... Delete project");
        viewWriter.println("[r] ... Delete requirement");
        viewWriter.println("[s] ... Delete sprint");
        viewWriter.println("[t] ... Delete task");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }
}
