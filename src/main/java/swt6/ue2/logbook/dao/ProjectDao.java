package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Project;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class ProjectDao extends AbstractDao<Project> {
    ProjectDao(Class<Project> clazz) {
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
