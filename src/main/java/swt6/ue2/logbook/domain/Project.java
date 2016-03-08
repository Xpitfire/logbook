package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ProjectEmployee",
            joinColumns = {@JoinColumn(name = "projectId")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId")})
    private Set<Employee> members = new HashSet<>();

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Module> modules = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    private Employee leader;

    public Long getId() {
        return id;
    }

    public Project() {
    }

    public Project(String name) {
        this.name = name;
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

    public void addMember(Employee empl) {
        if (empl == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }

        this.members.add(empl);
        empl.getProjects().add(this);
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public void attachLeader(Employee leader) {
        if (leader == null) {
            throw new IllegalArgumentException("Employee leader must not be null");
        }

        if (this.getLeader() != null) {
            this.getLeader().getProjectsLeader().remove(this);
        }

        this.leader = leader;
        this.leader.getProjectsLeader().add(this);
    }

    public void detachLeader() {
        if (this.leader != null) {
            this.leader.getProjectsLeader().remove(this);
        }
        this.leader = null;
    }

    public String toString() {
        return name;
    }

}
