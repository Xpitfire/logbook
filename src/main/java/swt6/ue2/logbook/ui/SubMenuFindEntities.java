package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.Requirement;
import swt6.ue2.logbook.domain.Task;
import swt6.ue2.logbook.io.CommandCanceledException;
import swt6.ue2.logbook.io.Console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuFindEntities extends Menu {

    protected SubMenuFindEntities(Console console) {
        super(console);
    }

    protected SubMenuFindEntities(Console console, boolean showEntranceInfo) {
        super(console, showEntranceInfo);
    }

    @Override
    public String getMenuTitle() {
        return "Find Entities";
    }

    @Override
    public void run() {
        do {
            input = console.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    Employee employee = findEmployee();
                    console.println(employee);
                } else if (input.equalsIgnoreCase("l")) {

                } else if (input.equalsIgnoreCase("p")) {

                } else if (input.equalsIgnoreCase("r")) {

                } else if (input.equalsIgnoreCase("s")) {

                } else if (input.equalsIgnoreCase("t")) {

                } else if (input.equalsIgnoreCase("b")) {
                    // skip
                } else {
                    printInvalidInput();
                }
            } catch (CommandCanceledException ex) {
                printUserCancelMessage();
                printEntranceInfo();
            }

        } while (!input.equalsIgnoreCase("b"));
    }

    @Override
    public void printMenuOptions() {
        console.println("Select an option:");
        printSeparator();
        console.setIndent(2);
        console.println("[b] ... Back to previous menu");
        console.println("[m] ... Print menu");
        console.newLine();
        console.println("[e] ... Find employee");
        console.println("[l] ... Find logbook entry");
        console.println("[p] ... Find project");
        console.println("[r] ... Find requirement");
        console.println("[s] ... Find sprint");
        console.println("[t] ... Find task");
        console.resetIndent();
        printSeparator();
    }


    public Employee findEmployee() throws CommandCanceledException {
        List<Employee> employees = employeeDao.findAll();
        String[] tempCmdList = new String[employees.size()];
        Map<String, Employee> employeeCmdMapping = new HashMap<>();
        console.setIndent(2);
        for (int i = 0; i < employees.size(); i++) {
            tempCmdList[i] = String.valueOf(i);
            employeeCmdMapping.put(tempCmdList[i], employees.get(i));
            console.print(i);
            console.print(employees.get(i));
            console.newLine();
        }
        input = console.blockingReadCommand("Select an employee to delete: ", tempCmdList);
        return employeeCmdMapping.get(input);
    }

    public Task findTask() {
        return null;
    }

    public Requirement findRequirement() {
        return null;
    }
}
