package swt6.ue3.logbook.ui;

import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Task;
import swt6.ue3.logbook.io.CommandCanceledException;

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
            input = viewWriter.readLine("> ");

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
        viewWriter.println("*** Employee ***");
        Employee employee = new SubMenuFindEntities().findEmployee();
        employee = new SubMenuCreateEntities().createOrUpdateEmployee(employee, false);
        employeeService.save(employee);
        viewWriter.println("Successfully updated employee!");
    }

    public void updateProject() throws CommandCanceledException {
        viewWriter.println("*** Project ***");
        Project project = new SubMenuFindEntities().findProject();
        project = new SubMenuCreateEntities().createOrUpdateProject(project, false);
        projectService.save(project);
        viewWriter.println("Successfully updated project!");
    }

    public void updateTask() throws CommandCanceledException {
        viewWriter.println("*** Task ***");
        Task task = new SubMenuFindEntities().findTask();
        task = new SubMenuCreateEntities().createOrUpdateTask(task, false);
        taskService.save(task);
        viewWriter.println("Successfully updated task!");
    }

    public void updateRequirement() throws CommandCanceledException {
        viewWriter.println("*** Requirement ***");
        Requirement requirement = new SubMenuFindEntities().findRequirement();
        requirement = new SubMenuCreateEntities().createOrUpdateRequirement(requirement, false);
        requirementService.save(requirement);
        viewWriter.println("Successfully updated requirement!");
    }

    @Override
    public Menu printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[e] ... Update employee");
        viewWriter.println("[p] ... Update project");
        viewWriter.println("[t] ... Update task");
        viewWriter.println("[r] ... Update requirement");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }

}
