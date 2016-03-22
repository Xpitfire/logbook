package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.PrintController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component
public class FindEntityConsoleView extends AbstractConsoleView {

    @Override
    public String getTitle() {
        return "Find Entities";
    }

    @Autowired
    private PrintController printController;

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    printController.printEntityEmployee();
                } else if (input.equalsIgnoreCase("l")) {
                    printController.printEntityLogbookEntry();
                } else if (input.equalsIgnoreCase("p")) {
                    printController.printEntityProject();
                } else if (input.equalsIgnoreCase("r")) {
                    printController.printEntityRequirement();
                } else if (input.equalsIgnoreCase("s")) {
                    printController.printEntitySprint();
                } else if (input.equalsIgnoreCase("t")) {
                    printController.printEntityTask();
                } else if (input.equalsIgnoreCase("b")) {
                    // skip
                } else {
                    showInvalidInput();
                }
            } catch (CommandCanceledException ex) {
                showUserCancelMessage();
                showEntranceInfo();
            } catch (IllegalStateException isx) {
                showInvalidStateMessage(isx.getMessage());
                showEntranceInfo();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    @Override
    public AbstractConsoleView showMenuOptions() {
        viewWriter.println("Select an option:");
        showSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[e] ... Find employee");
        viewWriter.println("[l] ... Find logbook entry");
        viewWriter.println("[p] ... Find project");
        viewWriter.println("[r] ... Find requirement");
        viewWriter.println("[s] ... Find sprint");
        viewWriter.println("[t] ... Find task");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }

}
