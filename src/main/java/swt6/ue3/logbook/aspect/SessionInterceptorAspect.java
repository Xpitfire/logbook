package swt6.ue3.logbook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.ue3.logbook.view.ViewWriter;

@Aspect
@Component
public class SessionInterceptorAspect {

    @Autowired
    private ViewWriter viewWriter;

    @Autowired
    private SessionFactory sessionFactory;

    @Around("execution(public * swt6.ue3.logbook.controller..*Controller.create*(..)) || " +
            "execution(public * swt6.ue3.logbook.controller..*Controller.find*(..)) || " +
            "execution(public * swt6.ue3.logbook.controller..*Controller.delete*(..)) || " +
            "execution(public * swt6.ue3.logbook.controller..*Controller.update*(..)) || " +
            "execution(public * swt6.ue3.logbook.controller..*Controller.link*(..))")
    public Object holdSessionAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue;
        viewWriter.println("###### >>>> transaction started");
        returnValue = pjp.proceed();
        viewWriter.println("###### <<<< transaction closed");
        return returnValue;
    }

}