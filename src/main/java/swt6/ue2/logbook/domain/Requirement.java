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
@Table(name = "REQUIREMENT")
public class Requirement implements Serializable {

    @Id
    private String id;
    private String description;

    @OneToMany(mappedBy = "requirement",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Project project;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Sprint sprint;

    public Requirement() {
    }

    public Requirement(String id, String description) {
        this.id = id;
        this.description = description;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }

        if (task.getRequirement() != null) {
            task.getRequirement().getTasks().remove(task);
        }

        this.tasks.add(task);
        task.setRequirement(this);
    }

    public void removeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }

        if (task.getRequirement() != null && task.getRequirement() != this) {
            throw new IllegalArgumentException("Task is associated with another requirement");
        }

        this.tasks.remove(task);
        task.setRequirement(null);
    }

    public void attachProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        if (this.project != null) {
            this.project.getRequirements().remove(this);
        }

        this.project = project;
        this.project.getRequirements().add(this);
    }

    public void detachProject() {
        if (this.project != null) {
            this.project.getRequirements().remove(this);
        }
        this.project = null;
    }

    public void attachSprint(Sprint sprint) {
        if (sprint == null) {
            throw new IllegalArgumentException("Sprint must not be null");
        }

        if (this.sprint != null) {
            this.sprint.getRequirements().remove(this);
        }

        this.sprint = sprint;
        this.sprint.getRequirements().add(this);
    }

    public void detachSprint() {
        if (this.sprint != null) {
            this.sprint.getRequirements().remove(this);
        }
        this.sprint = null;
    }
}
