package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.controller.InteractiveController;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 22.03.2016
 */
@Component
public class InteractiveConsoleView extends AbstractConsoleView {

    @Autowired
    private InteractiveController interactiveController;

    @Override
    public String getTitle() {
        return "Interactive View";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("1")) {
                    interactiveController.printAllEmployees();
                } else if (input.equalsIgnoreCase("2")) {
                    interactiveController.createProject();
                } else if (input.equalsIgnoreCase("3")) {
                    interactiveController.linkEmployeeToProject();
                } else if (input.equalsIgnoreCase("4")) {
                    interactiveController.unlinkEmployeeFromProject();
                } else if (input.equalsIgnoreCase("5")) {
                    interactiveController.printProjectMembers();
                } else if (input.equalsIgnoreCase("6")) {
                    interactiveController.printRequirementsGroupedBySprints();
                } else if (input.equalsIgnoreCase("7")) {
                    interactiveController.printRequirementTasks();
                } else if (input.equalsIgnoreCase("8")) {
                    interactiveController.linkRequirementToProject();
                } else if (input.equalsIgnoreCase("9")) {
                    interactiveController.linkRequirementToSprint();
                } else if (input.equalsIgnoreCase("10")) {
                    interactiveController.createRequirementTask();
                } else if (input.equalsIgnoreCase("11")) {
                    interactiveController.modifyTask();
                } else if (input.equalsIgnoreCase("12")) {
                    interactiveController.deleteTask();
                } else if (input.equalsIgnoreCase("13")) {
                    interactiveController.printBurnDownChartPerSprint();
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
    protected AbstractConsoleView showMenuOptions() {
        viewWriter.println("Select an option:");
        showSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b]  ... Back to previous menu");
        viewWriter.println("[m]  ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[1]  ... Print all employees");
        viewWriter.println("[2]  ... Create new project");
        viewWriter.println("[3]  ... Add employee to project");
        viewWriter.println("[4]  ... Remove employee from project");
        viewWriter.println("[5]  ... Print project members");
        viewWriter.println("[6]  ... Print requirements grouped by sprints");
        viewWriter.println("[7]  ... Print requirement tasks");
        viewWriter.println("[8]  ... Add requirement to project");
        viewWriter.println("[9]  ... Add requirement to sprint");
        viewWriter.println("[10] ... Create new requirement task");
        viewWriter.println("[11] ... Modify task");
        viewWriter.println("[12] ... Delete task");
        viewWriter.println("[13] ... Print project Burn-Down-Chart per sprint");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }
}
