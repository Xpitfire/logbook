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
        return entity;
    }

    @Override
    public T findById(Object id) {
        T entity =dao.findById(id);
        return entity;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = dao.findAll();
        return entities;
    }

    @Override
    public T safe(T entity) {
        try {
            entity = dao.safe(entity);
            DaoFactory.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            DaoFactory.rollback();
            throw new RuntimeException(ex);
        }
        return entity;
    }

    @Override
    public void remove(T entity) {
        try {
            dao.remove(entity);
            DaoFactory.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            DaoFactory.rollback();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Long count() {
        Long value = dao.count();
        DaoFactory.commit();
        return value;
    }

}
