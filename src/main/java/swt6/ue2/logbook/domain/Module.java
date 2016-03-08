package swt6.ue2.logbook.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Dinu Marius-Constantin
 * @date: 08.03.2016
 */
@Entity
public class Module {

    private Long id;
    private String name;
    private Project project;

    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "module",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            optional = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void attachModule(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Module must not be null");
        }

        if (this.getProject() != null) {
            this.getProject().getModules().remove(this);
        }

        this.project = project;
        this.project.getModules().add(this);
    }

    public void detachModule() {
        if (this.project != null) {
            this.project.getModules().remove(this);
        }
        this.project = null;
    }
}
