package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.dao.Dao;
import swt6.ue2.logbook.dao.DaoFactory;
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

    private Dao<Employee> employeeDao = DaoFactory.getDao(Employee.class);
    private Dao<LogbookEntry> logbookEntryDao = DaoFactory.getDao(LogbookEntry.class);
    private Dao<Task> taskDao = DaoFactory.getDao(Task.class);

    @Override
    public String getMenuTitle() {
        return "Create Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            try {
                if (input.equalsIgnoreCase("e")) {
                    createEmployee();
                } else if (input.equalsIgnoreCase("l")) {
                    createLogbookEntry();
                } else if (input.equalsIgnoreCase("p")) {

                } else if (input.equalsIgnoreCase("r")) {

                } else if (input.equalsIgnoreCase("s")) {

                } else if (input.equalsIgnoreCase("t")) {
                    createTask();
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

    public Employee createEmployee() throws CommandCanceledException {
        return createEmployee(true);
    }

    public Employee createEmployee(boolean safe) throws CommandCanceledException {
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
        if (console.blockingTypedReadLine("Add an address? (y/n)", Boolean.class)) {
            employee.setAddress(createAddress());
        }
        if (safe)
            employeeDao.safe(employee);
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

    public LogbookEntry createLogbookEntry() throws CommandCanceledException {
        console.println("*** Create LogbookEntry ***");
        Employee employee;
        LogbookEntry logbookEntry;
        console.println("You require to select an employee for a new logbook entry!");
        input = employeeDao.count() <= 0 ? "n" : console.blockingReadCommand("Please select an option? [n] = CREATE NEW EMPLOYEE, [s] = SELECT ONE FROM DATABASE", "n", "s");

        if (input.equalsIgnoreCase("n")) {
            employee = createEmployee(false);
        } else {
            employee = new SubMenuFindEntities(console).findEmployee();
        }

        logbookEntry = new LogbookEntry();
        logbookEntry.attachEmployee(employee);
        logbookEntry.setActivity(console.blockingTypedReadLine("Activity name", String.class));
        logbookEntry.setStartTime(console.blockingTypedReadLine("Start time (dd.MM.yyyy HH:mm)", Date.class));
        logbookEntry.setEndTime(console.blockingTypedReadLine("End time (dd.MM.yyyy HH:mm)", Date.class));
        if (console.blockingTypedReadLine("Add a task? (y/n)", Boolean.class)) {
            logbookEntry.attachTask(new SubMenuFindEntities(console).findTask());
        }

        logbookEntryDao.safe(logbookEntry);
        return logbookEntry;
    }

    private Task createTask() {
        console.println("*** Create Task ***");
        Task task = new Task();
        task.setId(console.blockingTypedReadLine("Task ID", String.class));
        task.setDescription(console.blockingTypedReadLine("Description", String.class, true));
        task.setEstimatedHours(console.blockingTypedReadLine("Estimated hours", Integer.class));
        if (console.blockingTypedReadLine("Add a requirement?", Boolean.class)) {
            task.attachRequirement(new SubMenuFindEntities(console).findRequirement());
        }

        taskDao.safe(task);
        return task;
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.skipLine();
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
