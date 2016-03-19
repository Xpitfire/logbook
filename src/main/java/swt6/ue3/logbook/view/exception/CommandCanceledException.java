package swt6.ue3.logbook.view.exception;

/**
 * @author: Dinu Marius-Constantin
 * @date: 12.03.2016
 */
public class CommandCanceledException extends RuntimeException {
    public CommandCanceledException(String message) {
        super(message);
    }
}
