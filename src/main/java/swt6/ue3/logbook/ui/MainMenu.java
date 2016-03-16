package swt6.ue3.logbook.ui;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class MainMenu extends Menu {

    @Override
    public String getMenuTitle() {
        return "Main Menu";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

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
                console.println("Could not perform operation!");
                console.println("CAUSE: " + ex.getMessage());
                console.println("Return to main menu!");
                ex.printStackTrace();
            }

        } while (!input.equalsIgnoreCase("q"));
    }

    @Override
    public Menu printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[q] ... Quit");
        console.println("[m] ... Print menu");
        console.println("[p] ... Print data");
        console.newLine();
        console.println("[f] ... Find entities");
        console.println("[c] ... Create entities");
        console.println("[d] ... Delete entities");
        console.println("[l] ... Link entities");
        console.println("[u] ... Update entities");
        console.resetIndent();
        printSeparator();
        return this;
    }

    @Override
    public void close() {
    }
}
