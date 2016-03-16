package swt6.ue3.logbook.ui;

import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.io.CommandCanceledException;
import swt6.ue3.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuLinkEntities extends Menu {

    @Override
    public String getMenuTitle() {
        return "Link Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

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
        input = logbookEntryService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW LOGBOOK ENTRY, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            logbookEntry = new SubMenuCreateEntities().createLogbookEntry(false);
        } else {
            logbookEntry = new SubMenuFindEntities().findLogbookEntry();
        }
        return logbookEntry;
    }

    public Task selectTask() throws CommandCanceledException {
        Task task;
        input = taskService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW TASK, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            task = new SubMenuCreateEntities().createTask(false);
        } else {
            task = new SubMenuFindEntities().findTask();
        }
        return task;
    }

    public Sprint selectSprint() throws CommandCanceledException {
        Sprint sprint;
        input = sprintService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW SPRINT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            sprint = new SubMenuCreateEntities().createSprint(false);
        } else {
            sprint = new SubMenuFindEntities().findSprint();
        }
        return sprint;
    }

    public Employee selectEmployee() throws CommandCanceledException {
        Employee employee;
        input = employeeService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW EMPLOYEE, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            employee = new SubMenuCreateEntities().createEmployee(false);
        } else {
            employee = new SubMenuFindEntities().findEmployee();
        }
        return employee;
    }

    public Requirement selectRequirement() throws CommandCanceledException {
        Requirement requirement;
        input = requirementService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW REQUIREMENT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            requirement = new SubMenuCreateEntities().createRequirement(false);
        } else {
            requirement = new SubMenuFindEntities().findRequirement();
        }
        return requirement;
    }

    public Project selectProject() throws CommandCanceledException {
        Project project;
        input = projectService.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW PROJECT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            project = new SubMenuCreateEntities().createProject(false);
        } else {
            project = new SubMenuFindEntities().findProject();
        }
        return project;
    }

    public LogbookEntry linkLogbookEntryTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || console.blockingTypedReadLine("Optionally add a logbook entry? (y/n)", Boolean.class)) {
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
        if (mandatory || console.blockingTypedReadLine("Optionally add an address? (y/n)", Boolean.class)) {
            Address address = new SubMenuCreateEntities().createAddress();
            employee.setAddress(address);
            return address;
        }
        return null;
    }

    public Task linkTaskTo(Object object, boolean mandatory) throws CommandCanceledException {
        if (mandatory || console.blockingTypedReadLine("Optionally add a task? (y/n)", Boolean.class)) {
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
        if (mandatory || console.blockingTypedReadLine("Optionally add a requirement? (y/n)", Boolean.class)) {
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
        if (mandatory || console.blockingTypedReadLine("Optionally add a employee? (y/n)", Boolean.class)) {
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
        if (mandatory || console.blockingTypedReadLine("Optionally add a project? (y/n)", Boolean.class)) {
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
        if (mandatory || console.blockingTypedReadLine("Optionally add a sprint? (y/n)", Boolean.class)) {
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
    public Menu printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[lt] ... Link logbook entry with task");
        console.println("[le] ... Link logbook entry with employee");
        console.println("[pr] ... Link project with requirement");
        console.println("[pe] ... Link project with employee");
        console.println("[rt] ... Link requirement with task");
        console.println("[sp] ... Link sprint with project");
        console.println("[sr] ... Link sprint with requirement");
        console.resetIndent();
        printSeparator();
        return this;
    }
}
