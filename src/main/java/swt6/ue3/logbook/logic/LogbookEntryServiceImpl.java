package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.LogbookEntryRepository;
import swt6.ue3.logbook.domain.LogbookEntry;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service("logbookEntryService")
@Transactional
public class LogbookEntryServiceImpl implements LogbookEntryService {
    @Autowired
    private LogbookEntryRepository logbookEntryRepository;

    @Override
    public JpaRepository<LogbookEntry, Long> getRepository() {
        return logbookEntryRepository;
    }
}
