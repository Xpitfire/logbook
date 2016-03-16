package swt6.ue3.logbook.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.ue3.logbook.domain.Sprint;

/**
 * @author: Dinu Marius-Constantin
 * @date: 16.03.2016
 */
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
