package swt6.ue3.logbook.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.Application;

/**
 * @author: Dinu Marius-Constantin
 * @date: 17.03.2016
 */
@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(Application.class);

    @After("execution(public * swt6.ue3.logbook.logic..*Service.*(..))")
    public void logMethodEnterAdvice(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("[INFO-LOG-ASPECT] ", methodName);
    }

}
