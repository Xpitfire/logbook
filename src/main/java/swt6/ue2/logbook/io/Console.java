package swt6.ue2.logbook.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public class Console implements AutoCloseable {

    private final BufferedReader in;
    private final PrintStream out;
    private final PrintStream err;
    private String indent;

    public Console() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = System.out;
        err = System.err;
    }

    public void print(Object object) {
        printIndent(out);
        out.print(object);
    }

    public void println(Object object) {
        printIndent(out);
        out.println(object);
    }

    public void printException(Exception ex) {
        err.print(ex);
    }

    public String readLine() {
        return readLine(null);
    }

    public String readLine(String message) {
        if (message != null) {
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

    public void setIndent(int indent) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            b.append(" ");
        }
        this.indent = b.toString();
    }

    public void resetIndent() {
        this.indent = null;
    }

    public void skipLine() {
        out.println();
    }
}
