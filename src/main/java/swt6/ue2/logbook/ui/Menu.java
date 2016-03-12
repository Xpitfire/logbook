package swt6.ue2.logbook.ui;

import swt6.ue2.logbook.io.Console;

/**
 * @author: Dinu Marius-Constantin
 * @date: 10.03.2016
 */
public abstract class Menu {

    private static boolean initializing = true;
    protected final Console console;
    protected String input;

    protected Menu(Console console) {
        this.console = console;
        if (initializing) {
            printHeader();
            initializing = false;
        }
        console.skipLine();
        printMenuTile();
        printMenuOptions();
    }

    public abstract String getMenuTitle();
    public abstract void enterMenu();
    public abstract void printMenuOptions();

    protected void printHeader() {
        console.println(
                " _______             _                           ______                        ____ _______ \n" +
                        "(_______)           | |                         |  ___ \\                      / __ (_______)\n" +
                        " _____   ____  ____ | | ___  _   _  ____ ____   | | _ | |____   ____ ____    ( (__) )_____  \n" +
                        "|  ___) |    \\|  _ \\| |/ _ \\| | | |/ _  ) _  )  | || || |  _ \\ / _  |    \\    \\__  (_____ \\ \n" +
                        "| |_____| | | | | | | | |_| | |_| ( (/ ( (/ /   | || || | | | ( ( | | | | |     / / _____) )\n" +
                        "|_______)_|_|_| ||_/|_|\\___/ \\__  |\\____)____)  |_||_||_|_| |_|\\_|| |_|_|_|    /_/ (______/ \n" +
                        "              |_|           (____/                            (_____|                       ");
    }

    protected void printMenuTile() {
        console.println(String.format("===== [%s] =====", getMenuTitle().toUpperCase()));
    }

    protected void printSeparator() {
        console.println(
                "--------------------------------------------------------------------------------------------");
    }

    protected void printInvalidInput() {
        console.println("Invalid input!");
        console.skipLine();
        printMenuOptions();
    }

}
