package swt6.ue2.logbook.dao;

import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.jpa.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class EmployeeDao extends AbstractDao<Employee> {

    EmployeeDao(Class<Employee> clazz) {
        super(clazz);
    }

    public <K extends Employee> List<K> findAll(Class<K> type) {
        EntityManager entityManager = JpaUtil.getTransactedEntityManager();
        Query query = entityManager.createQuery(String.format("SELECT t FROM %s t", clazz.getSimpleName()));

        Stream<K> filterStream = query.getResultList().stream().filter(o -> o.getClass().equals(type));
        List<K> entities = filterStream.collect(Collectors.toList());

        JpaUtil.commit();
        return entities;
    }

}
