package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.*;

import java.io.Serializable;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public final class DaoFactory {

    private DaoFactory() {}

    public static <T extends Serializable, K extends Dao<T>> K getDao(Class<T> clazz) {
        if (clazz.equals(Employee.class)) {
            return (K)new EmployeeDao((Class<Employee>)clazz);
        } else if (clazz.equals(LogbookEntry.class)) {
            return (K)new LogbookEntryDao((Class<LogbookEntry>)clazz);
        } else if (clazz.equals(Project.class)) {
            return (K)new ProjectDao((Class<Project>)clazz);
        } else if (clazz.equals(Requirement.class)) {
            return (K)new RequirementDao((Class<Requirement>)clazz);
        } else if (clazz.equals(Sprint.class)) {
            return (K)new SprintDao((Class<Sprint>)clazz);
        } else if (clazz.equals(Task.class)) {
            return (K)new TaskDao((Class<Task>)clazz);
        }
        return null;
    }

}
