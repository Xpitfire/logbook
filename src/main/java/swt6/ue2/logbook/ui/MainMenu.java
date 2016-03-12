package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.io.Console;
import swt6.ue2.logbook.jpa.util.JpaUtil;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class MainMenu extends Menu {

    public MainMenu() {
        super(new Console());
    }

    @Override
    public String getMenuTitle() {
        return "Main Menu";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            if (input.equalsIgnoreCase("m")) {
                printMenuOptions();
            } else if (input.equalsIgnoreCase("p")) {
                new SubMenuPrintStatistics(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("f")) {
                new SubMenuFindEntities(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("c")) {
                new SubMenuCreateEntities(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("d")) {
                new SubMenuDeleteEntities(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("l")) {
                new SubMenuLinkEntities(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("u")) {
                new SubMenuUpdateEntities(console).run();
                printEntranceInfo();
            } else if (input.equalsIgnoreCase("q")) {
                // skip
            } else {
                printInvalidInput();
            }

        } while (!input.equalsIgnoreCase("q"));
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[q] ... Quit");
        console.println("[m] ... Print menu");
        console.println("[p] ... Print statistics");
        console.skipLine();
        console.println("[f] ... Find entities");
        console.println("[c] ... Create entities");
        console.println("[d] ... Delete entities");
        console.println("[l] ... Link entities");
        console.println("[u] ... Update entities");
        console.resetIndent();
        printSeparator();
    }

    @Override
    public void close() {
        JpaUtil.closeEntityManagerFactory();
    }
}
