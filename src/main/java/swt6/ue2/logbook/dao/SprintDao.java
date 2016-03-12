package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Sprint;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class SprintDao extends AbstractDao<Sprint> {
    SprintDao(Class<Sprint> clazz) {
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
