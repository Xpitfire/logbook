package swt6.ue3.logbook.logic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public interface AppService<T extends Serializable, ID extends Serializable> {

    JpaRepository<T, ID> getRepository();

    @Transactional(propagation = Propagation.REQUIRED)
    default List<T> findAll() {
        return getRepository().findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    default void remove(T entity) {
        getRepository().delete(entity);
    }

    default Long count() {
        return getRepository().count();
    }

}
