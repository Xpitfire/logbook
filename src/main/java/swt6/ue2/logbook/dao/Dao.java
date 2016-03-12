package swt6.ue2.logbook.dao;

import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public interface Dao<T> {

    T selectById(Object id);
    List<T> selectAll();
    void insert(T entity);
    void update(T entity);
    void delete(T entity);

}
