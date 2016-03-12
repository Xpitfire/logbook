package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuLinkEntities extends Menu {

    public SubMenuLinkEntities(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Link Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");


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
