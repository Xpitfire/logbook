package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Sprint;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class SprintDao extends AbstractDao<Sprint> {
    SprintDao(Class<Sprint> clazz) {
        super(clazz);
    }

    SprintDao(Class<Sprint> clazz, boolean explicitTransactionControl) {
        super(clazz, explicitTransactionControl);
    }
}
