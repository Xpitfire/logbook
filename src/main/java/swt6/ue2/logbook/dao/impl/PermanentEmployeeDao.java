package swt6.ue2.logbook.dao.impl;

import swt6.ue2.logbook.dao.AbstractJpaDao;
import swt6.ue2.logbook.domain.PermanentEmployee;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class PermanentEmployeeDao extends AbstractJpaDao<PermanentEmployee> {
    protected PermanentEmployeeDao(Class<PermanentEmployee> clazz) {
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
