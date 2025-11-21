package login;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Login $login = new Login();

        // USERNAME PROMPT 
        String $username;
        do {
            $username = JOptionPane.showInputDialog("""
                    Enter username:
                    - Must contain an underscore '_'
                    - Must be no longer than 5 characters
                    """);
            if ($username == null) return; // user canceled
            if (!$login.checkUserName($username)) {
                JOptionPane.showMessageDialog(null, Login.USERNAME_FAILURE);
            }
        } while (!$login.checkUserName($username));

        // PASSWORD PROMPT 
        String $password;
        do {
            $password = JOptionPane.showInputDialog("""
                    Enter password:
                    - At least 8 characters
                    - Must include a capital letter, a number, and a special character
                    """);
            if ($password == null) return;
            if (!$login.checkPasswordComplexity($password)) {
                JOptionPane.showMessageDialog(null, Login.PASSWORD_FAILURE);
            }
        } while (!$login.checkPasswordComplexity($password));

        // CELL NUMBER PROMPT 
        String $cell;
        do {
            $cell = JOptionPane.showInputDialog("""
                    Enter cellphone number:
                    - Must start with +27
                    - Must contain 9 digits after +27 (e.g. +27821234567)
                    """);
            if ($cell == null) return;
            if (!$login.checkCellPhoneNumber($cell)) {
                JOptionPane.showMessageDialog(null, Login.CELL_FAILURE);
            }
        } while (!$login.checkCellPhoneNumber($cell));

        // REGISTER USER
        String $registerMsg = $login.registerUser($username, $password, $cell);
        JOptionPane.showMessageDialog(null, $registerMsg);

        // LOGIN USER
        boolean $isLogged = $login.loginUser($username, $password);
        if (!$isLogged) {
            JOptionPane.showMessageDialog(null, Login.LOGIN_FAILURE + "\nExiting...");
            return;
        }

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        // MAIN MENU LOOP
        while (true) {
            String $option = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send Messages
                    2) Show recently sent messages
                    3) Quit
                    """);

            if ($option == null) return; // user canceled

            switch ($option) {
                case "1" -> {
                    int $count = 0;
                    try {
                        $count = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you want to send?"));
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid number entered.");
                        continue;
                    }

                    for (int $i = 0; $i < $count; $i++) {
                        String $recipient;
                        do {
                            $recipient = JOptionPane.showInputDialog("Enter recipient number (+27...)");
                            if ($recipient == null) return;
                            if (!$recipient.matches("^\\+27\\d{9}$")) {
                                JOptionPane.showMessageDialog(null, "Invalid recipient number. Must start with +27 and be 12 digits total.");
                            }
                        } while (!$recipient.matches("^\\+27\\d{9}$"));

                        String $msg = JOptionPane.showInputDialog("Enter your message:");
                        if ($msg == null) return;

                        Message $m = new Message($recipient, $msg);
                        JOptionPane.showMessageDialog(null, $m.checkMessageLength($msg));
                        $m.sendMessage();
                    }
                    JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
                }

                // calls showRecentMessages()
                case "2" -> {
                    JOptionPane.showMessageDialog(null, Message.showRecentMessages());
                }

                case "3" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;
                }

                default -> JOptionPane.showMessageDialog(null, "Invalid option, try again.");
            }
        }
    }
}
