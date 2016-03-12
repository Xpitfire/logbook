package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Task;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class TaskDao extends AbstractDao<Task> {
    TaskDao(Class<Task> clazz) {
        super(clazz);
    }

    @Override
    protected String eagerSelectById() {
        return null;
    }

    @Override
    protected String eagerSelectAll() {
        return null;
    }
}
