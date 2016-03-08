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

    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Address address;

    private Set<LogbookEntry> logbookEntries = new HashSet<>();
    private Set<Project> projects = new HashSet<>();

    // Classes persisted with Hibernate must have a
    // default constructor
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

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
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

        this.getLogbookEntries().remove(entry);
        entry.setEmployee(null);
    }

    @ManyToMany(mappedBy = "members",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project must not be null");
        }

        this.projects.add(project);
        project.getMembers().add(this);
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
