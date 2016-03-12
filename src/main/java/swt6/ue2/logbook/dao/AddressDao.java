package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Address;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class AddressDao extends AbstractDao<Address> {
    AddressDao(Class<Address> clazz) {
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
