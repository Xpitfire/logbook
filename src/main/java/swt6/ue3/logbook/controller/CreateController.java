package swt6.ue3.logbook.controller;

import swt6.ue3.logbook.domain.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface CreateController {

    Project createProject(boolean immediateSafe);
    Project createOrUpdateProject(Project value, boolean immediateSafe);

    Employee createEmployee(boolean immediateSafe);
    Employee createOrUpdateEmployee(Employee value, boolean immediateSafe);

    Address createAddress();
    Address createOrUpdateAddress(Address value);

    Task createTask(boolean immediateSafe);
    Task createOrUpdateTask(Task value, boolean immediateSafe);

    Requirement createRequirement(boolean immediateSafe);
    Requirement createOrUpdateRequirement(Requirement value, boolean immediateSafe);

    Sprint createSprint(boolean immediateSafe);

    LogbookEntry createLogbookEntry(boolean immediateSafe);

}
