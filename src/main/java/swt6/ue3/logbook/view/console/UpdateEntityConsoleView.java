package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.UpdateController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component
public class UpdateEntityConsoleView extends AbstractConsoleView {

    @Autowired
    private UpdateController updateController;

    @Override
    public String getTitle() {
        return "Update Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    updateController.updateEmployee();
                } else if (input.equalsIgnoreCase("p")) {
                    updateController.updateProject();
                } else if (input.equalsIgnoreCase("t")) {
                    updateController.updateTask();
                } else if (input.equalsIgnoreCase("r")) {
                    updateController.updateRequirement();
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
        viewWriter.println("[e] ... Update employee");
        viewWriter.println("[p] ... Update project");
        viewWriter.println("[t] ... Update task");
        viewWriter.println("[r] ... Update requirement");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }

}
