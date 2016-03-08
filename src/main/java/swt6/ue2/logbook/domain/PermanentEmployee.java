package swt6.ue2.logbook.domain;

import java.util.Date;

import javax.persistence.Entity;

@Entity
//V1: table per class hierarchy
//@DiscriminatorValue("P")
public class PermanentEmployee extends Employee {

    private double salary;

    public PermanentEmployee() {
    }

    public PermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
        super(firstName, lastName, dateOfBirth);
    }

    public PermanentEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
        super(firstName, lastName, dateOfBirth, address);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String toString() {
        return super.toString() + ", salary=" + salary;
    }
}
