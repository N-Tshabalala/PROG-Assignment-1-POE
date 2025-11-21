/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author RC_Student_lab
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

public class LoginTest {

    private Login $login;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setup() {
        $login = new Login();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test

    public void usernameCorrectlyFormatted_and_loginProducesWelcomeMessage() {
        String $username = "kyl_1";
        String $password = "Ch&&sec@ke99!";
        String $cell = "+27838968976";
        String $regResult = $login.registerUser($username, $password, $cell);
        assertEquals("User successfully registered.", $regResult);

        boolean $loginOk = $login.loginUser($username, $password);
        assertTrue($loginOk);

        String $expectedWelcome = "Welcome kyl,1 it is great to see you.";
        assertEquals($expectedWelcome, $login.returnLoginStatus());
    }

    @Test
    public void usernameIncorrectlyFormatted_returnsSpecificMessage() {
        String $username = "kyle!!!!!!";
        String $password = "Ch&&sec@ke99!";
        String $cell = "+27838968976";
        String $regResult = $login.registerUser($username, $password, $cell);
        assertEquals(Login.USERNAME_FAILURE, $regResult);
    }

    @Test
    public void passwordMeetsComplexity_returnsTrueFromChecker() {
        String $password = "Ch&&sec@ke99!";
        assertTrue($login.checkPasswordComplexity($password));
    }

    @Test
    public void passwordDoesNotMeetComplexity_returnsSpecificMessage() {
        String $username = "abc_1";
        String $badPassword = "password";
        String $cell = "+27838968976";
        String $regResult = $login.registerUser($username, $badPassword, $cell);
        assertEquals(Login.PASSWORD_FAILURE, $regResult);
    }

    @Test
    public void cellPhoneCorrectlyFormatted_returnsSuccess() {
        String $username = "abc_1";
        String $password = "Ch&&sec@ke99!";
        String $cell = "+27838968976";
        String $regResult = $login.registerUser($username, $password, $cell);
        assertEquals("User successfully registered.", $regResult);
        assertTrue($login.checkCellPhoneNumber($cell));
    }

    @Test
    public void cellPhoneIncorrectlyFormatted_returnsSpecificMessage() {
        String $username = "abc_1";
        String $password = "Ch&&sec@ke99!";
        String $badCell = "08966553";
        String $regResult = $login.registerUser($username, $password, $badCell);
        assertEquals(Login.CELL_FAILURE, $regResult);
    }

    @Test
    public void loginSuccessful_returnsTrue() {
        String $username = "abc_1";
        String $password = "Ch&&sec@ke99!";
        String $cell = "+27838968976";
        $login.registerUser($username, $password, $cell);
        assertTrue($login.loginUser($username, $password));
    }

    @Test
    public void loginFailed_returnsFalse() {
        String $username = "abc_1";
        String $password = "Ch&&sec@ke99!";
        String $cell = "+27838968976";
        $login.registerUser($username, $password, $cell);
        assertFalse($login.loginUser($username, "wrongPassword1!"));
    }

    @Test
    public void usernameCorrectlyFormatted_returnsTrueFromChecker() {
        assertTrue($login.checkUserName("kyl_1"));
    }

    @Test
    public void usernameIncorrectlyFormatted_returnsFalseFromChecker() {
        assertFalse($login.checkUserName("kyle!!!!"));
    }

    @Test
    public void passwordDoesNotMeetComplexity_returnsFalseFromChecker() {
        assertFalse($login.checkPasswordComplexity("password"));
    }

    @Test
    public void cellPhoneNumberIncorrectlyFormatted_returnsFalseFromChecker() {
        assertFalse($login.checkCellPhoneNumber("08966553"));
    }

    /**
     * Test of checkUserName method, of class Login.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        String $username = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkUserName($username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        String $password = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity($password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String $cellNumber = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber($cellNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String $username = "";
        String $password = "";
        String $cellNumber = "";
        Login instance = new Login();
        String expResult = "";
        String result = instance.registerUser($username, $password, $cellNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String $username = "";
        String $password = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser($username, $password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        Login instance = new Login();
        String expResult = "";
        String result = instance.returnLoginStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegisteredUsername method, of class Login.
     */
    @Test
    public void testGetRegisteredUsername() {
        System.out.println("getRegisteredUsername");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getRegisteredUsername();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegisteredPassword method, of class Login.
     */
    @Test
    public void testGetRegisteredPassword() {
        System.out.println("getRegisteredPassword");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getRegisteredPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegisteredCellNumber method, of class Login.
     */
    @Test
    public void testGetRegisteredCellNumber() {
        System.out.println("getRegisteredCellNumber");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getRegisteredCellNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}



