package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Requirement;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class RequirementDao extends AbstractDao<Requirement> {
    RequirementDao(Class<Requirement> clazz) {
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
