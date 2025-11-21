/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package login;


public class Login {

    // Stored user details 
    private String $registeredUsername;
    private String $registeredPassword;
    private String $registeredCellNumber;

    // Internal login flag
    private boolean $loginStatus = false;

    // Messages 
    public static final String USERNAME_SUCCESS = "Username successfully captured.";
    public static final String USERNAME_FAILURE =
        "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";

    public static final String PASSWORD_SUCCESS = "Password successfully captured.";
    public static final String PASSWORD_FAILURE =
        "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

    public static final String CELL_SUCCESS = "Cell number successfully captured.";
    public static final String CELL_FAILURE =
        "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";

    public static final String LOGIN_FAILURE = "Username or password incorrect, please try again.";
    private static final String WELCOME_FORMAT = "Welcome %s,%s it is great to see you.";

    

    // Check username: underscore + max 5 characters
    public boolean checkUserName(String $username) {
        if ($username == null) return false;
        return $username.contains("_") && $username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String $password) {
        if ($password == null) return false;
        if ($password.length() < 8) return false;
        boolean $hasUpper = $password.chars().anyMatch(Character::isUpperCase);
        boolean $hasDigit = $password.chars().anyMatch(Character::isDigit);
        boolean $hasSpecial = $password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return $hasUpper && $hasDigit && $hasSpecial;
    }

    // Checks cell number: +27 followed by 9 digits
    public boolean checkCellPhoneNumber(String $cellNumber) {
        if ($cellNumber == null) return false;
        // Regex inspired by ChatGPT 
        return $cellNumber.matches("^\\+27\\d{9}$");
    }

    // Registration & Login 

    public String registerUser(String $username, String $password, String $cellNumber) {
        if (!checkUserName($username)) {
            return USERNAME_FAILURE;
        }
        if (!checkPasswordComplexity($password)) {
            return PASSWORD_FAILURE;
        }
        if (!checkCellPhoneNumber($cellNumber)) {
            return CELL_FAILURE;
        }
        // All checks passed then stores details
        this.$registeredUsername = $username;
        this.$registeredPassword = $password;
        this.$registeredCellNumber = $cellNumber;
        return "User successfully registered.";
    }

    public boolean loginUser(String $username, String $password) {
        boolean $ok = $username != null && $password != null
                && $username.equals(this.$registeredUsername)
                && $password.equals(this.$registeredPassword);
        this.$loginStatus = $ok;
        return $ok;
    }

    public String returnLoginStatus() {
        if (!this.$loginStatus) {
            return LOGIN_FAILURE;
        }
        String $first = "";
        String $last = "";
        if (this.$registeredUsername != null && this.$registeredUsername.contains("_")) {
            String[] $parts = this.$registeredUsername.split("_", 2);
            $first = $parts.length > 0 ? $parts[0] : "";
            $last = $parts.length > 1 ? $parts[1] : "";
        } else {
            $first = this.$registeredUsername != null ? this.$registeredUsername : "";
            $last = "";
        }
        return String.format(WELCOME_FORMAT, $first, $last);
    }

    // Getters for tests
    public String getRegisteredUsername() { return $registeredUsername; }
    public String getRegisteredPassword() { return $registeredPassword; }
    public String getRegisteredCellNumber() { return $registeredCellNumber; }
}

    
