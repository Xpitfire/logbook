package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.Requirement;
import swt6.ue2.logbook.domain.Task;
import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuFindEntities extends Menu {

    protected SubMenuFindEntities(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Find Entities";
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
        console.println("[e] ... Find employee");
        console.println("[l] ... Find logbook entry");
        console.println("[p] ... Find project");
        console.println("[r] ... Find requirement");
        console.println("[s] ... Find sprint");
        console.println("[t] ... Find task");
        console.resetIndent();
        printSeparator();
    }


    public Employee findEmployee() {
        return null;
    }

    public Task findTask() {
        return null;
    }

    public Requirement findRequirement() {
        return null;
    }
}
