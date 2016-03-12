package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.LogbookEntry;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class LogbookEntryDao extends AbstractDao<LogbookEntry> {
    LogbookEntryDao(Class<LogbookEntry> clazz) {
        super(clazz);
    }
}
