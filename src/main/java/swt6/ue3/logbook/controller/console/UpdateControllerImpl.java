package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.CreateController;
import swt6.ue3.logbook.controller.FindController;
import swt6.ue3.logbook.controller.UpdateController;
import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Task;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("updateEntityController")
public class UpdateControllerImpl implements UpdateController {

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private FindController findController;
    @Autowired
    private CreateController createController;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RequirementService requirementService;

    @Override
    public void updateEmployee() throws CommandCanceledException {
        viewWriter.println("*** Employee ***");
        Employee employee = findController.findEmployee();
        employee = createController.createOrUpdateEmployee(employee, false);
        employeeService.save(employee);
        viewWriter.println("Successfully updated employee!");
    }

    @Override
    public void updateProject() throws CommandCanceledException {
        viewWriter.println("*** Project ***");
        Project project = findController.findProject();
        project = createController.createOrUpdateProject(project, false);
        projectService.save(project);
        viewWriter.println("Successfully updated project!");
    }

    @Override
    public void updateTask() throws CommandCanceledException {
        viewWriter.println("*** Task ***");
        Task task = findController.findTask();
        task = createController.createOrUpdateTask(task, false);
        taskService.save(task);
        viewWriter.println("Successfully updated task!");
    }

    @Override
    public void updateRequirement() throws CommandCanceledException {
        viewWriter.println("*** Requirement ***");
        Requirement requirement = findController.findRequirement();
        requirement = createController.createOrUpdateRequirement(requirement, false);
        requirementService.save(requirement);
        viewWriter.println("Successfully updated requirement!");
    }

}
