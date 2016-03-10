package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LogbookEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String activity;
    private Date startTime;
    private Date endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = true)
    private Module module;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = true)
    private Phase phase;

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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
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

    public void attachModule(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module must not be null");
        }

        if (this.module != null) {
            this.module.getLogbookEntries().remove(this);
        }

        this.module = module;
        this.module.getLogbookEntries().add(this);
    }

    public void detachModule() {
        if (this.module != null) {
            this.module.getLogbookEntries().remove(this);
        }
        this.module = null;
    }

    public void attachPhase(Phase phase) {
        if (phase == null) {
            throw new IllegalArgumentException("Phase must not be null");
        }

        if (this.phase != null) {
            this.phase.getLogbookEntries().remove(this);
        }

        this.phase = phase;
        this.phase.getLogbookEntries().add(this);
    }

    public void detachPhase() {
        if (this.phase != null) {
            this.phase.getLogbookEntries().remove(this);
        }
        this.phase = null;
    }

    @Override
    public String toString() {
        DateFormat fmt = DateFormat.getDateTimeInstance();
        return activity + ": " + fmt.format(startTime) + " - " + fmt.format(endTime) + "(" + employee.getLastName() + ")";
    }

}
