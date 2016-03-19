package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.CreateController;
import swt6.ue3.logbook.controller.FindController;
import swt6.ue3.logbook.controller.LinkController;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("linkEntityController")
public class LinkControllerImpl implements LinkController {

    private String input;

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
    private CreateController createController;

    @Autowired
    private FindController findController;

    @Override
    public LogbookEntry selectLogbookEntry() throws CommandCanceledException {
        LogbookEntry logbookEntry;
        input = logbookEntryService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW LOGBOOK ENTRY, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            logbookEntry = createController.createLogbookEntry(false);
        } else {
            logbookEntry = findController.findLogbookEntry();
        }
        return logbookEntry;
    }

    @Override
    public Task selectTask() throws CommandCanceledException {
        Task task;
        input = taskService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW TASK, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            task = createController.createTask(false);
        } else {
            task = findController.findTask();
        }
        return task;
    }

    @Override
    public Sprint selectSprint() throws CommandCanceledException {
        Sprint sprint;
        input = sprintService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW SPRINT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            sprint = createController.createSprint(false);
        } else {
            sprint = findController.findSprint();
        }
        return sprint;
    }

    @Override
    public Employee selectEmployee() throws CommandCanceledException {
        Employee employee;
        input = employeeService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW EMPLOYEE, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            employee = createController.createEmployee(false);
        } else {
            employee = findController.findEmployee();
        }
        return employee;
    }

    @Override
    public Requirement selectRequirement() throws CommandCanceledException {
        Requirement requirement;
        input = requirementService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW REQUIREMENT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            requirement = createController.createRequirement(false);
        } else {
            requirement = findController.findRequirement();
        }
        return requirement;
    }

    @Override
    public Project selectProject() throws CommandCanceledException {
        Project project;
        input = projectService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW PROJECT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            project = createController.createProject(false);
        } else {
            project = findController.findProject();
        }
        return project;
    }

    @Override
    public void linkLogbookEntryToTask() {
        logbookEntryService.save(linkLogbookEntryTo(selectTask(), true));
    }

    @Override
    public void linkLogbookEntryToEmployee() {
        logbookEntryService.save(linkLogbookEntryTo(selectEmployee(), true));
    }

    @Override
    public void linkSprintToProject() {
        sprintService.save(linkSprintTo(selectProject(), true));
    }

    @Override
    public void linkSprintToRequirement() {
        sprintService.save(linkSprintTo(selectRequirement(), true));
    }

    @Override
    public void linkProjectToRequirement() {
        projectService.save(linkProjectTo(selectRequirement(), true));
    }

    @Override
    public void linkProjectToEmployee() {
        projectService.save(linkProjectTo(selectEmployee(), true));
    }

    @Override
    public void linkRequirementToTask() {
        requirementService.save(linkRequirementTo(selectTask(), true));
    }

    @Override
    public LogbookEntry linkLogbookEntryTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a logbook entry? (y/n)", Boolean.class)) {
            LogbookEntry logbookEntry = selectLogbookEntry();
            if (object instanceof Task) {
                ((Task)object).addLogbookEntries(logbookEntry);
            } else if (object instanceof Employee) {
                ((Employee)object).addLogbookEntry(logbookEntry);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return logbookEntry;
        }
        return null;
    }

    @Override
    public Address linkAddressTo(Employee employee, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add an address? (y/n)", Boolean.class)) {
            Address address = createController.createAddress();
            employee.setAddress(address);
            return address;
        }
        return null;
    }

    @Override
    public Task linkTaskTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a task? (y/n)", Boolean.class)) {
            Task task = selectTask();
            if (object instanceof LogbookEntry) {
                ((LogbookEntry)object).attachTask(task);
            } else if (object instanceof Requirement) {
                ((Requirement)object).addTask(task);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return task;
        }
        return null;
    }

    @Override
    public Requirement linkRequirementTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a requirement? (y/n)", Boolean.class)) {
            Requirement requirement = selectRequirement();
            if (object instanceof Sprint) {
                ((Sprint)object).addRequirement(requirement);
            } else if (object instanceof Task) {
                ((Task)object).attachRequirement(requirement);
            } else if (object instanceof Project) {
                ((Project)object).addRequirement(requirement);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return requirement;
        }
        return null;
    }

    @Override
    public Employee linkEmployeeTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a employee? (y/n)", Boolean.class)) {
            Employee employee = selectEmployee();
            if (object instanceof Project) {
                ((Project)object).addMember(employee);
            } else if (object instanceof LogbookEntry) {
                ((LogbookEntry)object).attachEmployee(employee);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return employee;
        }
        return null;
    }

    @Override
    public Project linkProjectTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a project? (y/n)", Boolean.class)) {
            Project project = selectProject();
            if (object instanceof Requirement) {
                ((Requirement)object).attachProject(project);
            } else if (object instanceof Sprint) {
                ((Sprint)object).attachProject(project);
            } else if (object instanceof Employee) {
                ((Employee)object).addProject(project);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return project;
        }
        return null;
    }

    @Override
    public Sprint linkSprintTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add a sprint? (y/n)", Boolean.class)) {
            Sprint sprint = selectSprint();
            if (object instanceof Project) {
                ((Project)object).addSprint(sprint);
            } else if (object instanceof Requirement) {
                ((Requirement)object).attachSprint(sprint);
            } else {
                throw new UnsupportedOperationException("Add cannot be applied for this object type!");
            }
            return sprint;
        }
        return null;
    }

}
