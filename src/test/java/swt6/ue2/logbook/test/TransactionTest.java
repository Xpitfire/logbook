package swt6.ue2.logbook.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.ue2.logbook.dal.DaoFactory;

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

    @After
    @Override
    public void complete() {
        super.complete();
    }

    @Test
    public void testExplicitDaoTransactionRollback() throws Exception {
        employeeDao.safe(permanentEmployee1);
        logbookEntryDao.safe(logbookEntry1);
        DaoFactory.rollback();
        assertTrue(employeeDao.count() == 0);
        assertTrue(logbookEntryDao.count() == 0);
    }

}
