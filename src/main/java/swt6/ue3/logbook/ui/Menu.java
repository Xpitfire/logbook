package swt6.ue3.logbook.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import swt6.ue3.logbook.io.CommandCanceledException;
import swt6.ue3.logbook.io.ViewWriter;
import swt6.ue3.logbook.logic.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class Menu implements AutoCloseable {

    protected String input;

    @Autowired
    protected ViewWriter viewWriter;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected LogbookEntryService logbookEntryService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected ProjectService projectService;
    @Autowired
    protected RequirementService requirementService;
    @Autowired
    protected SprintService sprintService;

    public Menu() {
    }

    public void setViewWriter(ViewWriter viewWriter) {
        this.viewWriter = viewWriter;
    }

    public Menu printHeader() {
        viewWriter.println(
                " _______             _                           ______                        ____ _______ \n" +
                        "(_______)           | |                         |  ___ \\                      / __ (_______)\n" +
                        " _____   ____  ____ | | ___  _   _  ____ ____   | | _ | |____   ____ ____    ( (__) )_____  \n" +
                        "|  ___) |    \\|  _ \\| |/ _ \\| | | |/ _  ) _  )  | || || |  _ \\ / _  |    \\    \\__  (_____ \\ \n" +
                        "| |_____| | | | | | | | |_| | |_| ( (/ ( (/ /   | || || | | | ( ( | | | | |     / / _____) )\n" +
                        "|_______)_|_|_| ||_/|_|\\___/ \\__  |\\____)____)  |_||_||_|_| |_|\\_|| |_|_|_|    /_/ (______/ \n" +
                        "              |_|           (____/                            (_____|                       ");
        return this;
    }

    protected Menu printMenuTile() {
        viewWriter.println(String.format("===== [%s] =====", getMenuTitle().toUpperCase()));
        return this;
    }

    protected Menu printSeparator() {
        viewWriter.println(
                "--------------------------------------------------------------------------------------------");
        return this;
    }

    protected Menu printInvalidInput() {
        viewWriter.println("Invalid input!");
        viewWriter.newLine();
        printMenuOptions();
        return this;
    }

    protected Menu printUserCancelMessage() {
        viewWriter.println("Operation canceled by user!");
        viewWriter.println("Data may have been lost.");
        return this;
    }

    protected Menu showConfirmationMessage() throws CommandCanceledException {
        if (!viewWriter.blockingTypedReadLine("Are you sure you want to continue? (y/n)", Boolean.class)) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
        return this;
    }

    public Menu printEntranceInfo() {
        printMenuTile();
        printMenuOptions();
        return this;
    }

    /**
     * Enter menu blocks until the quit option is selected!
     */
    public abstract void run();
    protected abstract String getMenuTitle();
    protected abstract Menu printMenuOptions();

    @Override
    public void close() { }

}
