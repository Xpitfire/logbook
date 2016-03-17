package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.SprintRepository;
import swt6.ue3.logbook.domain.Sprint;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service("sprintService")
@Transactional
public class SprintServiceImpl implements SprintService {
    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public JpaRepository<Sprint, Long> getRepository() {
        return sprintRepository;
    }
}
