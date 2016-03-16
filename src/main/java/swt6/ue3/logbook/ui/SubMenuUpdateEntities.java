package swt6.ue3.logbook.ui;

import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Task;
import swt6.ue3.logbook.io.CommandCanceledException;
import swt6.ue3.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuUpdateEntities extends Menu {

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
        Employee employee = new SubMenuFindEntities().findEmployee();
        employee = new SubMenuCreateEntities().createOrUpdateEmployee(employee, false);
        employeeService.save(employee);
        console.println("Successfully updated employee!");
    }

    public void updateProject() throws CommandCanceledException {
        console.println("*** Project ***");
        Project project = new SubMenuFindEntities().findProject();
        project = new SubMenuCreateEntities().createOrUpdateProject(project, false);
        projectService.save(project);
        console.println("Successfully updated project!");
    }

    public void updateTask() throws CommandCanceledException {
        console.println("*** Task ***");
        Task task = new SubMenuFindEntities().findTask();
        task = new SubMenuCreateEntities().createOrUpdateTask(task, false);
        taskService.save(task);
        console.println("Successfully updated task!");
    }

    public void updateRequirement() throws CommandCanceledException {
        console.println("*** Requirement ***");
        Requirement requirement = new SubMenuFindEntities().findRequirement();
        requirement = new SubMenuCreateEntities().createOrUpdateRequirement(requirement, false);
        requirementService.save(requirement);
        console.println("Successfully updated requirement!");
    }

    @Override
    public Menu printMenuOptions() {
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
        return this;
    }

}
