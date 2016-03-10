package swt6.ue2.logbook.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Dinu Marius-Constantin
 * @date: 08.03.2016
 */
@Entity
public class Phase implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "phase",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();


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

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogbookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (logbookEntry.getPhase() != null) {
            logbookEntry.getPhase().getLogbookEntries().remove(logbookEntry);
        }

        this.logbookEntries.add(logbookEntry);
        logbookEntry.setPhase(this);
    }

    public void removeLogbookEntry(LogbookEntry logbookEntry) {
        if (logbookEntry == null) {
            throw new IllegalArgumentException("LogbookEntry must not be null");
        }

        if (logbookEntry.getPhase() != null && logbookEntry.getPhase() != this) {
            throw new IllegalArgumentException("LogbookEntry is associated with another phase");
        }

        this.logbookEntries.remove(logbookEntry);
        logbookEntry.setPhase(null);
    }

}
