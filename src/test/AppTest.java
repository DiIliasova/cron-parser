package test;

import main.com.company.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


//TODO add formatter tests separately
class AppTest {

    private static PrintStream console;

    @BeforeAll
    static void setUp() {
        console = System.out;
    }


    @Test
    void testIllegalNumberOfArguments() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[]{""});
        } finally {
            System.setOut(console);
        }
        Assertions.assertTrue(outputStream.toString().contains("execution error"));
    }

    @Test
    void testHappyPathFromTask() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[]{"*/15 0 1,15 * 1-5 /usr/bin/find"});
        } finally {
            System.setOut(console);
        }
        String expected =
                "minute        0 15 30 45\n" +
                "hour          0\n" +
                "day of month  1 15\n" +
                "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week   1 2 3 4 5\n" +
                "command       /usr/bin/find\n";
        Assertions.assertEquals(outputStream.toString(), expected);
    }

    @Test
    void testHappyPath() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[]{"23 0-20/2 * * * /usr/bin/find"});
        } finally {
            System.setOut(console);
        }
        String expected =
                        "minute        23\n" +
                        "hour          0 2 4 6 8 10 12 14 16 18 20\n" +
                        "day of month  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31\n" +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week   0 1 2 3 4 5 6\n" +
                        "command       /usr/bin/find\n";
        Assertions.assertEquals(outputStream.toString(), expected);
    }

    @Test
    void testHappyPath2() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[]{"0 0,12 1 */2 * /usr/bin/find"});
        } finally {
            System.setOut(console);
        }
        String expected =
                        "minute        0\n" +
                        "hour          0 12\n" +
                        "day of month  1\n" +
                        "month         1 3 5 7 9 11\n" +
                        "day of week   0 1 2 3 4 5 6\n" +
                        "command       /usr/bin/find\n";
        Assertions.assertEquals(outputStream.toString(), expected);
    }

    @Test
    void testErrorInExpression() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outputStream));
            Main.main(new String[]{"23 0-20/2, * * * /usr/bin/find"});
        } finally {
            System.setOut(console);
        }
        Assertions.assertTrue(outputStream.toString().contains("execution error"));
    }
}


