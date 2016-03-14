package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.domain.*;
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
                    LogbookEntry logbookEntry = findLogbookEntry();
                    console.println(logbookEntry);
                } else if (input.equalsIgnoreCase("p")) {
                    Project project = findProject();
                    console.println(project);
                } else if (input.equalsIgnoreCase("r")) {
                    Requirement requirement = findRequirement();
                    console.println(requirement);
                } else if (input.equalsIgnoreCase("s")) {
                    Sprint sprint = findSprint();
                    console.println(sprint);
                } else if (input.equalsIgnoreCase("t")) {
                    Task task = findTask();
                    console.println(task);
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

    public Sprint findSprint() throws CommandCanceledException {
        List<Sprint> sprints = sprintService.findAll();
        String[] tempCmdList = new String[sprints.size()];
        Map<String, Sprint> sprintCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(sprints, tempCmdList, sprintCmdMapping);
        input = console.blockingReadCommand("Select a sprint: ", tempCmdList);
        return sprintCmdMapping.get(input);
    }

    public Project findProject() throws CommandCanceledException {
        List<Project> projects = projectService.findAll();
        String[] tempCmdList = new String[projects.size()];
        Map<String, Project> projectCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(projects, tempCmdList, projectCmdMapping);
        input = console.blockingReadCommand("Select a project: ", tempCmdList);
        return projectCmdMapping.get(input);
    }

    public Employee findEmployee() throws CommandCanceledException {
        List<Employee> employees = employeeService.findAll();
        String[] tempCmdList = new String[employees.size()];
        Map<String, Employee> employeeCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(employees, tempCmdList, employeeCmdMapping);
        input = console.blockingReadCommand("Select an employee: ", tempCmdList);
        return employeeCmdMapping.get(input);
    }

    public Task findTask() throws CommandCanceledException {
        List<Task> tasks = taskService.findAll();
        String[] tempCmdList = new String[tasks.size()];
        Map<String, Task> taskCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(tasks, tempCmdList, taskCmdMapping);
        input = console.blockingReadCommand("Select a task: ", tempCmdList);
        return taskCmdMapping.get(input);
    }

    public Requirement findRequirement() throws CommandCanceledException {
        List<Requirement> requirements = requirementService.findAll();
        String[] tempCmdList = new String[requirements.size()];
        Map<String, Requirement> requirementCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(requirements, tempCmdList, requirementCmdMapping);
        input = console.blockingReadCommand("Select a requirement: ", tempCmdList);
        return requirementCmdMapping.get(input);
    }

    public LogbookEntry findLogbookEntry() throws CommandCanceledException {
        List<LogbookEntry> logbookEntries = logbookEntryService.findAll();
        String[] tempCmdList = new String[logbookEntries.size()];
        Map<String, LogbookEntry> logbookCmdMapping = new HashMap<>();
        console.setIndent(2);
        initializeDataCollections(logbookEntries, tempCmdList, logbookCmdMapping);
        input = console.blockingReadCommand("Select a logbook entry: ", tempCmdList);
        return logbookCmdMapping.get(input);
    }

    private <T> void initializeDataCollections(List<T> entities, String[] tempCmdList, Map<String, T> entityCmdMapping) {
        for (int i = 0; i < entities.size(); i++) {
            tempCmdList[i] = String.valueOf(i);
            entityCmdMapping.put(tempCmdList[i], entities.get(i));
            console.print(i);
            console.print(entities.get(i));
            console.newLine();
        }
    }
}
