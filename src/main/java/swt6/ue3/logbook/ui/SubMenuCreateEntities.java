package swt6.ue3.logbook.ui;

import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.io.CommandCanceledException;
import swt6.ue3.logbook.io.Console;

import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuCreateEntities extends Menu {

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

    private boolean askForSkipOnUpdate(Object entity, String field) {
        return entity == null || console.blockingTypedReadLine(String.format("Update %s? (y/n)", field), Boolean.class);
    }

    private boolean modifyOnlyOnCreate(Object value) {
        return value == null;
    }

    public Project createProject(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateProject(null, immediateSafe);
    }

    public Project createOrUpdateProject(Project value, boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Project ***");
        Project project = value != null ? value : new Project();
        SubMenuLinkEntities submenu = new SubMenuLinkEntities();

        if (modifyOnlyOnCreate(value))
            project.setLeader(submenu.selectEmployee());
        if (askForSkipOnUpdate(value, "project name"))
            project.setName(console.blockingTypedReadLine("Project name", String.class));
        if (askForSkipOnUpdate(value, "link requirements"))
            submenu.linkRequirementTo(project, false);
        if (askForSkipOnUpdate(value, "link sprint"))
            submenu.linkSprintTo(project, false);
        if (askForSkipOnUpdate(value, "link employee"))
            submenu.linkEmployeeTo(project, false);

        if (immediateSafe) {
            projectService.save(project);
            console.println("Successfully saved!");
        }
        return project;
    }

    public Sprint createSprint(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Sprint ***");
        Sprint sprint = new Sprint();
        SubMenuLinkEntities submenu = new SubMenuLinkEntities();
        console.println("You require to select a project to continue:");
        sprint.setProject(submenu.selectProject());
        submenu.linkRequirementTo(sprint, false);

        if (immediateSafe) {
            sprintService.save(sprint);
            console.println("Successfully saved!");
        }
        return sprint;
    }

    public Employee createEmployee(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateEmployee(null, immediateSafe);
    }

    public Employee createOrUpdateEmployee(Employee value, boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Employee ***");
        Employee employee = value;
        if (modifyOnlyOnCreate(value)) {
            input = console.blockingReadCommand("What kind of employee do you want to create? %n [p] = PERMANENT, [t] = TEMPORARY", "p", "t");
            if (input.equalsIgnoreCase("p")) {
                employee = new PermanentEmployee();
            } else {
                employee = new TemporaryEmployee();
            }
        }
        if (askForSkipOnUpdate(value, "first name"))
            employee.setFirstName(console.blockingTypedReadLine("First name", String.class));
        if (askForSkipOnUpdate(value, "last name"))
            employee.setLastName(console.blockingTypedReadLine("Last name", String.class));
        if (modifyOnlyOnCreate(value))
            employee.setDateOfBirth(console.blockingTypedReadLine("Birthday (dd.MM.yyyy)", Date.class));
        if (employee instanceof PermanentEmployee) {
            PermanentEmployee permanentEmployee = (PermanentEmployee)employee;
            if (askForSkipOnUpdate(value, "salary"))
                permanentEmployee.setSalary(console.blockingTypedReadLine("Salary", Double.class));
            if (askForSkipOnUpdate(value, "hours per week"))
                permanentEmployee.setHoursPerWeek(console.blockingTypedReadLine("Hours per week", Integer.class));
        } else {
            TemporaryEmployee temporaryEmployee = (TemporaryEmployee)employee;
            if (modifyOnlyOnCreate(value))
                temporaryEmployee.setRenter(console.blockingTypedReadLine("Renter name", String.class));
            if (modifyOnlyOnCreate(value))
                temporaryEmployee.setStartDate(console.blockingTypedReadLine("Start date (dd.MM.yyyy)", Date.class));
            if (askForSkipOnUpdate(value, "end date"))
                temporaryEmployee.setEndDate(console.blockingTypedReadLine("End date (dd.MM.yyyy)", Date.class, true));
            if (askForSkipOnUpdate(value, "hourly rate"))
                temporaryEmployee.setHourlyRate(console.blockingTypedReadLine("Hourly rate", Double.class));
        }
        if (askForSkipOnUpdate(value, "hourly rate"))
            new SubMenuLinkEntities().linkAddressTo(employee, false);
        if (immediateSafe) {
            employeeService.save(employee);
            console.println("Successfully saved!");
        }
        return employee;
    }

    public Address createAddress() {
        return createOrUpdateAddress(null);
    }

    public Address createOrUpdateAddress(Address value) {
        console.println("*** Address ***");
        Address address = value != null ? value : new Address();
        if (askForSkipOnUpdate(value, "city"))
            address.setCity(console.blockingTypedReadLine("City", String.class));
        if (askForSkipOnUpdate(value, "street"))
            address.setStreet(console.blockingTypedReadLine("Street", String.class));
        if (askForSkipOnUpdate(value, "zip code"))
            address.setZipCode(console.blockingTypedReadLine("Zip code", String.class));
        return address;
    }

    public LogbookEntry createLogbookEntry(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** LogbookEntry ***");
        LogbookEntry logbookEntry = new LogbookEntry();
        console.println("You require to select an employee to continue:");
        Employee employee = new SubMenuLinkEntities().selectEmployee();
        logbookEntry.attachEmployee(employee);
        logbookEntry.setActivity(console.blockingTypedReadLine("Logbook activity", String.class));
        logbookEntry.setStartTime(console.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(console.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));
        new SubMenuLinkEntities().linkTaskTo(logbookEntry, false);

        if (immediateSafe) {
            logbookEntryService.save(logbookEntry);
            console.println("Successfully saved!");
        }
        return logbookEntry;
    }

    public Task createTask(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateTask(null, immediateSafe);
    }

    public Task createOrUpdateTask(Task value, boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Task ***");
        Task task = value != null ? value : new Task();
        if (modifyOnlyOnCreate(value))
            task.setId(console.blockingTypedReadLine("Task ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            task.setDescription(console.blockingTypedReadLine("Description", String.class, true));
        if (modifyOnlyOnCreate(value)) {
            task.setEstimatedHours(console.blockingTypedReadLine("Estimated hours", Integer.class));
            SubMenuLinkEntities subMenuLinkEntities = new SubMenuLinkEntities();
            subMenuLinkEntities.linkLogbookEntryTo(task, false);
            console.println("You require to select a requirement to continue:");
            task.attachRequirement(subMenuLinkEntities.selectRequirement());
        }

        if (immediateSafe) {
            taskService.save(task);
            console.println("Successfully saved!");
        }
        return task;
    }

    public Requirement createRequirement(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateRequirement(null, immediateSafe);
    }

    public Requirement createOrUpdateRequirement(Requirement value, boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Requirement ***");
        Requirement requirement = value != null ? value : new Requirement();
        if (modifyOnlyOnCreate(value))
            requirement.setId(console.blockingTypedReadLine("Requirement ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            requirement.setDescription(console.blockingTypedReadLine("Description", String.class));
        if (modifyOnlyOnCreate(value)) {
            SubMenuLinkEntities subMenuLinkEntities = new SubMenuLinkEntities();
            subMenuLinkEntities.linkTaskTo(requirement, false);
            subMenuLinkEntities.linkSprintTo(requirement, false);
            console.println("You require to select a project to continue:");
            requirement.attachProject(subMenuLinkEntities.selectProject());
        }

        if (immediateSafe) {
            requirementService.save(requirement);
            console.println("Successfully saved!");
        }
        return requirement;
    }

    @Override
    public Menu printMenuOptions() {
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
        return this;
    }

}
