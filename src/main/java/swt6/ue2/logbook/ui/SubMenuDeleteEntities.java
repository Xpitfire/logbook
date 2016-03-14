package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.*;
import swt6.ue2.logbook.io.CommandCanceledException;
import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuDeleteEntities extends Menu {

    public SubMenuDeleteEntities(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Delete Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

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
        Task task = new SubMenuFindEntities(console, false).findTask();
        showConfirmationMessage();
        taskService.remove(task);
        console.println("Successfully deleted!");
    }

    public void deleteSprint() throws CommandCanceledException {
        Sprint sprint = new SubMenuFindEntities(console, false).findSprint();
        showConfirmationMessage();
        sprintService.remove(sprint);
        console.println("Successfully deleted!");
    }

    public void deleteRequirement() throws CommandCanceledException {
        Requirement requirement = new SubMenuFindEntities(console, false).findRequirement();
        showConfirmationMessage();
        requirementService.remove(requirement);
        console.println("Successfully deleted!");
    }

    public void deleteProject() throws CommandCanceledException {
        Project project = new SubMenuFindEntities(console, false).findProject();
        showConfirmationMessage();
        projectService.remove(project);
        console.println("Successfully deleted!");
    }

    public void deleteLogbookEntry() throws CommandCanceledException {
        LogbookEntry logbookEntry = new SubMenuFindEntities(console, false).findLogbookEntry();
        showConfirmationMessage();
        logbookEntryService.remove(logbookEntry);
        console.println("Successfully deleted!");
    }

    public void deleteEmployee() throws CommandCanceledException {
        Employee employee = new SubMenuFindEntities(console, false).findEmployee();
        showConfirmationMessage();
        employeeService.remove(employee);
        console.println("Successfully deleted!");
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[e] ... Delete employee");
        console.println("[l] ... Delete logbook entry");
        console.println("[p] ... Delete project");
        console.println("[r] ... Delete requirement");
        console.println("[s] ... Delete sprint");
        console.println("[t] ... Delete task");
        console.resetIndent();
        printSeparator();
    }
}
