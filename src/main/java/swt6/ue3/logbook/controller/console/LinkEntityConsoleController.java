package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.view.console.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("linkEntityController")
public class LinkEntityConsoleController extends AbstractConsoleController {

    @Autowired
    private CreateEntityConsoleController createEntityConsoleController;
    @Autowired
    private FindEntityConsoleController findEntityConsoleController;

    @Override
    public String getTitle() {
        return "Link Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("lt")) {
                    logbookEntryService.save(linkLogbookEntryTo(selectTask(), true));
                } else if (input.equalsIgnoreCase("le")) {
                    logbookEntryService.save(linkLogbookEntryTo(selectEmployee(), true));
                } else if (input.equalsIgnoreCase("sp")) {
                    sprintService.save(linkSprintTo(selectProject(), true));
                } else if (input.equalsIgnoreCase("sr")) {
                    sprintService.save(linkSprintTo(selectRequirement(), true));
                } else if (input.equalsIgnoreCase("pr")) {
                    projectService.save(linkProjectTo(selectRequirement(), true));
                } else if (input.equalsIgnoreCase("pe")) {
                    projectService.save(linkProjectTo(selectEmployee(), true));
                } else if (input.equalsIgnoreCase("rt")) {
                    requirementService.save(linkRequirementTo(selectTask(), true));
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

    public LogbookEntry selectLogbookEntry() throws CommandCanceledException {
        LogbookEntry logbookEntry;
        input = logbookEntryService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW LOGBOOK ENTRY, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            logbookEntry = createEntityConsoleController.createLogbookEntry(false);
        } else {
            logbookEntry = findEntityConsoleController.findLogbookEntry();
        }
        return logbookEntry;
    }

    public Task selectTask() throws CommandCanceledException {
        Task task;
        input = taskService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW TASK, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            task = createEntityConsoleController.createTask(false);
        } else {
            task = findEntityConsoleController.findTask();
        }
        return task;
    }

    public Sprint selectSprint() throws CommandCanceledException {
        Sprint sprint;
        input = sprintService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW SPRINT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            sprint = createEntityConsoleController.createSprint(false);
        } else {
            sprint = findEntityConsoleController.findSprint();
        }
        return sprint;
    }

    public Employee selectEmployee() throws CommandCanceledException {
        Employee employee;
        input = employeeService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW EMPLOYEE, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            employee = createEntityConsoleController.createEmployee(false);
        } else {
            employee = findEntityConsoleController.findEmployee();
        }
        return employee;
    }

    public Requirement selectRequirement() throws CommandCanceledException {
        Requirement requirement;
        input = requirementService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW REQUIREMENT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            requirement = createEntityConsoleController.createRequirement(false);
        } else {
            requirement = findEntityConsoleController.findRequirement();
        }
        return requirement;
    }

    public Project selectProject() throws CommandCanceledException {
        Project project;
        input = projectService.count() <= 0 ? "n" : viewWriter.blockingReadCommand("Please select an option? [n] = CREATE NEW PROJECT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            project = createEntityConsoleController.createProject(false);
        } else {
            project = findEntityConsoleController.findProject();
        }
        return project;
    }

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

    public Address linkAddressTo(Employee employee, boolean mandatory) throws CommandCanceledException {
        if (mandatory || viewWriter.blockingTypedReadLine("Optionally add an address? (y/n)", Boolean.class)) {
            Address address = createEntityConsoleController.createAddress();
            employee.setAddress(address);
            return address;
        }
        return null;
    }

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

    @Override
    public AbstractConsoleController printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[lt] ... Link logbook entry with task");
        viewWriter.println("[le] ... Link logbook entry with employee");
        viewWriter.println("[pr] ... Link project with requirement");
        viewWriter.println("[pe] ... Link project with employee");
        viewWriter.println("[rt] ... Link requirement with task");
        viewWriter.println("[sp] ... Link sprint with project");
        viewWriter.println("[sr] ... Link sprint with requirement");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }
}
