package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuDeleteEntities extends Menu {

    public SubMenuDeleteEntities(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Delete Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {

            } else if (input.equalsIgnoreCase("e")) {
                // skip
            } else {
                printInvalidInput();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.skipLine();
        console.resetIndent();
        printSeparator();
    }
}
