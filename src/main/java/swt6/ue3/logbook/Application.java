package swt6.ue3.logbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swt6.ue3.logbook.ui.Menu;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        Menu menu = applicationContext.getBean("mainMenu", Menu.class);
        menu.printHeader().printEntranceInfo();
        menu.run();
    }

}
