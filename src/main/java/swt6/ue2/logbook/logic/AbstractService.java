package swt6.ue2.logbook.logic;

import swt6.ue2.logbook.dal.Dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public abstract class AbstractService<T extends Serializable> implements Service<T> {

    private Dao<T> dao;

    public void setDao(Dao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T firstOrDefault() {
        return dao.firstOrDefault();
    }

    @Override
    public T findById(Object id) {
        return dao.findById(id);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public T safe(T entity) {
        return dao.safe(entity);
    }

    @Override
    public void remove(T entity) {
        dao.remove(entity);
    }

    @Override
    public Long count() {
        return dao.count();
    }

}
