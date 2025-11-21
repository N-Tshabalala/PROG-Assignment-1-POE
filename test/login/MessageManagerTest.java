/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package login;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerTest {
    
    public MessageManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addMessage method, of class MessageManager.
     */
    @Test
    public void testAddMessage() {
        System.out.println("addMessage");
        String $recipient = "";
        String $messageText = "";
        String $flag = "";
        MessageManager instance = new MessageManager();
        instance.addMessage($recipient, $messageText, $flag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSentSenderRecipient method, of class MessageManager.
     */
    @Test
    public void testGetSentSenderRecipient() {
        System.out.println("getSentSenderRecipient");
        MessageManager instance = new MessageManager();
        String[] expResult = null;
        String[] result = instance.getSentSenderRecipient();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLongestSentMessage method, of class MessageManager.
     */
    @Test
    public void testGetLongestSentMessage() {
        System.out.println("getLongestSentMessage");
        MessageManager instance = new MessageManager();
        String expResult = "";
        String result = instance.getLongestSentMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchByMessageID method, of class MessageManager.
     */
    @Test
    public void testSearchByMessageID() {
        System.out.println("searchByMessageID");
        String $id = "";
        MessageManager instance = new MessageManager();
        String expResult = "";
        String result = instance.searchByMessageID($id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchAllByRecipient method, of class MessageManager.
     */
    @Test
    public void testSearchAllByRecipient() {
        System.out.println("searchAllByRecipient");
        String $recipient = "";
        MessageManager instance = new MessageManager();
        String[] expResult = null;
        String[] result = instance.searchAllByRecipient($recipient);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByHash method, of class MessageManager.
     */
    @Test
    public void testDeleteByHash() {
        System.out.println("deleteByHash");
        String $hash = "";
        MessageManager instance = new MessageManager();
        boolean expResult = false;
        boolean result = instance.deleteByHash($hash);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayReport method, of class MessageManager.
     */
    @Test
    public void testDisplayReport() {
        System.out.println("displayReport");
        MessageManager instance = new MessageManager();
        String expResult = "";
        String result = instance.displayReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetAll method, of class MessageManager.
     */
    @Test
    public void testResetAll() {
        System.out.println("resetAll");
        MessageManager instance = new MessageManager();
        instance.resetAll();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
