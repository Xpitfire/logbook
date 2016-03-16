package swt6.ue3.logbook.logic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public interface AppService<T extends Serializable, ID extends Serializable> {

    JpaRepository<T, ID> getRepository();

    default T findOne(ID id) {
        return getRepository().findOne(id);
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default T save(T entity) {
        return getRepository().save(entity);
    }

    default void remove(T entity) {
        getRepository().delete(entity);
    }

    default Long count() {
        return getRepository().count();
    }

}
