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
public class Task implements Serializable {

    @Id
    private String id;
    private String description;
    private Integer estimatedHours;

    @OneToMany(mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Timestamp> timestamps = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Requirement requirement;

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

    public Set<Timestamp> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Set<Timestamp> timestamps) {
        this.timestamps = timestamps;
    }

    public void addTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }

        if (timestamp.getTask() != null) {
            timestamp.getTask().getTimestamps().remove(timestamp);
        }

        this.timestamps.add(timestamp);
        timestamp.setTask(this);
    }

    public void removeTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }

        if (timestamp.getTask() != null && timestamp.getTask() != this) {
            throw new IllegalArgumentException("Timestamp is associated with another task");
        }

        this.timestamps.remove(timestamp);
        timestamp.setTask(null);
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

}
