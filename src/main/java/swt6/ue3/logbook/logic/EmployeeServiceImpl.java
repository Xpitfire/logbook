package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.EmployeeRepository;
import swt6.ue3.logbook.domain.Employee;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public JpaRepository<Employee, Long> getRepository() {
        return employeeRepo;
    }
}
