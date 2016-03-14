package swt6.ue2.logbook.logic;

import swt6.ue2.logbook.dal.Dao;
import swt6.ue2.logbook.dal.DaoFactory;

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
        T entity = dao.firstOrDefault();
        DaoFactory.commit();
        return entity;
    }

    @Override
    public T findById(Object id) {
        T entity =dao.findById(id);
        DaoFactory.commit();
        return entity;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = dao.findAll();
        DaoFactory.commit();
        return entities;
    }

    @Override
    public T safe(T entity) {
        entity = dao.safe(entity);
        DaoFactory.commit();
        return entity;
    }

    @Override
    public void remove(T entity) {
        dao.remove(entity);
        DaoFactory.commit();
    }

    @Override
    public Long count() {
        Long value = dao.count();
        DaoFactory.commit();
        return value;
    }

}
