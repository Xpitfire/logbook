package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.CreateController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component
public class CreateEntityConsoleView extends AbstractConsoleView {

    @Autowired
    private CreateController createController;

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
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    createController.createEmployee(true);
                } else if (input.equalsIgnoreCase("l")) {
                    createController.createLogbookEntry(true);
                } else if (input.equalsIgnoreCase("p")) {
                    createController.createProject(true);
                } else if (input.equalsIgnoreCase("r")) {
                    createController.createRequirement(true);
                } else if (input.equalsIgnoreCase("s")) {
                    createController.createSprint(true);
                } else if (input.equalsIgnoreCase("t")) {
                    createController.createTask(true);
                } else if (input.equalsIgnoreCase("b")) {
                    // skip
                } else {
                    showInvalidInput();
                }
            } catch (CommandCanceledException ex) {
                showUserCancelMessage();
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
        viewWriter.println("[e] ... Create employee");
        viewWriter.println("[l] ... Create logbook entry");
        viewWriter.println("[p] ... Create project");
        viewWriter.println("[r] ... Create requirement");
        viewWriter.println("[s] ... Create sprint");
        viewWriter.println("[t] ... Create task");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }

}
