package swt6.ue3.logbook.controller;

/**
 * @author: Dinu Marius-Constantin
 * @date: 19.03.2016
 */
@FunctionalInterface
public interface ConfirmationConsumer {
    boolean askForConfirmation();
}
