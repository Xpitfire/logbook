package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.DeleteController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component
public class DeleteEntityConsoleView extends AbstractConsoleView {

    @Autowired
    private DeleteController deleteController;

    @Override
    public String getTitle() {
        return "Delete Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    deleteController.deleteEmployee(() -> showConfirmationMessage() != null);
                } else if (input.equalsIgnoreCase("l")) {
                    deleteController.deleteLogbookEntry(() -> showConfirmationMessage() != null);
                } else if (input.equalsIgnoreCase("p")) {
                    deleteController.deleteProject(() -> showConfirmationMessage() != null);
                } else if (input.equalsIgnoreCase("r")) {
                    deleteController.deleteRequirement(() -> showConfirmationMessage() != null);
                } else if (input.equalsIgnoreCase("s")) {
                    deleteController.deleteSprint(() -> showConfirmationMessage() != null);
                } else if (input.equalsIgnoreCase("t")) {
                    deleteController.deleteTask(() -> showConfirmationMessage() != null);
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
        viewWriter.println("[e] ... Delete employee");
        viewWriter.println("[l] ... Delete logbook entry");
        viewWriter.println("[p] ... Delete project");
        viewWriter.println("[r] ... Delete requirement");
        viewWriter.println("[s] ... Delete sprint");
        viewWriter.println("[t] ... Delete task");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }
}
