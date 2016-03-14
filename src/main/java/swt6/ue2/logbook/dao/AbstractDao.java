package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class AbstractDao<T extends Serializable> implements Dao<T> {

    protected final Class<T> clazz;
    private boolean implicitTransactionClose;

    protected AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected AbstractDao(Class<T> clazz, boolean explicitTransactionControl) {
        this.clazz = clazz;
        this.implicitTransactionClose = !explicitTransactionControl;
    }

    @Override
    public T firstOrDefault() {
        EntityManager entityManager  = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT t FROM %s t", clazz.getSimpleName()));
        T entity = null;
        try {
            entity = (T) query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
            // do nothing
        }
        if (implicitTransactionClose)
            JpaUtil.commit();
        return entity;
    }

    @Override
    public T findById(Object id) {
        EntityManager entityManager  = JpaUtil.getTransactedEntityManager();
        T entity = entityManager.find(clazz, id);
        if (implicitTransactionClose)
            JpaUtil.commit();
        return entity;
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT t FROM %s t", clazz.getSimpleName()));
        List<T> entities = query.getResultList();
        if (implicitTransactionClose)
            JpaUtil.commit();
        return entities;
    }

    @Override
    public T safe(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entity = entityManager.merge(entity);
        if (implicitTransactionClose)
            JpaUtil.commit();
        return entity;
    }

    @Override
    public void remove(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
        if (implicitTransactionClose)
            JpaUtil.commit();
    }

    @Override
    public Long count() {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT COUNT(t) FROM %s t", clazz.getSimpleName()));
        Long count = (Long)query.getSingleResult();
        if (implicitTransactionClose)
            JpaUtil.commit();
        return count;
    }

    @Override
    public void commit() {
        JpaUtil.commit();
    }

    @Override
    public void rollback() {
        JpaUtil.rollback();
    }

    @Override
    public void close() throws Exception {
        commit();
    }
}
