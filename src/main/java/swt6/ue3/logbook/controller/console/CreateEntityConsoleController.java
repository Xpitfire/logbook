package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.annotation.SessionExtended;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.view.console.CommandCanceledException;

import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("createEntityController")
public class CreateEntityConsoleController extends AbstractConsoleController {

    @Autowired
    private LinkEntityConsoleController linkEntityConsoleController;

    @Override
    public String getTitle() {
        return "Create Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

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
        return entity == null || viewWriter.blockingTypedReadLine(String.format("Update %s? (y/n)", field), Boolean.class);
    }

    private boolean modifyOnlyOnCreate(Object value) {
        return value == null;
    }

    public Project createProject(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateProject(null, immediateSafe);
    }

    @SessionExtended
    public Project createOrUpdateProject(Project value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Project ***");
        Project project = value != null ? value : new Project();

        if (modifyOnlyOnCreate(value))
            project.setLeader(linkEntityConsoleController.selectEmployee());
        if (askForSkipOnUpdate(value, "project name"))
            project.setName(viewWriter.blockingTypedReadLine("Project name", String.class));
        if (askForSkipOnUpdate(value, "link requirements"))
            linkEntityConsoleController.linkRequirementTo(project, false);
        if (askForSkipOnUpdate(value, "link sprint"))
            linkEntityConsoleController.linkSprintTo(project, false);
        if (askForSkipOnUpdate(value, "link employee"))
            linkEntityConsoleController.linkEmployeeTo(project, false);

        if (immediateSafe) {
            projectService.save(project);
            viewWriter.println("Successfully saved!");
        }
        return project;
    }

    @SessionExtended
    public Sprint createSprint(boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Sprint ***");
        Sprint sprint = new Sprint();
        viewWriter.println("You require to select a project to continue:");
        sprint.setProject(linkEntityConsoleController.selectProject());
        linkEntityConsoleController.linkRequirementTo(sprint, false);

        if (immediateSafe) {
            sprintService.save(sprint);
            viewWriter.println("Successfully saved!");
        }
        return sprint;
    }

    public Employee createEmployee(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateEmployee(null, immediateSafe);
    }

    @SessionExtended
    public Employee createOrUpdateEmployee(Employee value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Employee ***");
        Employee employee = value;
        if (modifyOnlyOnCreate(value)) {
            input = viewWriter.blockingReadCommand("What kind of employee do you want to create? %n [p] = PERMANENT, [t] = TEMPORARY", "p", "t");
            if (input.equalsIgnoreCase("p")) {
                employee = new PermanentEmployee();
            } else {
                employee = new TemporaryEmployee();
            }
        }
        if (askForSkipOnUpdate(value, "first name"))
            employee.setFirstName(viewWriter.blockingTypedReadLine("First name", String.class));
        if (askForSkipOnUpdate(value, "last name"))
            employee.setLastName(viewWriter.blockingTypedReadLine("Last name", String.class));
        if (modifyOnlyOnCreate(value))
            employee.setDateOfBirth(viewWriter.blockingTypedReadLine("Birthday (dd.MM.yyyy)", Date.class));
        if (employee instanceof PermanentEmployee) {
            PermanentEmployee permanentEmployee = (PermanentEmployee)employee;
            if (askForSkipOnUpdate(value, "salary"))
                permanentEmployee.setSalary(viewWriter.blockingTypedReadLine("Salary", Double.class));
            if (askForSkipOnUpdate(value, "hours per week"))
                permanentEmployee.setHoursPerWeek(viewWriter.blockingTypedReadLine("Hours per week", Integer.class));
        } else {
            TemporaryEmployee temporaryEmployee = (TemporaryEmployee)employee;
            if (modifyOnlyOnCreate(value))
                temporaryEmployee.setRenter(viewWriter.blockingTypedReadLine("Renter name", String.class));
            if (modifyOnlyOnCreate(value))
                temporaryEmployee.setStartDate(viewWriter.blockingTypedReadLine("Start date (dd.MM.yyyy)", Date.class));
            if (askForSkipOnUpdate(value, "end date"))
                temporaryEmployee.setEndDate(viewWriter.blockingTypedReadLine("End date (dd.MM.yyyy)", Date.class, true));
            if (askForSkipOnUpdate(value, "hourly rate"))
                temporaryEmployee.setHourlyRate(viewWriter.blockingTypedReadLine("Hourly rate", Double.class));
        }
        if (askForSkipOnUpdate(value, "hourly rate"))
            linkEntityConsoleController.linkAddressTo(employee, false);
        if (immediateSafe) {
            employeeService.save(employee);
            viewWriter.println("Successfully saved!");
        }
        return employee;
    }

    public Address createAddress() {
        return createOrUpdateAddress(null);
    }

    public Address createOrUpdateAddress(Address value) {
        viewWriter.println("*** Address ***");
        Address address = value != null ? value : new Address();
        if (askForSkipOnUpdate(value, "city"))
            address.setCity(viewWriter.blockingTypedReadLine("City", String.class));
        if (askForSkipOnUpdate(value, "street"))
            address.setStreet(viewWriter.blockingTypedReadLine("Street", String.class));
        if (askForSkipOnUpdate(value, "zip code"))
            address.setZipCode(viewWriter.blockingTypedReadLine("Zip code", String.class));
        return address;
    }

    @SessionExtended
    public LogbookEntry createLogbookEntry(boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** LogbookEntry ***");
        LogbookEntry logbookEntry = new LogbookEntry();
        viewWriter.println("You require to select an employee to continue:");
        Employee employee = linkEntityConsoleController.selectEmployee();
        logbookEntry.attachEmployee(employee);
        logbookEntry.setActivity(viewWriter.blockingTypedReadLine("Logbook activity", String.class));
        logbookEntry.setStartTime(viewWriter.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(viewWriter.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));
        linkEntityConsoleController.linkTaskTo(logbookEntry, false);

        if (immediateSafe) {
            logbookEntryService.save(logbookEntry);
            viewWriter.println("Successfully saved!");
        }
        return logbookEntry;
    }

    public Task createTask(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateTask(null, immediateSafe);
    }

    @SessionExtended
    public Task createOrUpdateTask(Task value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Task ***");
        Task task = value != null ? value : new Task();
        if (modifyOnlyOnCreate(value))
            task.setId(viewWriter.blockingTypedReadLine("Task ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            task.setDescription(viewWriter.blockingTypedReadLine("Description", String.class, true));
        if (modifyOnlyOnCreate(value)) {
            task.setEstimatedHours(viewWriter.blockingTypedReadLine("Estimated hours", Integer.class));
            linkEntityConsoleController.linkLogbookEntryTo(task, false);
            viewWriter.println("You require to select a requirement to continue:");
            task.attachRequirement(linkEntityConsoleController.selectRequirement());
        }

        if (immediateSafe) {
            taskService.save(task);
            viewWriter.println("Successfully saved!");
        }
        return task;
    }

    public Requirement createRequirement(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateRequirement(null, immediateSafe);
    }

    @SessionExtended
    public Requirement createOrUpdateRequirement(Requirement value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Requirement ***");
        Requirement requirement = value != null ? value : new Requirement();
        if (modifyOnlyOnCreate(value))
            requirement.setId(viewWriter.blockingTypedReadLine("Requirement ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            requirement.setDescription(viewWriter.blockingTypedReadLine("Description", String.class));
        if (modifyOnlyOnCreate(value)) {
            linkEntityConsoleController.linkTaskTo(requirement, false);
            linkEntityConsoleController.linkSprintTo(requirement, false);
            viewWriter.println("You require to select a project to continue:");
            requirement.attachProject(linkEntityConsoleController.selectProject());
        }

        if (immediateSafe) {
            requirementService.save(requirement);
            viewWriter.println("Successfully saved!");
        }
        return requirement;
    }

    @Override
    public AbstractConsoleController printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[e] ... Create employee");
        viewWriter.println("[l] ... Create logbook entry");
        viewWriter.println("[p] ... Create project");
        viewWriter.println("[r] ... Create requirement");
        viewWriter.println("[s] ... Create sprint");
        viewWriter.println("[t] ... Create task");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }

}
