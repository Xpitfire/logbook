package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.CreateController;
import swt6.ue3.logbook.controller.LinkController;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("createEntityController")
public class CreateControllerImpl implements CreateController {

    private String input;

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private LinkController linkController;

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


    private boolean askForSkipOnUpdate(Object entity, String field) {
        return entity == null || viewWriter.blockingTypedReadLine(String.format("Update %s? (y/n)", field), Boolean.class);
    }

    private boolean modifyOnlyOnCreate(Object value) {
        return value == null;
    }

    @Override
    public Project createProject(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateProject(null, immediateSafe);
    }

    @Override
    public Project createOrUpdateProject(Project value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Project ***");
        Project project = value != null ? value : new Project();

        if (modifyOnlyOnCreate(value))
            project.setLeader(linkController.selectEmployee());
        if (askForSkipOnUpdate(value, "project name"))
            project.setName(viewWriter.blockingTypedReadLine("Project name", String.class));

        if (immediateSafe) {
            projectService.save(project);
            viewWriter.println("Successfully saved!");
        }
        return project;
    }

    @Override
    public Sprint createSprint(boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Sprint ***");
        Sprint sprint = new Sprint();
        viewWriter.println("You require to select a project to continue:");
        sprint.setProject(linkController.selectProject());

        if (immediateSafe) {
            sprintService.save(sprint);
            viewWriter.println("Successfully saved!");
        }
        return sprint;
    }

    @Override
    public Employee createEmployee(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateEmployee(null, immediateSafe);
    }

    @Override
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
            linkController.linkAddressTo(employee, false);
        if (immediateSafe) {
            employeeService.save(employee);
            viewWriter.println("Successfully saved!");
        }
        return employee;
    }

    @Override
    public Address createAddress() {
        return createOrUpdateAddress(null);
    }

    @Override
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

    @Override
    public LogbookEntry createLogbookEntry(boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** LogbookEntry ***");
        LogbookEntry logbookEntry = new LogbookEntry();
        viewWriter.println("You require to select an employee to continue:");
        Employee employee = linkController.selectEmployee();
        logbookEntry.attachEmployee(employee);
        logbookEntry.setActivity(viewWriter.blockingTypedReadLine("Logbook activity", String.class));
        logbookEntry.setStartTime(viewWriter.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(viewWriter.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));

        if (immediateSafe) {
            logbookEntryService.save(logbookEntry);
            viewWriter.println("Successfully saved!");
        }
        return logbookEntry;
    }

    @Override
    public Task createTask(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateTask(null, immediateSafe);
    }

    @Override
    public Task createOrUpdateTask(Task value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Task ***");
        Task task = value != null ? value : new Task();
        if (modifyOnlyOnCreate(value))
            task.setId(viewWriter.blockingTypedReadLine("Task ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            task.setDescription(viewWriter.blockingTypedReadLine("Description", String.class, true));
        if (modifyOnlyOnCreate(value)) {
            task.setEstimatedHours(viewWriter.blockingTypedReadLine("Estimated hours", Integer.class));
            viewWriter.println("You require to select a requirement to continue:");
            task.attachRequirement(linkController.selectRequirement());
        }

        if (immediateSafe) {
            taskService.save(task);
            viewWriter.println("Successfully saved!");
        }
        return task;
    }

    @Override
    public Requirement createRequirement(boolean immediateSafe) throws CommandCanceledException {
        return createOrUpdateRequirement(null, immediateSafe);
    }

    @Override
    public Requirement createOrUpdateRequirement(Requirement value, boolean immediateSafe) throws CommandCanceledException {
        viewWriter.println("*** Requirement ***");
        Requirement requirement = value != null ? value : new Requirement();
        if (modifyOnlyOnCreate(value))
            requirement.setId(viewWriter.blockingTypedReadLine("Requirement ID", String.class));
        if (askForSkipOnUpdate(value, "description"))
            requirement.setDescription(viewWriter.blockingTypedReadLine("Description", String.class));
        if (modifyOnlyOnCreate(value)) {
            viewWriter.println("You require to select a project to continue:");
            requirement.attachProject(linkController.selectProject());
        }

        if (immediateSafe) {
            requirementService.save(requirement);
            viewWriter.println("Successfully saved!");
        }
        return requirement;
    }

}
