package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.Project;
import swt6.ue2.logbook.domain.Requirement;
import swt6.ue2.logbook.domain.Task;
import swt6.ue2.logbook.io.CommandCanceledException;
import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuUpdateEntities extends Menu {

    protected SubMenuUpdateEntities(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Update Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    updateEmployee();
                } else if (input.equalsIgnoreCase("p")) {
                    updateProject();
                } else if (input.equalsIgnoreCase("t")) {
                    updateTask();
                } else if (input.equalsIgnoreCase("r")) {
                    updateRequirement();
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

    public void updateEmployee() throws CommandCanceledException {
        console.println("*** Employee ***");
        Employee employee = new SubMenuFindEntities(console, false).findEmployee();
        employee = new SubMenuCreateEntities(console, false).createOrUpdateEmployee(employee, false);
        employeeService.safe(employee);
        console.println("Successfully updated employee!");
    }

    public void updateProject() throws CommandCanceledException {
        console.println("*** Project ***");
        Project project = new SubMenuFindEntities(console, false).findProject();
        project = new SubMenuCreateEntities(console, false).createOrUpdateProject(project, false);
        projectService.safe(project);
        console.println("Successfully updated project!");
    }

    public void updateTask() throws CommandCanceledException {
        console.println("*** Task ***");
        Task task = new SubMenuFindEntities(console, false).findTask();
        task = new SubMenuCreateEntities(console, false).createOrUpdateTask(task, false);
        taskService.safe(task);
        console.println("Successfully updated task!");
    }

    public void updateRequirement() throws CommandCanceledException {
        console.println("*** Requirement ***");
        Requirement requirement = new SubMenuFindEntities(console, false).findRequirement();
        requirement = new SubMenuCreateEntities(console, false).createOrUpdateRequirement(requirement, false);
        requirementService.safe(requirement);
        console.println("Successfully updated requirement!");
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[e] ... Update employee");
        console.println("[p] ... Update project");
        console.println("[t] ... Update task");
        console.println("[r] ... Update requirement");
        console.resetIndent();
        printSeparator();
    }

}
