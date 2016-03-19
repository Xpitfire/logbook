package swt6.ue3.logbook.controller;

import swt6.ue3.logbook.domain.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface LinkController {

    LogbookEntry selectLogbookEntry();
    Task selectTask();
    Sprint selectSprint();
    Employee selectEmployee();
    Requirement selectRequirement();
    Project selectProject();

    Sprint linkSprintTo(Object object, boolean mandatory);
    Project linkProjectTo(Object object, boolean mandatory);
    Employee linkEmployeeTo(Object object, boolean mandatory);
    Requirement linkRequirementTo(Object object, boolean mandatory);
    Task linkTaskTo(Object object, boolean mandatory);
    Address linkAddressTo(Employee employee, boolean mandatory);
    LogbookEntry linkLogbookEntryTo(Object object, boolean mandatory);

    void linkLogbookEntryToTask();
    void linkLogbookEntryToEmployee();
    void linkSprintToProject();
    void linkSprintToRequirement();
    void linkProjectToRequirement();
    void linkProjectToEmployee();
    void linkRequirementToTask();

}
