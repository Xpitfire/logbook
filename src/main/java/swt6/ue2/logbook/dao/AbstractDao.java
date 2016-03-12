package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class AbstractDao<T extends Serializable> implements Dao<T> {

    protected final Class<T> clazz;

    protected AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
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
        JpaUtil.commit();
        return entity;
    }

    @Override
    public T findById(Object id) {
        EntityManager entityManager  = JpaUtil.getTransactedEntityManager();
        T entity = entityManager.find(clazz, id);
        JpaUtil.commit();
        return entity;
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT t FROM %s t", clazz.getSimpleName()));
        List<T> entities = query.getResultList();
        JpaUtil.commit();
        return entities;
    }

    @Override
    public T safe(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entity = entityManager.merge(entity);
        JpaUtil.commit();
        return entity;
    }

    @Override
    public void remove(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entity = entityManager.merge(entity);
        entityManager.remove(entity);
        JpaUtil.commit();
    }

    @Override
    public Long count() {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT COUNT(t) FROM %s t", clazz.getSimpleName()));
        Long count = (Long)query.getSingleResult();
        JpaUtil.commit();
        return count;
    }
}
