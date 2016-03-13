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

            if (input.equalsIgnoreCase("m")) {
                printMenuOptions();
            } else if (input.equalsIgnoreCase("e")) {
                deleteEmployee();
            } else if (input.equalsIgnoreCase("l")) {
                deleteLogbookEntry();
            } else if (input.equalsIgnoreCase("p")) {
                deleteProject();
            } else if (input.equalsIgnoreCase("r")) {
                deleteRequirement();
            } else if (input.equalsIgnoreCase("s")) {
                deleteSprint();
            } else if (input.equalsIgnoreCase("t")) {
                deleteTask();
            } else if (input.equalsIgnoreCase("b")) {
                // skip
            } else {
                printInvalidInput();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    public void deleteTask() {
    }

    public void deleteSprint() {
    }

    public void deleteRequirement() {
    }

    public void deleteProject() {

    }

    public void deleteLogbookEntry() {
    }

    public void deleteEmployee() {
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.skipLine();
        console.println("[e] ... Delete employee");
        console.println("[l] ... Delete logbook entry");
        console.println("[p] ... Delete project");
        console.println("[r] ... Delete requirement");
        console.println("[s] ... Delete sprint");
        console.println("[t] ... Delete task");
        console.resetIndent();
        printSeparator();
    }
}
