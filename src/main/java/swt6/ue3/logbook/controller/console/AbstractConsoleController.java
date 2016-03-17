package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.ue3.logbook.controller.AppController;
import swt6.ue3.logbook.view.console.CommandCanceledException;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.logic.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class AbstractConsoleController implements AutoCloseable, AppController {

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

    public void setViewWriter(ViewWriter viewWriter) {
        this.viewWriter = viewWriter;
    }

    public AbstractConsoleController printHeader() {
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

    protected AbstractConsoleController printMenuTile() {
        viewWriter.println(String.format("===== [%s] =====", getTitle().toUpperCase()));
        return this;
    }

    protected AbstractConsoleController printSeparator() {
        viewWriter.println(
                "--------------------------------------------------------------------------------------------");
        return this;
    }

    protected AbstractConsoleController printInvalidInput() {
        viewWriter.println("Invalid input!");
        viewWriter.newLine();
        printMenuOptions();
        return this;
    }

    protected AbstractConsoleController printUserCancelMessage() {
        viewWriter.println("Operation canceled by user!");
        viewWriter.println("Data may have been lost.");
        return this;
    }

    protected AbstractConsoleController showConfirmationMessage() throws CommandCanceledException {
        if (!viewWriter.blockingTypedReadLine("Are you sure you want to continue? (y/n)", Boolean.class)) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
        return this;
    }

    public AbstractConsoleController printEntranceInfo() {
        printMenuTile();
        printMenuOptions();
        return this;
    }

    /**
     * Enter menu blocks until the quit option is selected!
     */
    protected abstract AbstractConsoleController printMenuOptions();

    protected void runAndPrintOptions() {
        printMenuOptions();
        run();
    }

    @Override
    public void close() { }

}
