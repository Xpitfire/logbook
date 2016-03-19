package swt6.ue3.logbook.view;

import swt6.ue3.logbook.view.exception.CommandCanceledException;

/**
 * @author: Dinu Marius-Constantin
 * @date: 17.03.2016
 */
public interface ViewWriter extends AutoCloseable {

    void print(Object object);
    void println(Object object);
    void printf(String s, Object... args);
    void printTableHeader(String... titles);
    void printTableRow(Object... columns);
    String readLine();
    void newLine();
    void printException(Exception ex);
    String readLine(String message);
    void setIndent(int indent);
    void resetIndent();
    String blockingReadCommand(String message, String... commands) throws CommandCanceledException;
    <T> T blockingTypedReadLine(String message, Class<T> clazz, boolean optional);
    <T> T blockingTypedReadLine(String message, Class<T> clazz);

}
