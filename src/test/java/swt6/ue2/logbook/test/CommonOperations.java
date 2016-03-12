package swt6.ue2.logbook.test;

import com.ninja_squad.dbsetup.operation.Operation;
import static com.ninja_squad.dbsetup.Operations.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class CommonOperations {

    public static final Operation DELETE_ALL = deleteAllFrom("LOGBOOK_ENTRY", "TASK", "REQUIREMENT", "SPRINT", "PROJECT_EMPLOYEE", "PROJECT", "PERMANENT_EMPLOYEE", "TEMPORARY_EMPLOYEE");

    public static final Operation INSERTT_PERMANENT_EMPLOYEE_DATA =
            sequenceOf(
                    insertInto("PERMANENT_EMPLOYEE")
                            .columns("ID", "CITY", "STREET", "ZIPCODE", "DATEOFBIRTH", "FIRSTNAME", "LASTNAME", "HOURSPERWEEK", "SALARY")
                            .values(1, "Hagenberg", "Hauptstarße 1", "4232", "1988-07-03 00:00:00", "Marius", "Dinu", "40", "4000")
                            .values(2, "Gallneukirchen", "Hauptstarße 27", "4210", "1980-12-09 00:00:00", "Max", "Mustermann", "38", "2500")
                            .build()
            );

    public static final Operation INSERT_TEMPORARY_EMPLOYEE_DATA =
            sequenceOf(
                    insertInto("TEMPORARY_EMPLOYEE")
                            .columns("ID", "CITY", "STREET", "ZIPCODE", "DATEOFBIRTH", "FIRSTNAME", "LASTNAME", "ENDDATE", "HOURLYRATE", "RENTER", "STARTDATE")
                            .values(3, "Linz", "Salzburgerstraße 12", "4030", "1990-01-05 00:00:00", "Ilker", "Coskun", "2016-06-28 00:00:00", "20", "Microsoft", "2013-01-01 00:00:00")
                            .build()
            );

    public static final Operation INSERT_PROJECT_DATA =
            sequenceOf(
                    insertInto("PROJECT")
                            .columns("ID", "NAME", "LEADER_ID")
                            .values(1, "Enterprise Server", 1)
                            .values(2, "Office", 2)
                            .values(3, "Azure", 1)
                            .build()
            );

    public static final Operation INSERT_PROJECT_EMPLOYEE_DATA =
            sequenceOf(
                    insertInto("PROJECT_EMPLOYEE")
                            .columns("PROJECTID", "EMPLOYEEID")
                            .values(1, 1)
                            .values(1, 2)
                            .values(2, 1)
                            .values(3, 2)
                            .values(3, 3)
                            .build()
            );


}
