package swt6.ue3.logbook;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.ue3.logbook.jpa.util.Util;
import swt6.ue3.logbook.logic.EmployeeService;
import swt6.ue3.logbook.ui.MainMenu;
import swt6.ue3.logbook.ui.Menu;

import javax.persistence.EntityManagerFactory;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class Launch {

    public static void main(String[] args) {
        try (AbstractApplicationContext applicationContext =
                     new ClassPathXmlApplicationContext("swt6/ue3/logbook/applicationContext.xml");
             Menu menu = new MainMenu()) {
            menu.printHeader().printEntranceInfo();

            menu.run();
        }
    }

}
