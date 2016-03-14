package swt6.ue2.logbook.test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import swt6.ue2.logbook.dao.Dao;
import swt6.ue2.logbook.dao.DaoFactory;
import swt6.ue2.logbook.domain.*;
import swt6.ue2.logbook.jpa.util.JpaUtil;
import swt6.ue2.util.DateUtil;

import java.sql.SQLSyntaxErrorException;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public abstract class CommonTest {

    protected Dao<Employee> employeeDao;
    protected Dao<LogbookEntry> logbookEntryDao;
    protected Dao<Task> taskDao;
    protected Dao<Project> projectDao;
    protected Dao<Requirement> requirementDao;
    protected Dao<Sprint> sprintDao;

    protected Employee permanentEmployee1;
    protected Employee permanentEmployee2;
    protected Employee temporaryEmployee1;
    protected Project project1;
    protected Project project2;
    protected LogbookEntry logbookEntry1;
    protected LogbookEntry logbookEntry2;
    protected LogbookEntry logbookEntry3;
    protected Task task1;
    protected Task task2;
    protected Task task3;
    protected Requirement requirement1;
    protected Requirement requirement2;
    protected Requirement requirement3;
    protected Sprint sprint1;
    protected Sprint sprint2;


    public static final Operation DELETE_ALL = deleteAllFrom(
            "LOGBOOK_ENTRY", "TASK", "REQUIREMENT", "SPRINT", "PROJECT_EMPLOYEE", "PROJECT", "PERMANENT_EMPLOYEE", "TEMPORARY_EMPLOYEE");

    protected static DriverManagerDestination dbDestination =
            new DriverManagerDestination("jdbc:derby://localhost:1527/WorkLogDb", "APP", "APP");

    public void prepare() {
        Operation operation = sequenceOf(DELETE_ALL);
        DbSetup dbSetup = new DbSetup(dbDestination, operation);
        dbSetup.launch();

        employeeDao = DaoFactory.getDao(Employee.class);
        logbookEntryDao = DaoFactory.getDao(LogbookEntry.class);
        taskDao = DaoFactory.getDao(Task.class);
        projectDao = DaoFactory.getDao(Project.class);
        requirementDao = DaoFactory.getDao(Requirement.class);
        sprintDao = DaoFactory.getDao(Sprint.class);

        permanentEmployee1 = new PermanentEmployee(
                "Jack", "Black",
                DateUtil.getDate(1960, 7, 3),
                new Address("1010", "Wien", "Hauptstraße 1"));
        permanentEmployee2 = new PermanentEmployee("Jack", "Black",
                DateUtil.getDate(1960, 7, 3),
                new Address("1010", "Wien", "Hauptstraße 1"));
        temporaryEmployee1 = new TemporaryEmployee(
                "Johnny", "Cockburn",
                DateUtil.getDate(1979, 3, 12),
                new Address("1040", "Wien", "Salzburgerstraße 12"));

        project1 = new Project("Sprint Planer");
        project2 = new Project("OSGi Modules");

        logbookEntry1 = new LogbookEntry("Programming",
                DateUtil.getTime(2016, 2, 3, 15, 30),
                DateUtil.getTime(2016, 2, 3, 16, 30));
        logbookEntry2 = new LogbookEntry("Testing",
                DateUtil.getTime(2016, 2, 3, 18, 10),
                DateUtil.getTime(2016, 2, 3, 19, 45));
        logbookEntry3 = new LogbookEntry("Designing",
                DateUtil.getTime(2016, 2, 4, 10, 15),
                DateUtil.getTime(2016, 2, 4, 12, 30));

        task1 = new Task("DEV", "Write the code", 20);
        task2 = new Task("TST", "Test the classes", 10);
        task3 = new Task("DEV", "Designing the UI", 5);

        requirement1 = new Requirement("REQ1", "Develop an application");
        requirement2 = new Requirement("REQ2", "Test the database");
        requirement3 = new Requirement("REQ3", "Offer a UI");

        sprint1 = new Sprint();
        sprint2 = new Sprint();
    }

}
