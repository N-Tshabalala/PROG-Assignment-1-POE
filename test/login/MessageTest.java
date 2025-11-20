import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testConstructor() {
        Message msg = new Message("Alice", "Hello there");
        assertEquals("Alice", msg.getSender());
        assertEquals("Hello there", msg.getContent());
    }

    @Test
    public void testEmptyMessageNotAllowed() {
        Exception err = assertThrows(IllegalArgumentException.class, () -> {
            new Message("Alice", "");
        });
        assertEquals("Message content cannot be empty.", err.getMessage());
    }

    @Test
    public void testSetContent() {
        Message msg = new Message("Bob", "Hi");
        msg.setContent("Updated");
        assertEquals("Updated", msg.getContent());
    }
}
