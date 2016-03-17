package swt6.ue3.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 14.03.2016
 */
public class TransactionTest extends BaseTest {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        logbookEntry1.attachEmployee(permanentEmployee1);
        logbookEntry2.attachEmployee(permanentEmployee2);
        logbookEntry3.attachEmployee(permanentEmployee1);
    }

    @Test
    public void testExplicitDaoTransactionRollback() throws Exception {
        employeeRepo.save(permanentEmployee1);
        logbookEntryRepo.save(logbookEntry1);

        assertTrue(employeeRepo.count() == 0);
        assertTrue(logbookEntryRepo.count() == 0);
    }

}
