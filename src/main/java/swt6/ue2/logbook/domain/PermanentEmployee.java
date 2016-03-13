package swt6.ue2.logbook.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PERMANENT_EMPLOYEE")
public class PermanentEmployee extends Employee {

    private Double salary;

    private Integer hoursPerWeek;

    public PermanentEmployee() {
    }

    public PermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
        super(firstName, lastName, dateOfBirth);
    }

    public PermanentEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
        super(firstName, lastName, dateOfBirth, address);
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String toString() {
        return String.format("PERMANENT EMPLOYEE: %s, Salary: %s", super.toString(), salary);
    }

}
