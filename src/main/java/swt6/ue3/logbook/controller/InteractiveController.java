package swt6.ue3.logbook.controller;

/**
 * @author: Dinu Marius-Constantin
 * @date: 21.03.2016
 */
public interface InteractiveController {

    void printAllEmployees();
    void createProject();
    void linkEmployeeToProject();
    void unlinkEmployeeFromProject();
    void printProjectMembers();
    void printRequirementsGroupedBySprints();
    void printRequirementTasks();
    void linkRequirementToProject();
    void linkRequirementToSprint();
    void createRequirementTask();
    void modifyTask();
    void deleteTask();
    void printBurnDownChartPerSprint();

}
