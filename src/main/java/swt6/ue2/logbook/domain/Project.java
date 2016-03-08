package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Project implements Serializable {

    private Long id;
    private String name;
    private Set<Employee> members = new HashSet<>();
    private Set<Module> modules = new HashSet<>();

    @Id
    @GeneratedValue
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ProjectEmployee",
            joinColumns = {@JoinColumn(name = "projectId")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId")})
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

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

    public String toString() {
        return name;
    }

}
