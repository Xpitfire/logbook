package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.LogbookEntry;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class LogbookEntryDao extends AbstractDao<LogbookEntry> {
    LogbookEntryDao(Class<LogbookEntry> clazz) {
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
