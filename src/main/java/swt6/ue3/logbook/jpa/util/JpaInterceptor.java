package swt6.ue3.logbook.jpa.util;

import javax.persistence.EntityManagerFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class JpaInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private EntityManagerFactory entityManagerFactory;
  
    protected EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory emFactory) {
        this.entityManagerFactory = emFactory;
    }

    public Object holdEntityManger(ProceedingJoinPoint pjp) throws Throwable {
        if (entityManagerFactory == null)
            throw new IllegalArgumentException("Property 'entityManagerFactory' is required");

        boolean participate = false;
        if (TransactionSynchronizationManager.hasResource(entityManagerFactory))
            participate = true;
        else {
            logger.trace("Opening EntityManager");
            Util.openEntityManager(entityManagerFactory);
        }

        try {
            return pjp.proceed(); // delegates to method of target class.
        } finally {
            if (!participate) {
                Util.closeEntityManager(entityManagerFactory);
                logger.trace("Closed EntityManager");
            }
        }
    }
}