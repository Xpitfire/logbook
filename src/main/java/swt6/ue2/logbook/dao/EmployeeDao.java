package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Employee;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
class EmployeeDao extends AbstractDao<Employee> {
    EmployeeDao(Class<Employee> clazz) {
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
