package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.*;
import swt6.ue2.logbook.io.CommandCanceledException;
import swt6.ue2.logbook.io.Console;

import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuCreateEntities extends Menu {

    protected SubMenuCreateEntities(Console console) {
        super(console);
    }

    public SubMenuCreateEntities(Console console, boolean showEntranceInfo) {
        super(console, showEntranceInfo);
    }

    @Override
    public String getMenuTitle() {
        return "Create Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    createEmployee(true);
                } else if (input.equalsIgnoreCase("l")) {
                    createLogbookEntry(true);
                } else if (input.equalsIgnoreCase("p")) {
                    createProject(true);
                } else if (input.equalsIgnoreCase("r")) {
                    createRequirement(true);
                } else if (input.equalsIgnoreCase("s")) {
                    createSprint(true);
                } else if (input.equalsIgnoreCase("t")) {
                    createTask(true);
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

    public Project createProject(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Project ***");
        Project project = new Project();
        SubMenuLinkEntities submenu = new SubMenuLinkEntities(console, false);
        project.setLeader(submenu.selectEmployee());
        project.setName(console.blockingTypedReadLine("Project name", String.class));
        submenu.linkRequirementTo(project, false);
        submenu.linkSprintTo(project, false);
        submenu.linkEmployeeTo(project, false);

        if (immediateSafe) {
            projectDao.safe(project);
            console.println("Successfully saved!");
        }
        return project;
    }

    public Sprint createSprint(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Sprint ***");
        Sprint sprint = new Sprint();
        SubMenuLinkEntities submenu = new SubMenuLinkEntities(console, false);
        console.println("You require to select a project to continue:");
        sprint.setProject(submenu.selectProject());
        submenu.linkRequirementTo(sprint, false);

        if (immediateSafe) {
            sprintDao.safe(sprint);
            console.println("Successfully saved!");
        }
        return sprint;
    }

    public Employee createEmployee(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Employee ***");
        input = console.blockingReadCommand("What kind of employee do you want to create? %n [p] = PERMANENT, [t] = TEMPORARY", "p", "t");
        Employee employee;
        if (input.equalsIgnoreCase("p")) {
            employee = new PermanentEmployee();
        } else {
            employee = new TemporaryEmployee();
        }
        employee.setFirstName(console.blockingTypedReadLine("First name", String.class));
        employee.setLastName(console.blockingTypedReadLine("Last name", String.class));
        employee.setDateOfBirth(console.blockingTypedReadLine("Birthday (dd.MM.yyyy)", Date.class));
        if (employee instanceof PermanentEmployee) {
            PermanentEmployee permanentEmployee = (PermanentEmployee)employee;
            permanentEmployee.setSalary(console.blockingTypedReadLine("Salary", Double.class));
            permanentEmployee.setHoursPerWeek(console.blockingTypedReadLine("Hours per week", Integer.class));
        } else {
            TemporaryEmployee temporaryEmployee = (TemporaryEmployee)employee;
            temporaryEmployee.setRenter(console.blockingTypedReadLine("Renter name", String.class));
            temporaryEmployee.setStartDate(console.blockingTypedReadLine("Start date (dd.MM.yyyy)", Date.class));
            temporaryEmployee.setEndDate(console.blockingTypedReadLine("End date (dd.MM.yyyy)", Date.class, true));
            temporaryEmployee.setHourlyRate(console.blockingTypedReadLine("Hourly rate", Double.class));
        }
        new SubMenuLinkEntities(console, false).linkAddressTo(employee, false);
        if (immediateSafe) {
            employeeDao.safe(employee);
            console.println("Successfully saved!");
        }
        return employee;
    }

    public Address createAddress() {
        console.println("*** Create Address ***");
        Address address = new Address();
        address.setCity(console.blockingTypedReadLine("City", String.class));
        address.setStreet(console.blockingTypedReadLine("Street", String.class));
        address.setZipCode(console.blockingTypedReadLine("Zip code", String.class));
        return address;
    }

    public LogbookEntry createLogbookEntry(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create LogbookEntry ***");
        LogbookEntry logbookEntry = new LogbookEntry();
        console.println("You require to select an employee to continue:");
        Employee employee = new SubMenuLinkEntities(console, false).selectEmployee();
        logbookEntry.attachEmployee(employee);

        logbookEntry.setActivity(console.blockingTypedReadLine("Activity name", String.class));
        logbookEntry.setStartTime(console.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(console.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));
        new SubMenuLinkEntities(console, false).linkTaskTo(logbookEntry, false);

        if (immediateSafe) {
            logbookEntryDao.safe(logbookEntry);
            console.println("Successfully saved!");
        }
        return logbookEntry;
    }

    public Task createTask(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Task ***");
        Task task = new Task();
        task.setId(console.blockingTypedReadLine("Task ID", String.class));
        task.setDescription(console.blockingTypedReadLine("Description", String.class, true));
        task.setEstimatedHours(console.blockingTypedReadLine("Estimated hours", Integer.class));
        SubMenuLinkEntities subMenuLinkEntities = new SubMenuLinkEntities(console, false);
        subMenuLinkEntities.linkLogbookEntryTo(task, false);
        console.println("You require to select a requirement to continue:");
        task.attachRequirement(subMenuLinkEntities.selectRequirement());

        if (immediateSafe) {
            taskDao.safe(task);
            console.println("Successfully saved!");
        }
        return task;
    }

    public Requirement createRequirement(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Requirement ***");
        Requirement requirement = new Requirement();
        requirement.setId(console.blockingTypedReadLine("Requirement ID", String.class));
        requirement.setDescription(console.blockingTypedReadLine("Description", String.class));
        SubMenuLinkEntities subMenuLinkEntities = new SubMenuLinkEntities(console, false);
        subMenuLinkEntities.linkTaskTo(requirement, false);
        subMenuLinkEntities.linkSprintTo(requirement, false);
        console.println("You require to select a project to continue:");
        requirement.attachProject(subMenuLinkEntities.selectProject());

        if (immediateSafe) {
            requirementDao.safe(requirement);
            console.println("Successfully saved!");
        }
        return requirement;
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[e] ... Create employee");
        console.println("[l] ... Create logbook entry");
        console.println("[p] ... Create project");
        console.println("[r] ... Create requirement");
        console.println("[s] ... Create sprint");
        console.println("[t] ... Create task");
        console.resetIndent();
        printSeparator();
    }

}
