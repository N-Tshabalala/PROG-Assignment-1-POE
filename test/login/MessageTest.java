package login;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {

    private MessageManager manager;

    @Before
    public void setup() {
        manager = new MessageManager();
        manager.resetAll(); // start fresh for every test

        // Populate arrays with test data (Part 3 test cases)
        manager.addMessage("+27834557896", "Did you get the cake?", "Sent");       // Message 1
        manager.addMessage("+27838884567", "Message", "Stored");                  // Message 2
        manager.addMessage("+27834484567", "Yohoooo, I am at your gate.", "Disregard"); // Message 3
        manager.addMessage("0838884567", "It is dinner time!", "Sent");           // Message 4
        manager.addMessage("+27838884567", "Ok, I am leaving without you.", "Stored"); // Message 5
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        String[] sent = manager.getSentSenderRecipient();
        assertEquals(2, sent.length);
        assertTrue(sent[0].contains("Did you get the cake?"));
        assertTrue(sent[1].contains("It is dinner time!"));
    }

    @Test
    public void testLongestSentMessage() {
        String longest = manager.getLongestSentMessage();
        assertEquals("It is dinner time!", longest.length() > 0 ? longest : ""); // Ensure length > 0
    }

    @Test
    public void testSearchByMessageID() {
        String id = manager.messageIDs[1]; // Message 2 ID
        String result = manager.searchByMessageID(id);
        assertTrue(result.contains("Message") || result.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testSearchAllByRecipient() {
        String[] messages = manager.searchAllByRecipient("+27838884567");
        assertEquals(2, messages.length);
        assertTrue(messages[0].contains("Message") || messages[0].contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = manager.messageHashes[1]; // Delete Message 2
        boolean deleted = manager.deleteByHash(hashToDelete);
        assertTrue(deleted);

        // Ensure it's removed from arrays
        for (String msg : manager.storedMessages) {
            if (msg != null) assertFalse(msg.contains("Message") && msg.contains("+27838884567"));
        }
    }

    @Test
    public void testDisplayReport() {
        String report = manager.displayReport();
        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
        assertTrue(report.length() > 0);
    }
}
