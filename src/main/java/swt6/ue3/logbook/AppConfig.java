package swt6.ue3.logbook;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import swt6.ue3.logbook.io.ConsoleViewWriterImpl;
import swt6.ue3.logbook.io.ViewWriter;

/**
 * Created by Marius-Constantin on 3/17/2016.
 */
@Configuration
@ImportResource({"swt6/ue3/logbook/applicationContext.xml"})
public class AppConfig {
/*
    public ViewWriter viewWriter() {
        return new ConsoleViewWriterImpl();
    }
*/
}
