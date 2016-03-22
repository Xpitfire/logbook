package swt6.ue3.logbook.controller.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import swt6.ue3.logbook.controller.*;
import swt6.ue3.logbook.domain.Employee;
import swt6.ue3.logbook.domain.Project;
import swt6.ue3.logbook.domain.Requirement;
import swt6.ue3.logbook.domain.Task;
import swt6.ue3.logbook.logic.*;
import swt6.ue3.logbook.view.ViewWriter;

import java.util.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 21.03.2016
 */
@Controller("interactiveController")
public class InteractiveControllerImpl implements InteractiveController {

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

    @Autowired
    private LinkController linkController;
    @Autowired
    private CreateController createController;
    @Autowired
    private FindController findController;
    @Autowired
    private PrintController printController;
    @Autowired
    private DeleteController deleteController;
    @Autowired
    private UpdateController updateController;

    @Override
    public void printAllEmployees() {
        printController.printEntityEmployees();
    }

    @Override
    public void createProject() {
        createController.createProject(true);
    }

    @Override
    public void linkEmployeeToProject() {
        linkController.linkProjectToEmployee();
    }

    @Override
    public void unlinkEmployeeFromProject() {
        Project project = findController.findProject();
        List<Employee> members = new ArrayList<>(project.getMembers());
        String[] tempCmdList = new String[members.size()];
        Map<String, Employee> employeeCmdMapping = new HashMap<>();
        findController.initializeDataCollections(members, tempCmdList, employeeCmdMapping);
        input = viewWriter.blockingReadCommand("Select an employee: ", tempCmdList);
        project.removeMember(employeeCmdMapping.get(input));
        projectService.save(project);
    }

    @Override
    public void printProjectMembers() {
        Project project = findController.findProject();
        viewWriter.println("*** PRINT PROJECT MEMBERS ***");
        viewWriter.setIndent(2);
        project.getMembers().forEach(viewWriter::println);
        viewWriter.resetIndent();
    }

    @Override
    public void printRequirementsGroupedBySprints() {
        Project project = findController.findProject();
        viewWriter.println("*** PRINT REQUIREMENTS GROUPED BY SPRINTS ***");
        project.getSprints().forEach(sprint -> {
            viewWriter.setIndent(2);
            List<Requirement> requirements = new ArrayList<>(sprint.getRequirements());
            requirements.forEach(viewWriter::println);
            viewWriter.resetIndent();
        });
    }

    @Override
    public void printRequirementTasks() {
        Requirement requirement = findController.findRequirement();
        viewWriter.println("*** PRINT REQUIREMENTS TASKS ***");
        viewWriter.setIndent(2);
        requirement.getTasks().forEach(viewWriter::println);
        viewWriter.resetIndent();
    }

    @Override
    public void linkRequirementToProject() {
        linkController.linkRequirementToTask();
    }

    @Override
    public void linkRequirementToSprint() {
        linkController.linkSprintToRequirement();
    }

    @Override
    public void createRequirementTask() {
        Task task = createController.createTask(true);
        linkController.linkRequirementTo(task, true);
    }

    @Override
    public void modifyTask() {
        updateController.updateTask();
    }

    @Override
    public void deleteTask() {
        deleteController.deleteTask(null);
    }

    @Override
    public void printBurnDownChartPerSprint() {
        printController.printStatsBurnDownChartPerProjectSprint();
    }
}
