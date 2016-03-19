package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.ConfirmationConsumer;
import swt6.ue3.logbook.controller.DeleteController;
import swt6.ue3.logbook.controller.FindController;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("deleteEntityController")
public class DeleteControllerImpl implements DeleteController {

    @Autowired
    private FindController findController;

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

    public void deleteTask(ConfirmationConsumer confirm) throws CommandCanceledException {
        Task task = findController.findTask();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            taskService.remove(task);
            viewWriter.println("Successfully deleted!");
        }
    }

    public void deleteSprint(ConfirmationConsumer confirm) throws CommandCanceledException {
        Sprint sprint = findController.findSprint();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            sprintService.remove(sprint);
            viewWriter.println("Successfully deleted!");
        }
    }

    public void deleteRequirement(ConfirmationConsumer confirm) throws CommandCanceledException {
        Requirement requirement = findController.findRequirement();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            requirementService.remove(requirement);
            viewWriter.println("Successfully deleted!");
        }
    }

    public void deleteProject(ConfirmationConsumer confirm) throws CommandCanceledException {
        Project project = findController.findProject();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            projectService.remove(project);
            viewWriter.println("Successfully deleted!");
        }
    }

    public void deleteLogbookEntry(ConfirmationConsumer confirm) throws CommandCanceledException {
        LogbookEntry logbookEntry = findController.findLogbookEntry();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            logbookEntryService.remove(logbookEntry);
            viewWriter.println("Successfully deleted!");
        }
    }

    public void deleteEmployee(ConfirmationConsumer confirm) throws CommandCanceledException {
        Employee employee = findController.findEmployee();
        boolean result = true;
        if (confirm != null) {
            result = confirm.askForConfirmation();
        }
        if (result) {
            employeeService.remove(employee);
            viewWriter.println("Successfully deleted!");
        }
    }

}
