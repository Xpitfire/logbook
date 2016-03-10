package swt6.ue2.logbook.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name = "employeeType")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @OneToMany(mappedBy = "leader",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Project> supervisedProjects = new HashSet<>();

    @ManyToMany(mappedBy = "members",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, Date dateOfBirth) {
        this(firstName, lastName, dateOfBirth, null);
    }

    public Employee(String firstName, String lastName, Date dateOfBirth, Address address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        this.projects.add(project);
        project.getMembers().add(this);
    }

    public void removeProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        this.projects.remove(project);
        project.getMembers().remove(this);
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogbookEntries().remove(entry);
        }

        this.logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    public void removeLogbookEntry(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (entry.getEmployee() != null && entry.getEmployee() != this) {
            throw new IllegalArgumentException("LogbookEntry is associated with another employee");
        }

        this.logbookEntries.remove(entry);
        entry.setEmployee(null);
    }

    public Set<Project> getSupervisedProjects() {
        return supervisedProjects;
    }

    public void setSupervisedProjects(Set<Project> projectsLeader) {
        this.supervisedProjects = projectsLeader;
    }

    public void addSupervisedProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        if (project.getLeader() != null) {
            project.getLeader().getLogbookEntries().remove(project);
        }

        this.supervisedProjects.add(project);
        project.setLeader(this);
    }

    public void removeSupervisedProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        if (project.getLeader() != null && project.getLeader() != this) {
            throw new IllegalArgumentException("Project is associated with another leader");
        }

        this.supervisedProjects.remove(project);
        project.setLeader(null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d: %s, %s (%4$td.%4$tm.%4$tY)",
                id, lastName, firstName, dateOfBirth));
        if (address != null) {
            sb.append(address);
        }
        return sb.toString();
    }
}
