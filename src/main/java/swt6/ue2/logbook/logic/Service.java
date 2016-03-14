package swt6.ue2.logbook.logic;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public interface Service<T extends Serializable> {

    T firstOrDefault();
    T findById(Object id);
    List<T> findAll();
    T safe(T entity);
    void remove(T entity);
    Long count();

}
