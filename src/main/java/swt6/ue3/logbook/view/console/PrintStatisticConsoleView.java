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
public class PrintStatisticConsoleView extends AbstractConsoleView {

    @Autowired
    private PrintController printController;

    @Override
    public String getTitle() {
        return "Print Statistics";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("a")) {
                    printController.printEntityAll();
                } else if (input.equalsIgnoreCase("e")) {
                    printController.printEntityEmployees();
                } else if (input.equalsIgnoreCase("l")) {
                    printController.printEntityLogbookEntries();
                } else if (input.equalsIgnoreCase("p")) {
                    printController.printEntityProjects();
                } else if (input.equalsIgnoreCase("r")) {
                    printController.printEntityRequirements();
                } else if (input.equalsIgnoreCase("s")) {
                    printController.printEntitySprints();
                } else if (input.equalsIgnoreCase("u")) {
                    printController.printStatsBurndownCharts();
                } else if (input.equalsIgnoreCase("c")) {
                    printController.printStatsProjectTotalCosts();
                } else if (input.equalsIgnoreCase("t")) {
                    printController.printEntityTasks();
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
        viewWriter.println("[a] ... Print all");
        viewWriter.println("[u] ... Print burn down chart");
        viewWriter.println("[c] ... Print costs per project");
        viewWriter.newLine();
        viewWriter.println("[e] ... Print employees");
        viewWriter.println("[l] ... Print logbook entries");
        viewWriter.println("[p] ... Print projects");
        viewWriter.println("[r] ... Print requirements");
        viewWriter.println("[s] ... Print sprints");
        viewWriter.println("[t] ... Print tasks");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }
}
