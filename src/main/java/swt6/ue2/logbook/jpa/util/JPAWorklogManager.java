package swt6.ue2.logbook.jpa.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import swt6.ue2.logbook.dao.Dao;
import swt6.ue2.logbook.dao.DaoFactory;
import swt6.ue2.logbook.dao.EmployeeDao;
import swt6.ue2.logbook.domain.Address;
import swt6.ue2.logbook.domain.Employee;
import swt6.ue2.logbook.domain.LogbookEntry;
import swt6.ue2.logbook.domain.PermanentEmployee;
import swt6.ue2.logbook.domain.Project;
import swt6.ue2.logbook.domain.TemporaryEmployee;
import swt6.ue2.util.DateUtil;


public class JpaWorklogManager {

    private static Employee saveEmployee(Employee empl) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        em.persist(empl);
        JpaUtil.commit();
        return empl;
    }

    private static void listEmployees() {
        EntityManager em = JpaUtil.getTransactedEntityManager();

        List<Employee> empls = em.createQuery("select e from Employee e", Employee.class).getResultList();

        for (Employee e : empls) {
            System.out.println(e);
            if (e.getLogbookEntries().size() > 0) {
                System.out.println("  logbookEntries:");
                for (LogbookEntry le : e.getLogbookEntries()) {
                    System.out.println("    " + le);
                }
            }

            if (e.getProjects().size() > 0) {
                System.out.println("  projects:");
                for (Project p : e.getProjects()) {
                    System.out.println("    " + p);
                }
            }
        }

        JpaUtil.commit();
    }

    private static void addLogbookEntries(Employee empl, LogbookEntry... entries) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        empl = em.merge(empl);
        for (LogbookEntry entry : entries) {
            entry.attachEmployee(empl);
        }
        JpaUtil.commit();
    }

    private static void assignProjectsToEmployee(Employee empl, Project... projects) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        empl = em.merge(empl);
        for (Project project : projects) {
            project = em.merge(project);
            empl.addProject(project);
        }
        JpaUtil.commit();
    }

    private static void listLogbookEntriesOfEmployee(Employee empl) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        System.out.println("logbook entries of employee:");
        TypedQuery<LogbookEntry> qry = em.createQuery("from LogbookEntry where employee.id=:emplId", LogbookEntry.class);
        qry.setParameter("emplId", empl.getId());
        List<LogbookEntry> entries = qry.getResultList();
        for (LogbookEntry entry : entries) {
            System.out.println("   " + entry);
        }
        JpaUtil.commit();
    }

    public static void main(String[] args) {
        Employee empl1 = new PermanentEmployee("Bill", "Gates", DateUtil.getDate(1970, 1, 21));
        Employee empl2 = new PermanentEmployee("Franz", "Mayr", DateUtil.getDate(1980, 1, 21), new Address("4232", "Hagenberg", "Hauptstraße 1"));

        PermanentEmployee empl3 = new PermanentEmployee("Karl", "Kopf", DateUtil.getDate(1950, 9, 25));
        empl3.setSalary(5000.0);

        TemporaryEmployee empl4 = new TemporaryEmployee("Max", "Mustermann", DateUtil.getDate(1960, 9, 25));
        empl4.setHourlyRate(50.0);
        empl4.setRenter("Microsoft");
        empl4.setStartDate(DateUtil.getDate(2013, 01, 01));
        empl4.setEndDate(DateUtil.getDate(2013, 03, 28));

        LogbookEntry entry1 = new LogbookEntry("Analyse", DateUtil.getTime(10, 15), DateUtil.getTime(12, 15));
        LogbookEntry entry2 = new LogbookEntry("Implementierung", DateUtil.getTime(8, 00), DateUtil.getTime(16, 00));
        LogbookEntry entry3 = new LogbookEntry("Testen", DateUtil.getTime(12, 30), DateUtil.getTime(17, 00));

        Project prj1 = new Project("Enterprise Server");
        prj1.setLeader(empl1);
        Project prj2 = new Project("Office Project");
        prj2.setLeader(empl1);

        System.out.println("----- saveEmployee ------");
        empl1 = saveEmployee(empl1);

        System.out.println("----- saveEmployee ------");
        empl2 = saveEmployee(empl2);

        System.out.println("----- saveEmployee ------");
        empl3 = (PermanentEmployee) saveEmployee(empl3);

        System.out.println("----- saveEmployee ------");
        empl4 = (TemporaryEmployee) saveEmployee(empl4);

        System.out.println("----- addLogbookEntries -----");
        addLogbookEntries(empl1, entry1, entry2);
        addLogbookEntries(empl2, entry3);

        System.out.println("----- listEmployees -----");
        listEmployees();

        System.out.println("----- assignProjectsToEmployees -----");
        assignProjectsToEmployee(empl1, prj1);
        assignProjectsToEmployee(empl1, prj2);
        assignProjectsToEmployee(empl2, prj2);

        System.out.println("----- listEmployees -----");
        listEmployees();

        System.out.println("----- listLogbookEntriesOfEmployee -----");
        listLogbookEntriesOfEmployee(empl1);

        // -------------------------------------------------------------------

        System.out.println("----- Dao tests -----");
        Dao<Employee> dao = DaoFactory.getDao(Employee.class);
        dao.safe(new PermanentEmployee("Marius", "Dinu", DateUtil.getDate(1988, 7, 3)));

        EmployeeDao temporaryEmployeeDao = DaoFactory.getDao(Employee.class);
        List<TemporaryEmployee> tempEmployees = temporaryEmployeeDao.findAll(TemporaryEmployee.class);
        tempEmployees.forEach(System.out::println);

        JpaUtil.closeEntityManagerFactory();
    }
}
