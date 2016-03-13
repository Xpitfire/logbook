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
        console.println("*** Create Sprint ***");
        Project project = new Project();
        project.setLeader(selectEmployee());

        if (immediateSafe) {
            projectDao.safe(project);
            console.println("Project successfully saved!");
        }
        return project;
    }

    public Sprint createSprint(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Sprint ***");
        Sprint sprint = new Sprint();
        sprint.setProject(createProject(false));
        addRequirementOptional(sprint, false);

        if (immediateSafe) {
            sprintDao.safe(sprint);
            console.println("Employee successfully saved!");
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
        addAddressOptional(employee, false);
        if (immediateSafe) {
            employeeDao.safe(employee);
            console.println("Employee successfully saved!");
        }
        return employee;
    }

    private Address createAddress() {
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
        console.println("You require to select an employee for a new logbook entry!");
        Employee employee = selectEmployee();
        logbookEntry.attachEmployee(employee);

        logbookEntry.setActivity(console.blockingTypedReadLine("Activity name", String.class));
        logbookEntry.setStartTime(console.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(console.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));
        addTaskOptional(logbookEntry, false);

        if (immediateSafe) {
            logbookEntryDao.safe(logbookEntry);
            console.println("LogbookEntry successfully saved!");
        }
        return logbookEntry;
    }

    public Task createTask(boolean immediateSafe) throws CommandCanceledException {
        console.println("*** Create Task ***");
        Task task = new Task();
        task.setId(console.blockingTypedReadLine("Task ID", String.class));
        task.setDescription(console.blockingTypedReadLine("Description", String.class, true));
        task.setEstimatedHours(console.blockingTypedReadLine("Estimated hours", Integer.class));
        addRequirementOptional(task, false);
        addLogbookEntryOptional(task, false);

        if (immediateSafe) {
            taskDao.safe(task);
            console.println("Task successfully saved!");
        }
        return task;
    }

    public Task selectTask() throws CommandCanceledException {
        Task task;
        input = taskDao.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW TASK, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            task = createTask(false);
        } else {
            task = new SubMenuFindEntities(console, false).findTask();
        }
        return task;
    }

    public Employee selectEmployee() throws CommandCanceledException {
        Employee employee;
        input = employeeDao.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW EMPLOYEE, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            employee = createEmployee(false);
        } else {
            employee = new SubMenuFindEntities(console, false).findEmployee();
        }
        return employee;
    }

    public Requirement selectRequirement() throws CommandCanceledException {
        Requirement requirement;
        input = requirementDao.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW REQUIREMENT, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            requirement = createRequirement(false);
        } else {
            requirement = new SubMenuFindEntities(console, false).findRequirement();
        }
        return requirement;
    }

    public Requirement createRequirement(boolean immediateSafe) {
        return null;
    }

    public LogbookEntry selectLogbookEntry() throws CommandCanceledException {
        LogbookEntry logbookEntry;
        input = logbookEntryDao.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW LOGBOOK ENTRY, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            logbookEntry = createLogbookEntry(false);
        } else {
            logbookEntry = new SubMenuFindEntities(console, false).findLogbookEntry();
        }
        return logbookEntry;
    }

    public void addLogbookEntryOptional(Task task, boolean interruptWithException) throws CommandCanceledException {
        if (console.blockingTypedReadLine("Optionally add a logbook entry? (y/n)", Boolean.class)) {
            task.addLogbookEntries(selectLogbookEntry());
        } else if (interruptWithException) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
    }

    public void addAddressOptional(Employee employee, boolean interruptWithException) throws CommandCanceledException {
        if (console.blockingTypedReadLine("Optionally add an address? (y/n)", Boolean.class)) {
            employee.setAddress(createAddress());
        } else if (interruptWithException) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
    }

    public void addTaskOptional(LogbookEntry logbookEntry, boolean interruptWithException) throws CommandCanceledException {
        if (console.blockingTypedReadLine("Optionally add a task? (y/n)", Boolean.class)) {
            logbookEntry.attachTask(selectTask());
        } else if (interruptWithException) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
    }

    public void addRequirementOptional(Task task, boolean interruptWithException) throws CommandCanceledException {
        if (console.blockingTypedReadLine("Optionally add a requirement? (y/n)", Boolean.class)) {
            Requirement requirement = selectRequirement();
            task.attachRequirement(requirement);
        } else if (interruptWithException) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
    }

    public void addRequirementOptional(Sprint sprint, boolean interruptWithException) throws CommandCanceledException {
        if (console.blockingTypedReadLine("Optionally add a sprint? (y/n)", Boolean.class)) {
            Requirement requirement = selectRequirement();
            sprint.addRequirement(requirement);
        } else if (interruptWithException) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
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
