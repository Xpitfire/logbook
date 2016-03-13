package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name = "PROJECT_EMPLOYEE",
            joinColumns = {@JoinColumn(name = "projectId")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId")})
    private Set<Employee> members = new HashSet<>();

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Sprint> sprints = new HashSet<>();

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Requirement> requirements = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = true)
    private Employee leader;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getMembers() {
        return members;
    }

    public void setMembers(Set<Employee> members) {
        this.members = members;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    public void addMember(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }

        this.members.add(employee);
        employee.getProjects().add(this);
    }

    public void removeMember(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }

        this.members.remove(employee);
        employee.getProjects().remove(this);
    }

    public void addSprint(Sprint sprint) {
        if (sprint == null) {
            throw new IllegalArgumentException("Sprint must not be null");
        }

        if (sprint.getProject() != null) {
            sprint.getProject().getSprints().remove(sprint);
        }

        this.sprints.add(sprint);
        sprint.setProject(this);
    }

    public void removeSprint(Sprint sprint) {
        if (sprint == null) {
            throw new IllegalArgumentException("Sprint must not be null");
        }

        if (sprint.getProject() != null && sprint.getProject() != this) {
            throw new IllegalArgumentException("Sprint is associated with another project");
        }

        this.sprints.remove(sprint);
        sprint.setProject(null);
    }

    public void addRequirement(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement must not be null");
        }

        if (requirement.getProject() != null) {
            requirement.getProject().getRequirements().remove(requirement);
        }

        this.requirements.add(requirement);
        requirement.setProject(this);
    }

    public void removeRequirement(Requirement requirement) {
        if (requirement == null) {
            throw new IllegalArgumentException("Requirement must not be null");
        }

        if (requirement.getProject() != null && requirement.getProject() != this) {
            throw new IllegalArgumentException("Requirement is associated with another project");
        }

        this.requirements.remove(requirement);
        requirement.setProject(null);
    }

    public void attachLeader(Employee leader) {
        if (leader == null) {
            throw new IllegalArgumentException("Employee leader must not be null");
        }

        if (this.leader != null) {
            this.leader.getSupervisedProjects().remove(this);
        }

        this.leader = leader;
        this.leader.getSupervisedProjects().add(this);
    }

    public void detachLeader() {
        if (this.leader != null) {
            this.leader.getSupervisedProjects().remove(this);
        }
        this.leader = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PROJECT: ").append(name);
        sb.append(" (Leader: ").append(leader).append(")");
        if (members != null && members.size() > 0) {
            sb.append(" | MEMBERS: (");
            for (Employee e : members) {
                sb.append(e.getLastName()).append(" ");
            }
            sb.append(")");
        }
        if (sprints != null && sprints.size() > 0) {
            sb.append(" | SPRINTS: (");
            for (Sprint s : sprints) {
                sb.append(s.getId()).append(" ");
            }
            sb.append(")");
        }
        if (requirements != null && requirements.size() > 0) {
            sb.append(" | REQUIREMENTS: (");
            for (Requirement r : requirements) {
                sb.append(r.getId()).append(" ");
            }
            sb.append(")");
        }
        return sb.toString();
    }

}
