package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Project implements Serializable {

    private Long id;
    private String name;
    private Set<Employee> members = new HashSet<>();

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

    // The join table annotation is superfluous in this case.
    // The name of the table and its attributes are generated
    // automatically if the annotation is omitted.
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

    public String toString() {
        return name;
    }
}
