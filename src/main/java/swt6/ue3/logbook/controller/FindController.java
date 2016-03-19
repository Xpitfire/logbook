package swt6.ue3.logbook.controller;

import swt6.ue3.logbook.domain.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface FindController {
    Sprint findSprint();
    Project findProject();
    Employee findEmployee();
    Task findTask();
    Requirement findRequirement();
    LogbookEntry findLogbookEntry();

}
