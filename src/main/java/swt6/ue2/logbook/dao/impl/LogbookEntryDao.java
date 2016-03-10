package swt6.ue2.logbook.dao.impl;

import swt6.ue2.logbook.dao.AbstractJpaDao;
import swt6.ue2.logbook.domain.LogbookEntry;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class LogbookEntryDao extends AbstractJpaDao<LogbookEntry> {
    protected LogbookEntryDao(Class<LogbookEntry> clazz) {
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
