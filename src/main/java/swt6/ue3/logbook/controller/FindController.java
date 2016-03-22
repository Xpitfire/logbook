package swt6.ue3.logbook.controller;

import swt6.ue3.logbook.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface FindController {
    Sprint findSprint();
    Sprint findSprint(Project project);
    Project findProject();
    Employee findEmployee();
    Task findTask();
    Requirement findRequirement();
    LogbookEntry findLogbookEntry();
    <T> void initializeDataCollections(List<T> entities, String[] tempCmdList, Map<String, T> entityCmdMapping);

}
