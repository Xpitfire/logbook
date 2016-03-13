package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuPrintStatistics extends Menu {

    protected SubMenuPrintStatistics(Console console) {
        super(console);
    }

    @Override
    public String getMenuTitle() {
        return "Print Statistics";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");
            if (input.equalsIgnoreCase("m")) {
                printMenuOptions();
            } else if (input.equalsIgnoreCase("a")) {
                printAll();
            } else if (input.equalsIgnoreCase("e")) {
                printEmployees();
            } else if (input.equalsIgnoreCase("l")) {
                printLogbookEntries();
            } else if (input.equalsIgnoreCase("p")) {
                printProjects();
            } else if (input.equalsIgnoreCase("r")) {
                printRequirements();
            } else if (input.equalsIgnoreCase("s")) {
                printSprints();
            } else if (input.equalsIgnoreCase("t")) {
                printTasks();
            } else if (input.equalsIgnoreCase("b")) {
                // skip
            } else {
                printInvalidInput();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    public void printAll() {
        printEmployees();
        printLogbookEntries();
        printProjects();
        printTasks();
        printSprints();
        printRequirements();
    }

    public void printSprints() {
        console.println("*** PRINT SPRINTS ***");
        console.setIndent(2);
        sprintDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    public void printRequirements() {
        console.println("*** PRINT REQUIREMENTS ***");
        console.setIndent(2);
        requirementDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    public void printTasks() {
        console.println("*** PRINT TASKS ***");
        console.setIndent(2);
        taskDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    public void printProjects() {
        console.println("*** PRINT PROJECTS ***");
        console.setIndent(2);
        projectDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    public void printLogbookEntries() {
        console.println("*** PRINT LOGBOOK ENTRIES ***");
        console.setIndent(2);
        logbookEntryDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    public void printEmployees() {
        console.println("*** PRINT EMPLOYEES ***");
        console.setIndent(2);
        employeeDao.findAll().forEach(console::println);
        console.resetIndent();
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[a] ... Print all");
        console.println("[e] ... Print employees");
        console.println("[l] ... Print logbook entries");
        console.println("[p] ... Print projects");
        console.println("[r] ... Print requirements");
        console.println("[s] ... Print sprints");
        console.println("[t] ... Print tasks");
        console.resetIndent();
        printSeparator();
    }
}
