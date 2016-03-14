package swt6.ue2.logbook.dal;

import swt6.ue2.logbook.domain.*;
import swt6.ue2.logbook.jpa.util.JpaUtil;

import java.io.Serializable;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public final class DaoFactory {

    private DaoFactory() {}

    public static <T extends Serializable, K extends Dao<T>> K getDao(Class<T> clazz) {
        AbstractDao<T> dao = null;
        if (clazz.equals(Employee.class)) {
            dao = (AbstractDao<T>)new EmployeeDao();
        } else if (clazz.equals(LogbookEntry.class)) {
            dao = (AbstractDao<T>)new LogbookEntryDao();
        } else if (clazz.equals(Project.class)) {
            dao = (AbstractDao<T>)new ProjectDao();
        } else if (clazz.equals(Requirement.class)) {
            dao = (AbstractDao<T>)new RequirementDao();
        } else if (clazz.equals(Sprint.class)) {
            dao = (AbstractDao<T>)new SprintDao();
        } else if (clazz.equals(Task.class)) {
            dao = (AbstractDao<T>)new TaskDao();
        }
        if (dao != null)
            dao.setClass(clazz);
        return (K)dao;
    }

    public static void commit() {
        JpaUtil.commit();
    }

    public static void rollback() {
        JpaUtil.rollback();
    }

}
