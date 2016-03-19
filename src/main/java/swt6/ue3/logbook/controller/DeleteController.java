package swt6.ue3.logbook.controller;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
public interface DeleteController {

    void deleteTask(ConfirmationConsumer confirm);
    void deleteSprint(ConfirmationConsumer confirm);
    void deleteRequirement(ConfirmationConsumer confirm);
    void deleteProject(ConfirmationConsumer confirm);
    void deleteLogbookEntry(ConfirmationConsumer confirm);
    void deleteEmployee(ConfirmationConsumer confirm);

}
