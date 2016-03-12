package swt6.ue2.logbook;

import swt6.ue2.logbook.jpa.util.JpaUtil;
import swt6.ue2.logbook.ui.MainMenu;
import swt6.ue2.logbook.ui.Menu;

import javax.persistence.EntityManager;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class Launch {

    public static void main(String[] args) {
        try (Menu menu = new MainMenu()) {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            menu.run();
        }
    }

}
