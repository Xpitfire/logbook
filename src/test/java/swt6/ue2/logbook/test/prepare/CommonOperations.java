package swt6.ue2.logbook.test.prepare;

import com.ninja_squad.dbsetup.operation.Operation;
import static com.ninja_squad.dbsetup.Operations.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class CommonOperations {

    public static final Operation DELETE_ALL = deleteAllFrom("LOGBOOK_ENTRY", "TASK", "REQUIREMENT", "SPRINT", "PROJECT_EMPLOYEE", "PROJECT", "PERMANENT_EMPLOYEE", "TEMPORARY_EMPLOYEE");

    public static final Operation INSERTT_REFERENCE_DATA =
            sequenceOf(
            );

}
