package swt6.ue3.logbook;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Marius-Constantin on 3/17/2016.
 */
@Configuration
@ImportResource({"swt6/ue3/logbook/applicationContext.xml"})
@EnableAutoConfiguration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableAspectJAutoProxy
public class AppConfig {
}
