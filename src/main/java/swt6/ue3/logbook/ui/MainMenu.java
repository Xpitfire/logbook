package swt6.ue3.logbook.ui;

import org.springframework.stereotype.Component;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component("mainMenu")
public class MainMenu extends Menu {

    @Override
    public String getMenuTitle() {
        return "Main Menu";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {

                System.out.println(employeeService.toString());

                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("p")) {
                    new SubMenuPrintStatistics().run();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("f")) {
                    new SubMenuFindEntities().run();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("c")) {
                    new SubMenuCreateEntities().run();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("d")) {
                    new SubMenuDeleteEntities().run();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("l")) {
                    new SubMenuLinkEntities().run();
                    printEntranceInfo();
                } else if (input.equalsIgnoreCase("u")) {
                    new SubMenuUpdateEntities().run();
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
    public Menu printMenuOptions() {
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
