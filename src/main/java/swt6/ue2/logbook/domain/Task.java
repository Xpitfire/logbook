package swt6.ue2.logbook.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Entity
@Table(name = "TASK")
public class Task implements Serializable {

    @Id
    private String id;
    private String description;
    private Integer estimatedHours;

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = true)
    private Requirement requirement;

    public Task() {
    }

    public Task(String id, String description, Integer estimatedHours) {
        this.id = id;
        this.description = description;
        this.estimatedHours = estimatedHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogbookEntries(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (logbookEntry.getTask() != null) {
            logbookEntry.getTask().getLogbookEntries().remove(logbookEntry);
        }

        this.logbookEntries.add(logbookEntry);
        logbookEntry.setTask(this);
    }

    public void removeLogbookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (logbookEntry.getTask() != null && logbookEntry.getTask() != this) {
            throw new IllegalArgumentException("LogbookEntry is associated with another task");
        }

        this.logbookEntries.remove(logbookEntry);
        logbookEntry.setTask(null);
    }

    public void attachRequirement(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement must not be null");
        }

        if (this.requirement != null) {
            this.requirement.getTasks().remove(this);
        }

        this.requirement = requirement;
        this.requirement.getTasks().add(this);
    }

    public void detachRequirement() {
        if (this.requirement != null) {
            this.requirement.getTasks().remove(this);
        }
        this.requirement = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TASK: ").append(" Description:").append(description)
                .append(" Estimated Hours: ").append(estimatedHours);
        if (requirement != null) {
            sb.append(" | REQUIREMENT: ").append(requirement.getId());
        }
        if (logbookEntries != null && logbookEntries.size() > 0) {
            sb.append(" | LOG-ENTRIES: (");
            for (LogbookEntry l : logbookEntries) {
                sb.append(l.getActivity()).append(" ");
            }
            sb.append(")");
        }
        return sb.toString();
    }
}
