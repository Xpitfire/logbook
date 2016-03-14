package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.dal.Dao;
import swt6.ue2.logbook.dal.DaoFactory;
import swt6.ue2.logbook.domain.*;
import swt6.ue2.logbook.io.CommandCanceledException;
import swt6.ue2.logbook.io.Console;
import swt6.ue2.logbook.logic.Service;
import swt6.ue2.logbook.logic.ServiceFactory;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class Menu implements AutoCloseable {

    private static boolean initializing = true;
    protected final Console console;
    protected String input;

    protected final Service<Employee> employeeService = ServiceFactory.getService(Employee.class);
    protected final Service<LogbookEntry> logbookEntryService = ServiceFactory.getService(LogbookEntry.class);
    protected final Service<Task> taskService = ServiceFactory.getService(Task.class);
    protected final Service<Project> projectService = ServiceFactory.getService(Project.class);
    protected final Service<Requirement> requirementService = ServiceFactory.getService(Requirement.class);
    protected final Service<Sprint> sprintService = ServiceFactory.getService(Sprint.class);

    protected Menu(Console console) {
        this(console, true);
    }

    protected Menu(Console console, boolean showEntranceInfo) {
        this.console = console;
        if (initializing) {
            printHeader();
            initializing = false;
        }
        if (showEntranceInfo) {
            console.newLine();
            printEntranceInfo();
        }
    }

    protected void printHeader() {
        console.println(
                " _______             _                           ______                        ____ _______ \n" +
                        "(_______)           | |                         |  ___ \\                      / __ (_______)\n" +
                        " _____   ____  ____ | | ___  _   _  ____ ____   | | _ | |____   ____ ____    ( (__) )_____  \n" +
                        "|  ___) |    \\|  _ \\| |/ _ \\| | | |/ _  ) _  )  | || || |  _ \\ / _  |    \\    \\__  (_____ \\ \n" +
                        "| |_____| | | | | | | | |_| | |_| ( (/ ( (/ /   | || || | | | ( ( | | | | |     / / _____) )\n" +
                        "|_______)_|_|_| ||_/|_|\\___/ \\__  |\\____)____)  |_||_||_|_| |_|\\_|| |_|_|_|    /_/ (______/ \n" +
                        "              |_|           (____/                            (_____|                       ");
    }

    protected void printMenuTile() {
        console.println(String.format("===== [%s] =====", getMenuTitle().toUpperCase()));
    }

    protected void printSeparator() {
        console.println(
                "--------------------------------------------------------------------------------------------");
    }

    protected void printInvalidInput() {
        console.println("Invalid input!");
        console.newLine();
        printMenuOptions();
    }

    protected void printEntranceInfo() {
        printMenuTile();
        printMenuOptions();
    }

    protected void printUserCancelMessage() {
        console.println("Operation canceled by user!");
        console.println("Data may have been lost.");
    }

    protected void showConfirmationMessage() throws CommandCanceledException {
        if (!console.blockingTypedReadLine("Are you sure you want to continue? (y/n)", Boolean.class)) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
    }

    /**
     * Enter menu blocks until the quit option is selected!
     */
    public abstract void run();
    protected abstract String getMenuTitle();
    protected abstract void printMenuOptions();

    @Override
    public void close() { }

}
