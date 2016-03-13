package swt6.ue2.logbook.domain;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPORARY_EMPLOYEE")
public class TemporaryEmployee extends Employee {

    private static final DateFormat fmt = DateFormat.getDateInstance();

    private String renter;
    private Double hourlyRate;
    private Date startDate;
    private Date endDate;

    public TemporaryEmployee() {
    }

    public TemporaryEmployee(String firstName, String lastName, Date dateOfBirth) {
        super(firstName, lastName, dateOfBirth);
    }

    public TemporaryEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
        super(firstName, lastName, dateOfBirth, address);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TEMPORARY EMPLOYEE: ").append(super.toString());
        sb.append(" Hourly rate: ").append(hourlyRate);
        sb.append(" Renter: ").append(hourlyRate);
        if (startDate != null)
            sb.append(" Start date: ").append(fmt.format(startDate));
        if (endDate != null)
            sb.append(" End date: ").append(fmt.format(endDate));

        return sb.toString();
    }
}
