package swt6.ue2.logbook.logic;

import swt6.ue2.logbook.dal.DaoFactory;
import swt6.ue2.logbook.domain.*;

import java.io.Serializable;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public final class ServiceFactory {

    private ServiceFactory() {
    }

    public static <T extends Serializable, K extends Service<T>> K getService(Class<T> clazz) {
        AbstractService<T> service = null;
        if (clazz.equals(Employee.class)) {
            service = (AbstractService<T>)new EmployeeService();
        } else if (clazz.equals(LogbookEntry.class)) {
            service = (AbstractService<T>)new LogbookEntryService();
        } else if (clazz.equals(Project.class)) {
            service = (AbstractService<T>)new ProjectService();
        } else if (clazz.equals(Requirement.class)) {
            service = (AbstractService<T>)new RequirementService();
        } else if (clazz.equals(Sprint.class)) {
            service = (AbstractService<T>)new SprintService();
        } else if (clazz.equals(Task.class)) {
            service = (AbstractService<T>)new TaskService();
        }
        if (service != null)
            service.setDao(DaoFactory.getDao(clazz));
        return (K)service;
    }

}
