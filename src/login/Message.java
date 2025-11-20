package login;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Simple Message model.
 * Stores id, recipient, text, hash, and flag.
 */
public class Message {

    // Static counter for total sent messages
    private static int totalMessagesSent = 0;

    public String $messageID;
    public String $recipient;
    public String $messageText;
    public String $messageHash;
    public String $flag; // "Sent", "Stored", "Disregard"

    // Full constructor
    public Message(String $recipient, String $messageText, String $flag, int $indexForHash) {
        this.$recipient = $recipient;
        this.$messageText = $messageText;
        this.$flag = $flag;
        this.$messageID = generateMessageID();
        this.$messageHash = createMessageHash($indexForHash);
    }

    // Constructor for sending a message (default flag "Sent")
    public Message(String $recipient, String $msg) {
        this.$recipient = $recipient;
        this.$messageText = $msg;
        this.$flag = "Sent";
        this.$messageID = generateMessageID();
        this.$messageHash = createMessageHash(0); // simple index for hash
    }

    // Generate random 10-digit numeric ID as string
    private String generateMessageID() {
        Random $r = new Random();
        long $num = Math.abs($r.nextLong()) % 10000000000L; // up to 10 digits
        return String.format("%010d", $num);
    }

    // Create message hash: first two digits of ID : index : first+last words uppercased
    public String createMessageHash(int $indexForHash) {
        String $idPart = ($messageID != null && $messageID.length() >= 2) ? $messageID.substring(0, 2) : "00";
        String $first = "";
        String $last = "";
        if ($messageText != null && !$messageText.trim().isEmpty()) {
            String[] $words = $messageText.trim().split("\\s+");
            $first = $words.length > 0 ? $words[0] : "";
            $last = $words.length > 1 ? $words[$words.length - 1] : $first;
        }
        return ($idPart + ":" + $indexForHash + ":" + ($first + $last)).toUpperCase();
    }

    // Basic message length check
    public String checkMessageLength(String $msg) {
        if ($msg != null && !$msg.isEmpty() && $msg.length() <= 500) {
            return "Message is OK!";
        } else {
            return "Message too long or empty!";
        }
    }

    // Simulate sending message (increment static counter)
    public void sendMessage() {
        totalMessagesSent++;
    }

    // Static method to get total messages sent
    public static String returnTotalMessages() {
        return String.valueOf(totalMessagesSent);
    }

    // Static placeholder for recent messages
    public static String showRecentMessages() {
        return "Feature coming soon";
    }

    // Optional: store message to JSON file
    public void storeToJSON(String $filename) {
        String $line = "{\"MessageID\":\"" + escape($messageID) + "\"," +
                        "\"Recipient\":\"" + escape($recipient) + "\"," +
                        "\"Message\":\"" + escape($messageText) + "\"," +
                        "\"MessageHash\":\"" + escape($messageHash) + "\"," +
                        "\"Flag\":\"" + escape($flag) + "\"}";
        try (FileWriter $fw = new FileWriter($filename, true)) {
            $fw.write($line);
            $fw.write(System.lineSeparator());
        } catch (IOException ex) {
            System.out.println("Error writing JSON: " + ex.getMessage());
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // Read stored messages from JSON file (simplified)
    public static Message[] readStoredFromJSON(String $filename) {
        ArrayList<Message> $list = new ArrayList<>();
        try (BufferedReader $br = new BufferedReader(new FileReader($filename))) {
            String $line;
            while (($line = $br.readLine()) != null) {
                $line = $line.trim();
                if ($line.isEmpty()) continue;
                String $id = extractJsonValue($line, "MessageID");
                String $rec = extractJsonValue($line, "Recipient");
                String $msg = extractJsonValue($line, "Message");
                String $hash = extractJsonValue($line, "MessageHash");
                String $flag = extractJsonValue($line, "Flag");
                Message $m = new Message($rec, $msg, $flag, 0);
                if (!$id.isEmpty()) $m.$messageID = $id;
                if (!$hash.isEmpty()) $m.$messageHash = $hash;
                $list.add($m);
            }
        } catch (IOException e) {
            // file may not exist yet; that's fine
        }
        return $list.toArray(new Message[0]);
    }

    private static String extractJsonValue(String $line, String $key) {
        String $pattern = "\"" + $key + "\":\"";
        int $start = $line.indexOf($pattern);
        if ($start < 0) return "";
        $start += $pattern.length();
        int $end = $line.indexOf("\"", $start);
        if ($end < 0) return "";
        String $raw = $line.substring($start, $end);
        return $raw.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
