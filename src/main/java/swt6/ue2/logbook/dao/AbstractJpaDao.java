package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class AbstractJpaDao<T extends Serializable> implements CommonDao<T> {

    private Class<T> clazz;

    protected AbstractJpaDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T selectById(Object id) {
        EntityManager entityManager  = JpaUtil.getTransactedEntityManager();
        T entity = entityManager.find(clazz, id);
        JpaUtil.commit();
        return entity;
    }

    public T selectByid(Object id, FetchType type) {
        if (type == null) throw new IllegalArgumentException("FetchType can not be null");

        if (type == FetchType.LAZY) {
            return selectById(id);
        } else {
            EntityManager entityManager = JpaUtil.getTransactedEntityManager();
            Query query = entityManager.createQuery(eagerSelectById());
            T entity = (T) query.getSingleResult();
            JpaUtil.commit();
            return entity;
        }
    }

    @Override
    public List<T> selectAll() {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT t FROM %s t", clazz.getSimpleName()));
        List<T> entities = query.getResultList();
        JpaUtil.commit();
        return entities;
    }

    public List<T> selectAll(FetchType type) {
        if (type == null) throw new IllegalArgumentException("FetchType can not be null");

        if (type == FetchType.LAZY) {
            return selectAll();
        } else {
            EntityManager entityManager = JpaUtil.getTransactedEntityManager();
            Query query = entityManager.createQuery(eagerSelectById());
            List<T> entities = query.getResultList();
            JpaUtil.commit();
            return entities;
        }
    }

    @Override
    public void insert(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entityManager.persist(entity);
        JpaUtil.commit();
    }

    @Override
    public void update(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entityManager.merge(entity);
        JpaUtil.commit();
    }

    @Override
    public void delete(T entity) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        entityManager.remove(entity);
        JpaUtil.commit();
    }

    protected abstract String eagerSelectById();
    protected abstract String eagerSelectAll();

}
