package swt6.ue3.logbook;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Marius-Constantin on 3/17/2016.
 */
@Configuration
@ImportResource({"swt6/ue3/logbook/applicationContext.xml"})
@EnableAspectJAutoProxy
public class AppConfig {
}
