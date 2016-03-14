package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Task;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class TaskDao extends AbstractDao<Task> {
    TaskDao(Class<Task> clazz) {
        super(clazz);
    }

    TaskDao(Class<Task> clazz, boolean explicitTransactionControl) {
        super(clazz, explicitTransactionControl);
    }
}
