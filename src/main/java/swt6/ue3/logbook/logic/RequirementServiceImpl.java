package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.RequirementRepository;
import swt6.ue3.logbook.domain.Requirement;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service("requirementService")
@Transactional
public class RequirementServiceImpl implements RequirementService {
    @Autowired
    private RequirementRepository requirementRepository;

    @Override
    public JpaRepository<Requirement, String> getRepository() {
        return requirementRepository;
    }
}
