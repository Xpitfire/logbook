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

    private Long id;
    private String activity;
    private Date startTime;
    private Date endTime;

    private Employee employee;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, Date startTime, Date endTime) {
        super();
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void attachEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }

        // If entry is already linked to some employee, remove this link
        if (this.getEmployee() != null) {
            this.getEmployee().getLogbookEntries().remove(this);
        }

        // Add a bidirectional link between this entry and employee
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
        DateFormat fmt = DateFormat.getDateTimeInstance();
        return activity + ": " + fmt.format(startTime) + " - " + fmt.format(endTime) + "(" + employee.getLastName() + ")";
    }
}
