package swt6.ue3.logbook.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.ue3.logbook.dal.TaskRepository;
import swt6.ue3.logbook.domain.Task;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public JpaRepository<Task, String> getRepository() {
        return taskRepository;
    }
}
