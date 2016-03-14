package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Requirement;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class RequirementDao extends AbstractDao<Requirement> {
    RequirementDao(Class<Requirement> clazz) {
        super(clazz);
    }

    RequirementDao(Class<Requirement> clazz, boolean explicitTransactionControl) {
        super(clazz, explicitTransactionControl);
    }
}
