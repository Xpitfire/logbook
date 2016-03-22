package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.ue3.logbook.view.AppViewBase;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class AbstractConsoleView implements AutoCloseable, AppViewBase {

    protected String input;

    @Autowired
    protected ViewWriter viewWriter;

    public AbstractConsoleView showHeader() {
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

    protected AbstractConsoleView showMenuTile() {
        viewWriter.println(String.format("===== [%s] =====", getTitle().toUpperCase()));
        return this;
    }

    protected AbstractConsoleView showSeparator() {
        viewWriter.println(
                "--------------------------------------------------------------------------------------------");
        return this;
    }

    protected AbstractConsoleView showInvalidInput() {
        viewWriter.println("Invalid input!");
        viewWriter.newLine();
        showMenuOptions();
        return this;
    }

    protected AbstractConsoleView showInvalidStateMessage(String message) {
        viewWriter.printf("Process canceled due to invalid operation: %s%n", message);
        viewWriter.println("Data may have been lost.");
        return this;
    }

    protected AbstractConsoleView showUserCancelMessage() {
        viewWriter.println("Operation canceled by user!");
        viewWriter.println("Data may have been lost.");
        return this;
    }

    protected AbstractConsoleView showConfirmationMessage() throws CommandCanceledException {
        if (!viewWriter.blockingTypedReadLine("Are you sure you want to continue? (y/n)", Boolean.class)) {
            throw new CommandCanceledException("Operation aborted by user!");
        }
        return this;
    }

    public AbstractConsoleView showEntranceInfo() {
        showMenuTile();
        showMenuOptions();
        return this;
    }

    /**
     * Enter menu blocks until the quit option is selected!
     */
    protected abstract AbstractConsoleView showMenuOptions();

    protected void runAndShowOptions() {
        showMenuOptions();
        run();
    }

    @Override
    public void close() { }

}
