package swt6.ue3.logbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swt6.ue3.logbook.view.console.AbstractConsoleView;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        AbstractConsoleView menu = applicationContext.getBean("mainView", AbstractConsoleView.class);
        menu.showHeader().showEntranceInfo().run();
    }

}
