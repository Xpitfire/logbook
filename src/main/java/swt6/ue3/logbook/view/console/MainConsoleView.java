package swt6.ue3.logbook.view.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component("mainView")
public class MainConsoleView extends AbstractConsoleView {

    @Autowired
    private PrintStatisticConsoleView printStatisticConsoleController;
    @Autowired
    private FindEntityConsoleView findEntityConsoleController;
    @Autowired
    private CreateEntityConsoleView createEntityConsoleController;
    @Autowired
    private DeleteEntityConsoleView deleteEntityConsoleController;
    @Autowired
    private LinkEntityConsoleView linkEntityConsoleController;
    @Autowired
    private UpdateEntityConsoleView updateEntityConsoleController;
    @Autowired
    private InteractiveConsoleView interactiveConsoleView;

    @Override
    public String getTitle() {
        return "Main Menu";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {

                if (input.equalsIgnoreCase("m")) {
                    showMenuOptions();
                } else if (input.equalsIgnoreCase("p")) {
                    printStatisticConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("f")) {
                    findEntityConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("c")) {
                    createEntityConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("d")) {
                    deleteEntityConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("l")) {
                    linkEntityConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("u")) {
                    updateEntityConsoleController.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("i")) {
                    interactiveConsoleView.runAndShowOptions();
                    showEntranceInfo();
                } else if (input.equalsIgnoreCase("q")) {
                    // skip
                } else {
                    showInvalidInput();
                }
            } catch (Exception ex) {
                viewWriter.println("Could not perform operation!");
                viewWriter.println("CAUSE: " + ex.getMessage());
                viewWriter.println("Return to main menu!");
                ex.printStackTrace();
            }

        } while (!input.equalsIgnoreCase("q"));
    }

    @Override
    public AbstractConsoleView showMenuOptions() {
        viewWriter.println("Select an option:");
        showSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[q] ... Quit");
        viewWriter.println("[m] ... Print menu");
        viewWriter.println("[p] ... Print data");
        viewWriter.newLine();
        viewWriter.println("[f] ... Find entities");
        viewWriter.println("[c] ... Create entities");
        viewWriter.println("[d] ... Delete entities");
        viewWriter.println("[l] ... Link entities");
        viewWriter.println("[u] ... Update entities");
        viewWriter.println("[i] ... Interactive mode");
        viewWriter.resetIndent();
        showSeparator();
        return this;
    }

    @Override
    public void close() {
    }

}
