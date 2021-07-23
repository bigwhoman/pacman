import model.User;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @org.junit.jupiter.api.Test
    void compareTest() {
        ArrayList<User> users = new ArrayList<>();
        User a = new User("za", "lo");
        User b = new User("za", "lo");
        User c = new User("aa", "lo");
        User d = new User("ab", "lo");
        User e = new User("bc", "lo");
        users.add(a);
        users.add(b);
        users.add(c);
        users.add(d);
        users.add(e);
        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getScore());
        }
        Collections.sort(users);
        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getScore());
        }
        a.setScore(20);
        b.setScore(200);
        c.setScore(20);
        d.setScore(20);
        e.setScore(400);
        Collections.sort(users);
        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getScore());
        }

        assertEquals("za 0\r\n" +
                "za 0\r\n" +
                "aa 0\r\n" +
                "ab 0\r\n" +
                "bc 0\r\n" +
                "aa 0\r\n" +
                "ab 0\r\n" +
                "bc 0\r\n" +
                "za 0\r\n" +
                "za 0\r\n" +
                "bc 400\r\n" +
                "za 200\r\n" +
                "aa 20\r\n" +
                "ab 20\r\n" +
                "za 20\r\n", outContent.toString());
    }
}
