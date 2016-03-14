package swt6.ue2.logbook.dal;

import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public interface Dao<T> {

    T firstOrDefault();
    T findById(Object id);
    List<T> findAll();
    Long count();
    T safe(T entity);
    void remove(T entity);

}
