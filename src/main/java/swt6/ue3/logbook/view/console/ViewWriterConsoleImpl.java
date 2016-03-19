package swt6.ue3.logbook.view.console;

import org.springframework.stereotype.Component;
import swt6.ue3.logbook.view.ViewWriter;
import swt6.ue3.logbook.view.exception.CommandCanceledException;
import swt6.ue3.logbook.view.exception.IllegalBooleanLogicException;
import swt6.ue3.logbook.view.exception.IllegalDateFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
@Component("consoleViewWriter")
public class ViewWriterConsoleImpl implements ViewWriter {

    private final BufferedReader in;
    private final PrintStream out;
    private final PrintStream err;
    private String indent;

    public ViewWriterConsoleImpl() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = System.out;
        err = System.err;
    }

    @Override
    public void print(Object object) {
        printIndent(out);
        out.print(object);
    }

    @Override
    public void println(Object object) {
        printIndent(out);
        out.println(object);
    }

    @Override
    public void printTableHeader(String... titles) {
        printIndent(out);
        out.print("_____________________________________________________________________________________");
        newLine();
        printIndent(out);
        for (String title : titles) {
            out.printf("%25s", title);
        }
        newLine();
        printIndent(out);
        out.print("=====================================================================================");
        newLine();
    }

    @Override
    public void printTableRow(Object... columns) {
        printIndent(out);
        for (Object column : columns) {
            out.printf("%25s", column);
        }
        newLine();
    }

    @Override
    public void printException(Exception ex) {
        err.print(ex);
    }

    @Override
    public String readLine() {
        return readLine(null);
    }

    @Override
    public String readLine(String message) {
        if (message != null) {
            printIndent(out);
            out.print(message);
        }
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (err != null) {
            err.close();
        }
    }

    private void printIndent(PrintStream stream) {
        if (indent != null) {
            stream.print(indent);
        }
    }

    @Override
    public void setIndent(int indent) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            b.append(" ");
        }
        this.indent = b.toString();
    }

    @Override
    public void resetIndent() {
        this.indent = null;
    }

    @Override
    public void newLine() {
        out.println();
    }

    @Override
    public String blockingReadCommand(String message, String... commands) throws CommandCanceledException {
        String result;
        for (String c : commands) {
            if (c.equalsIgnoreCase("x"))
                throw new IllegalArgumentException("Command cannot override cancel keyword 'x'!");
        }
        List<String> commandList = new ArrayList<>(Arrays.asList(commands));
        commandList.add("x");
        message += ", [x] = CANCEL >";

        while (true) {
            printIndent(out);
            final String input = readLine(message);
            if (commandList.stream().anyMatch(s -> s.equalsIgnoreCase(input))) {
                result = input;
                break;
            }
        }
        if (result.equalsIgnoreCase("x"))
            throw new CommandCanceledException("User canceled input");

        return result;
    }

    @Override
    public <T> T blockingTypedReadLine(String message, Class<T> clazz, boolean optional) {
        T result = null;
        String input;
        message += optional ? " (leave empty and press ENTER to skip) >" : " >";
        do {
            try {
                printIndent(out);
                input = readLine(message);
                if (input.isEmpty() && optional) {
                    break;
                }

                if (clazz.equals(String.class)) {
                    result = (T)input;
                } else if (clazz.equals(Integer.class)) {
                    result = (T)Integer.valueOf(input);
                } else if (clazz.equals(Double.class)) {
                    result = (T)Double.valueOf(input);
                } else if (clazz.equals(Boolean.class)) {
                    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                        result = (T) Boolean.valueOf(input);
                    } else if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
                        result = (T) (input.equalsIgnoreCase("y") ? new Boolean(true) : new Boolean(false));
                    } else if (input.isEmpty()) {
                        result = (T) new Boolean(false);
                    } else {
                        throw new IllegalBooleanLogicException("Only (true/false), (y/n) or (ENTER with empty result = false) is supported!");
                    }
                } else if (clazz.equals(Long.class)) {
                    result = (T)Long.valueOf(input);
                } else if (clazz.equals(Character.class)) {
                    result = (T)Character.valueOf(input.charAt(0));
                } else if (clazz.equals(Date.class)) {
                    DateFormat df;
                    if (input.length() == 10) {
                        df = new SimpleDateFormat("dd.MM.yyyy");
                    } else if (input.length() == 16) {
                        df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    } else {
                        throw new IllegalDateFormatException("Unsupported date format! (dd.MM.yyyy hh:mm)");
                    }
                    result = (T)df.parse(input);
                } else {
                    throw new UnsupportedOperationException("Only base language data types are supported!");
                }
            } catch (ClassCastException | ParseException ex) {
                // skip and retry
            } catch (IllegalDateFormatException | IllegalBooleanLogicException | NumberFormatException iex) {
                println(iex.getMessage());
            }
        } while (result == null);
        return result;
    }

    @Override
    public <T> T blockingTypedReadLine(String message, Class<T> clazz) {
        return blockingTypedReadLine(message, clazz, false);
    }

    @Override
    public void printf(String s, Object... args) {
        printIndent(out);
        out.printf(s, args);
    }
}
