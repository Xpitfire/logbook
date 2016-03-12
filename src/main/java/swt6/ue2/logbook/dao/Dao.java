package swt6.ue2.logbook.dao;

import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public interface Dao<T> {

    T findById(Object id);
    List<T> findAll();
    T merge(T entity);
    void remove(T entity);

}
