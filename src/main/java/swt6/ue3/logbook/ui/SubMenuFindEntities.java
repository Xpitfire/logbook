package swt6.ue3.logbook.ui;

import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.io.CommandCanceledException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SubMenuFindEntities extends Menu {

    @Override
    public String getMenuTitle() {
        return "Find Entities";
    }

    @Override
    public void run() {
        do {
            input = viewWriter.readLine("> ");

            try {
                if (input.equalsIgnoreCase("m")) {
                    printMenuOptions();
                } else if (input.equalsIgnoreCase("e")) {
                    Employee employee = findEmployee();
                    viewWriter.println(employee);
                } else if (input.equalsIgnoreCase("l")) {
                    LogbookEntry logbookEntry = findLogbookEntry();
                    viewWriter.println(logbookEntry);
                } else if (input.equalsIgnoreCase("p")) {
                    Project project = findProject();
                    viewWriter.println(project);
                } else if (input.equalsIgnoreCase("r")) {
                    Requirement requirement = findRequirement();
                    viewWriter.println(requirement);
                } else if (input.equalsIgnoreCase("s")) {
                    Sprint sprint = findSprint();
                    viewWriter.println(sprint);
                } else if (input.equalsIgnoreCase("t")) {
                    Task task = findTask();
                    viewWriter.println(task);
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
    public Menu printMenuOptions() {
        viewWriter.println("Select an option:");
        printSeparator();
        viewWriter.setIndent(2);
        viewWriter.println("[b] ... Back to previous menu");
        viewWriter.println("[m] ... Print menu");
        viewWriter.newLine();
        viewWriter.println("[e] ... Find employee");
        viewWriter.println("[l] ... Find logbook entry");
        viewWriter.println("[p] ... Find project");
        viewWriter.println("[r] ... Find requirement");
        viewWriter.println("[s] ... Find sprint");
        viewWriter.println("[t] ... Find task");
        viewWriter.resetIndent();
        printSeparator();
        return this;
    }

    public Sprint findSprint() throws CommandCanceledException {
        List<Sprint> sprints = sprintService.findAll();
        String[] tempCmdList = new String[sprints.size()];
        Map<String, Sprint> sprintCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(sprints, tempCmdList, sprintCmdMapping);
        input = viewWriter.blockingReadCommand("Select a sprint: ", tempCmdList);
        return sprintCmdMapping.get(input);
    }

    public Project findProject() throws CommandCanceledException {
        List<Project> projects = projectService.findAll();
        String[] tempCmdList = new String[projects.size()];
        Map<String, Project> projectCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(projects, tempCmdList, projectCmdMapping);
        input = viewWriter.blockingReadCommand("Select a project: ", tempCmdList);
        return projectCmdMapping.get(input);
    }

    public Employee findEmployee() throws CommandCanceledException {
        List<Employee> employees = employeeService.findAll();
        String[] tempCmdList = new String[employees.size()];
        Map<String, Employee> employeeCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(employees, tempCmdList, employeeCmdMapping);
        input = viewWriter.blockingReadCommand("Select an employee: ", tempCmdList);
        return employeeCmdMapping.get(input);
    }

    public Task findTask() throws CommandCanceledException {
        List<Task> tasks = taskService.findAll();
        String[] tempCmdList = new String[tasks.size()];
        Map<String, Task> taskCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(tasks, tempCmdList, taskCmdMapping);
        input = viewWriter.blockingReadCommand("Select a task: ", tempCmdList);
        return taskCmdMapping.get(input);
    }

    public Requirement findRequirement() throws CommandCanceledException {
        List<Requirement> requirements = requirementService.findAll();
        String[] tempCmdList = new String[requirements.size()];
        Map<String, Requirement> requirementCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(requirements, tempCmdList, requirementCmdMapping);
        input = viewWriter.blockingReadCommand("Select a requirement: ", tempCmdList);
        return requirementCmdMapping.get(input);
    }

    public LogbookEntry findLogbookEntry() throws CommandCanceledException {
        List<LogbookEntry> logbookEntries = logbookEntryService.findAll();
        String[] tempCmdList = new String[logbookEntries.size()];
        Map<String, LogbookEntry> logbookCmdMapping = new HashMap<>();
        viewWriter.setIndent(2);
        initializeDataCollections(logbookEntries, tempCmdList, logbookCmdMapping);
        input = viewWriter.blockingReadCommand("Select a logbook entry: ", tempCmdList);
        return logbookCmdMapping.get(input);
    }

    private <T> void initializeDataCollections(List<T> entities, String[] tempCmdList, Map<String, T> entityCmdMapping) {
        for (int i = 0; i < entities.size(); i++) {
            tempCmdList[i] = String.valueOf(i);
            entityCmdMapping.put(tempCmdList[i], entities.get(i));
            viewWriter.print(i);
            viewWriter.print(entities.get(i));
            viewWriter.newLine();
        }
    }
}
