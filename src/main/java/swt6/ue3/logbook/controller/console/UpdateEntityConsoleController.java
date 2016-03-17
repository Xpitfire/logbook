package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Task;
import swt6.ue3.logbook.view.console.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("updateEntityController")
public class UpdateEntityConsoleController extends AbstractConsoleController {

    @Autowired
    private CreateEntityConsoleController createEntityConsoleController;
    @Autowired
    private FindEntityConsoleController findEntityConsoleController;

    @Override
    public String getTitle() {
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
        Employee employee = findEntityConsoleController.findEmployee();
        employee = createEntityConsoleController.createOrUpdateEmployee(employee, false);
        employeeService.save(employee);
        viewWriter.println("Successfully updated employee!");
    }

    public void updateProject() throws CommandCanceledException {
        viewWriter.println("*** Project ***");
        Project project = findEntityConsoleController.findProject();
        project = createEntityConsoleController.createOrUpdateProject(project, false);
        projectService.save(project);
        viewWriter.println("Successfully updated project!");
    }

    public void updateTask() throws CommandCanceledException {
        viewWriter.println("*** Task ***");
        Task task = findEntityConsoleController.findTask();
        task = createEntityConsoleController.createOrUpdateTask(task, false);
        taskService.save(task);
        viewWriter.println("Successfully updated task!");
    }

    public void updateRequirement() throws CommandCanceledException {
        viewWriter.println("*** Requirement ***");
        Requirement requirement = findEntityConsoleController.findRequirement();
        requirement = createEntityConsoleController.createOrUpdateRequirement(requirement, false);
        requirementService.save(requirement);
        viewWriter.println("Successfully updated requirement!");
    }

    @Override
    public AbstractConsoleController printMenuOptions() {
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
