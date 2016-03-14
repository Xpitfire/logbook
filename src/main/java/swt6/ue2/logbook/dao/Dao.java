package swt6.ue2.logbook.dao;

import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public interface Dao<T> extends AutoCloseable {

    T firstOrDefault();
    T findById(Object id);
    List<T> findAll();
    Long count();
    T safe(T entity);
    void remove(T entity);
    void commit();
    void rollback();

}
