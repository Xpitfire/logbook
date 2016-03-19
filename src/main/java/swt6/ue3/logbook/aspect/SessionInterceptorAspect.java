package swt6.ue3.logbook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import swt6.ue3.logbook.Application;
import swt6.ue3.logbook.view.ViewWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Aspect
@Component
public class SessionInterceptorAspect {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Around("@annotation(swt6.ue3.logbook.annotation.SessionExtended) && execution(public * swt6.ue3.logbook..*(..))")
    public Object extendSessionAdvice(ProceedingJoinPoint pjp) throws Throwable {
        boolean participate = false;
        if (TransactionSynchronizationManager.hasResource(entityManagerFactory))
            participate = true;
        else {
            logger.trace("Opening EntityManager");
            openEntityManager();
        }
        Object returnValue;
        viewWriter.println("###### >>>> transaction started");
        try {
            returnValue = pjp.proceed();
        } finally {
            viewWriter.println("###### <<<< transaction closed");
            if (!participate) {
                closeEntityManager();
                logger.trace("Closed EntityManager");
            }
        }
        return returnValue;
    }

    private EntityManager openEntityManager() {
        try {
            EntityManager em = getEntityManager(entityManagerFactory);
            if (em == null) {
                logger.trace("Opening JPA EntityManager");
                em = entityManagerFactory.createEntityManager();
            }

            TransactionSynchronizationManager.bindResource(entityManagerFactory,
                    new EntityManagerHolder(em));
            return em;
        } catch (IllegalStateException ex) {
            throw new DataAccessResourceFailureException("Could not open JPA EntityManager", ex);
        }
    }

    private EntityManager getEntityManager(EntityManagerFactory entityManagerFactory) {
        if (entityManagerFactory == null)
            return null;
        return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
    }

    private void closeEntityManager() {
        try {
            EntityManager em = getEntityManager(entityManagerFactory);
            if (em != null) {
                TransactionSynchronizationManager.unbindResource(entityManagerFactory);
                em.close();
                logger.trace("Closed JPA EntityManager");
            }
        } catch (IllegalStateException ex) {
            throw new DataAccessResourceFailureException("Could not close JPA EntityManager", ex);
        }
    }

}