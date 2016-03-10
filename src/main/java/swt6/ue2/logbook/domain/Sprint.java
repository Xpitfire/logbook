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
public class Sprint implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Project project;

    @OneToMany(mappedBy = "sprint",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Requirement> requirements = new HashSet<>();

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    public void attachProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        if (this.project != null) {
            this.project.getSprints().remove(this);
        }

        this.project = project;
        this.project.getSprints().add(this);
    }

    public void detachProject() {
        if (this.project != null) {
            this.project.getSprints().remove(this);
        }
        this.project = null;
    }

    public void addRequirement(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement must not be null");
        }

        if (requirement.getSprint() != null) {
            requirement.getSprint().getRequirements().remove(requirement);
        }

        this.requirements.add(requirement);
        requirement.setSprint(this);
    }

    public void removeRequirement(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement must not be null");
        }

        if (requirement.getSprint() != null && requirement.getSprint() != this) {
            throw new IllegalArgumentException("Requirement is associated with another sprint");
        }

        this.requirements.remove(requirement);
        requirement.setSprint(null);
    }

}
