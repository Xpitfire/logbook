package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.FindController;
import swt6.ue3.logbook.domain.*;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@Controller("findEntityController")
public class FindControllerImpl implements FindController {

    private String input;

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LogbookEntryService logbookEntryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RequirementService requirementService;
    @Autowired
    private SprintService sprintService;

    @Override
    public Sprint findSprint() throws CommandCanceledException {
        return findSprint(null);
    }

    @Override
    public Sprint findSprint(Project project) {
        List<Sprint> sprints = sprintService.findAll();
        Optional<Sprint> sprintOptional = null;
        if (project != null) {
            sprintOptional = sprints.stream().filter(
                    sprint -> project.equals(sprint.getProject())).findFirst();
        }
        if (sprintOptional.get() != null) {
            return sprintOptional.get();
        } else {
            String[] tempCmdList = new String[sprints.size()];
            Map<String, Sprint> sprintCmdMapping = new HashMap<>();
            initializeDataCollections(sprints, tempCmdList, sprintCmdMapping);
            input = viewWriter.blockingReadCommand("Select a sprint: [0]..[n]", tempCmdList);
            return sprintCmdMapping.get(input);
        }
    }

    @Override
    public Project findProject() throws CommandCanceledException {
        List<Project> projects = projectService.findAll();
        String[] tempCmdList = new String[projects.size()];
        Map<String, Project> projectCmdMapping = new HashMap<>();
        initializeDataCollections(projects, tempCmdList, projectCmdMapping);
        input = viewWriter.blockingReadCommand("Select a project: [0]..[n]", tempCmdList);
        return projectCmdMapping.get(input);
    }

    @Override
    public Employee findEmployee() throws CommandCanceledException {
        List<Employee> employees = employeeService.findAll();
        String[] tempCmdList = new String[employees.size()];
        Map<String, Employee> employeeCmdMapping = new HashMap<>();
        initializeDataCollections(employees, tempCmdList, employeeCmdMapping);
        input = viewWriter.blockingReadCommand("Select an employee: [0]..[n]", tempCmdList);
        return employeeCmdMapping.get(input);
    }

    @Override
    public Task findTask() throws CommandCanceledException {
        List<Task> tasks = taskService.findAll();
        String[] tempCmdList = new String[tasks.size()];
        Map<String, Task> taskCmdMapping = new HashMap<>();
        initializeDataCollections(tasks, tempCmdList, taskCmdMapping);
        input = viewWriter.blockingReadCommand("Select a task: [0]..[n]", tempCmdList);
        return taskCmdMapping.get(input);
    }

    @Override
    public Requirement findRequirement() throws CommandCanceledException {
        List<Requirement> requirements = requirementService.findAll();
        String[] tempCmdList = new String[requirements.size()];
        Map<String, Requirement> requirementCmdMapping = new HashMap<>();
        initializeDataCollections(requirements, tempCmdList, requirementCmdMapping);
        input = viewWriter.blockingReadCommand("Select a requirement: [0]..[n]", tempCmdList);
        return requirementCmdMapping.get(input);
    }

    @Override
    public LogbookEntry findLogbookEntry() throws CommandCanceledException {
        List<LogbookEntry> logbookEntries = logbookEntryService.findAll();
        String[] tempCmdList = new String[logbookEntries.size()];
        Map<String, LogbookEntry> logbookCmdMapping = new HashMap<>();
        initializeDataCollections(logbookEntries, tempCmdList, logbookCmdMapping);
        input = viewWriter.blockingReadCommand("Select a logbook entry: [0]..[n]", tempCmdList);
        return logbookCmdMapping.get(input);
    }

    public  <T> void initializeDataCollections(List<T> entities, String[] tempCmdList, Map<String, T> entityCmdMapping) {
        for (int i = 0; i < entities.size(); i++) {
            tempCmdList[i] = String.valueOf(i);
            entityCmdMapping.put(tempCmdList[i], entities.get(i));
            viewWriter.setIndent(2);
            viewWriter.print(i);
            viewWriter.resetIndent();
            if (i < 10) viewWriter.print(" ");
            if (i < 100) viewWriter.print(" ");
            viewWriter.print(" | ");
            viewWriter.print(entities.get(i));
            viewWriter.newLine();
        }
        viewWriter.resetIndent();
    }

}
