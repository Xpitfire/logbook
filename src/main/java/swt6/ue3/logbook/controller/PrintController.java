package swt6.ue3.logbook.controller;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface PrintController {

    PrintController printStatsProjectTotalCosts();
    PrintController printStatsBurnDownChartPerProjectSprint();
    PrintController printEntityAll();
    PrintController printEntitySprints();
    PrintController printEntityRequirements();
    PrintController printEntityTasks();
    PrintController printEntityProjects();
    PrintController printEntityLogbookEntries();
    PrintController printEntityEmployees();
    PrintController printEntityProject();
    PrintController printEntitySprint();
    PrintController printEntityRequirement();
    PrintController printEntityTask();
    PrintController printEntityEmployee();
    PrintController printEntityLogbookEntry();

}
