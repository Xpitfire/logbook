package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Address;
import swt6.ue2.logbook.domain.Employee;

import java.io.Serializable;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public final class DaoFactory {

    private DaoFactory() {}

    public static <T extends Serializable, K extends Dao<T>> K getDao(Class<T> clazz) {
        if (clazz.getSimpleName().equals("Employee")) {
            return (K)new EmployeeDao((Class<Employee>) clazz);
        } else if (clazz.getSimpleName().equals("AddressDao")) {
            return (K)new AddressDao((Class<Address>) clazz);
        }
        return null;
    }

}
