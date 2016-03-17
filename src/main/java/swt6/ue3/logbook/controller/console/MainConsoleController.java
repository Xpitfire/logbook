package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Controller("mainController")
public class MainConsoleController extends AbstractConsoleController {

    @Autowired
    private PrintStatisticConsoleController printStatisticConsoleController;
    @Autowired
    private FindEntityConsoleController findEntityConsoleController;
    @Autowired
    private CreateEntityConsoleController createEntityConsoleController;
    @Autowired
    private DeleteEntityConsoleController deleteEntityConsoleController;
    @Autowired
    private LinkEntityConsoleController linkEntityConsoleController;
    @Autowired
    private UpdateEntityConsoleController updateEntityConsoleController;

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
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("p")) {
                    printStatisticConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("f")) {
                    findEntityConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("c")) {
                    createEntityConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("d")) {
                    deleteEntityConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("l")) {
                    linkEntityConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("u")) {
                    updateEntityConsoleController.runAndPrintOptions();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("q")) {
                    // skip
                } else {
                    printInvalidInput();
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
    public AbstractConsoleController printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
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
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }

    @Override
    public void close() {
    }

}
