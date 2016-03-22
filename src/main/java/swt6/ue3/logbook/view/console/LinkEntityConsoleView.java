package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.LinkController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component
public class LinkEntityConsoleView extends AbstractConsoleView {

    @Autowired
    private LinkController linkController;

    @Override
    public String getTitle() {
        return "Link Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("lt")) {
                    linkController.linkLogbookEntryToTask();
                } else if (input.equalsIgnoreCase("le")) {
                    linkController.linkLogbookEntryToEmployee();
                } else if (input.equalsIgnoreCase("sp")) {
                    linkController.linkSprintToProject();
                } else if (input.equalsIgnoreCase("sr")) {
                    linkController.linkSprintToRequirement();
                } else if (input.equalsIgnoreCase("pr")) {
                    linkController.linkProjectToRequirement();
                } else if (input.equalsIgnoreCase("pe")) {
                    linkController.linkProjectToEmployee();
                } else if (input.equalsIgnoreCase("rt")) {

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
        viewWriter.println("[lt] ... Link logbook entry with task");
        viewWriter.println("[le] ... Link logbook entry with employee");
        viewWriter.println("[pr] ... Link project with requirement");
        viewWriter.println("[pe] ... Link project with employee");
        viewWriter.println("[rt] ... Link requirement with task");
        viewWriter.println("[sp] ... Link sprint with project");
        viewWriter.println("[sr] ... Link sprint with requirement");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }
}
