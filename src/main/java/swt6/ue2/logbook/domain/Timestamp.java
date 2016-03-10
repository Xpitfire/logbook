package swt6.ue2.logbook.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Entity
public class Timestamp implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private Date startTime;
    private Integer duration;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Task task;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date timestamp) {
        this.startTime = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void attachTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }

        if (this.task != null) {
            this.task.getTimestamps().remove(this);
        }

        this.task = task;
        this.task.getTimestamps().add(this);
    }

    public void detachRequirement() {
        if (this.task != null) {
            this.task.getTimestamps().remove(this);
        }
        this.task = null;
    }

}
