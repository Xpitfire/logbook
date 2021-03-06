package swt6.ue3.logbook.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "LOGBOOK_ENTRY")
public class LogbookEntry implements Serializable {

    private static final DateFormat fmt = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    @Id
    @GeneratedValue
    private Long id;
    private String activity;
    private Date startTime;
    private Date endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = true)
    private Task task;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, Date startTime, Date endTime) {
        super();
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
            this.task.getLogbookEntries().remove(this);
        }

        this.task = task;
        this.task.getLogbookEntries().add(this);
    }

    public void detachRequirement() {
        if (this.task != null) {
            this.task.getLogbookEntries().remove(this);
        }
        this.task = null;
    }

    public void attachEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }

        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }

        this.employee = employee;
        this.employee.getLogbookEntries().add(this);
    }

    public void detachEmployee() {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }
        this.employee = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LOG-ENTRY: ").append(activity).append(": ");
        if (startTime != null) {
            sb.append(fmt.format(startTime)).append(" - ");
        }
        if (endTime != null) {
            sb.append(fmt.format(endTime));
        }
        if (employee != null) {
            sb.append(" (created by: ").append(employee.getLastName()).append(")");
        }
        if (task != null) {
            sb.append(" | ").append(task.getId());
        }
        return sb.toString();
    }

}
