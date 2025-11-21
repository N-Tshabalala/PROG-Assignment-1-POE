package login;

import java.io.IOException;


public class MessageManager {

    public static final int CAP = 100;
    public String[] $sentMessages = new String[CAP];
    public String[] $disregardedMessages = new String[CAP];
    public String[] $storedMessages = new String[CAP];

    public String[] $messageHashes = new String[CAP];
    public String[] $messageIDs = new String[CAP];
    public String[] $recipients = new String[CAP]; //sent/stored arrays for tracking

    public int $sentCount = 0;
    public int $disregardedCount = 0;
    public int $storedCount = 0;
    public int $hashCount = 0;
    public int $idCount = 0;

    private final String $jsonFile = "messages.json";

    // Message add and populate parallel arrays 
    public void addMessage(String $recipient, String $messageText, String $flag) {
         
        int $indexForHash = $sentCount + $storedCount + $disregardedCount;
        Message $m = new Message($recipient, $messageText, $flag, $indexForHash);

        // store ID and hash in arrays
        if ($idCount < CAP) $messageIDs[$idCount++] = $m.$messageID;
        if ($hashCount < CAP) $messageHashes[$hashCount++] = $m.$messageHash;

        // store text + recipient in appropriate arrays
        if ($flag.equalsIgnoreCase("Sent")) {
            if ($sentCount < CAP) {
                $sentMessages[$sentCount] = $m.$messageText;
                $recipients[$sentCount] = $recipient;
                $sentCount++;
            }
        } else if ($flag.equalsIgnoreCase("Disregard") || $flag.equalsIgnoreCase("Disregarded")) {
            if ($disregardedCount < CAP) {
                $disregardedMessages[$disregardedCount] = $m.$messageText;
                $recipients[$sentCount + $disregardedCount] = $recipient; 
                $disregardedCount++;
            }
        } else if ($flag.equalsIgnoreCase("Stored")) {
            if ($storedCount < CAP) {
                $storedMessages[$storedCount] = $m.$messageText;
                
                $recipients[$sentCount + $disregardedCount + $storedCount] = $recipient;
                $storedCount++;
            }
            // write to messages.json
            $m.storeToJSON($jsonFile);
        }
    }

    // Display sender and recipient of all sent messages
   
    public String[] getSentSenderRecipient() {
        String[] $out = new String[$sentCount];
        for (int i = 0; i < $sentCount; i++) {
            String $rec = ($recipients[i] != null) ? $recipients[i] : "UNKNOWN";
            $out[i] = "Developer -> " + $rec + " : " + $sentMessages[i];
        }
        return $out;
    }

    // Display the longest sent message 
    public String getLongestSentMessage() {
        String $longest = "";
        for (int i = 0; i < $sentCount; i++) {
            if ($sentMessages[i] != null && $sentMessages[i].length() > $longest.length()) {
                $longest = $sentMessages[i];
            }
        }
        return $longest;
    }

    // Search for message ID and return recipient and message 
    public String searchByMessageID(String $id) {
        for (int i = 0; i < $idCount; i++) {
            if ($messageIDs[i] != null && $messageIDs[i].equals($id)) {
                // try to find in sentMessages (match by index position when possible)
                // search sent messages for index i (approx)
                for (int s = 0; s < $sentCount; s++) {
                    if ($messageIDs[s].equals($id)) {
                        String $rec = ($recipients[s] != null) ? $recipients[s] : "UNKNOWN";
                        return $sentMessages[s] + " (Recipient: " + $rec + ")";
                    }
                }
                // search stored by loading JSON and matching
                Message[] $loaded = Message.readStoredFromJSON($jsonFile);
                for (Message $m : $loaded) {
                    if ($m != null && $m.$messageID.equals($id)) {
                        return $m.$messageText + " (Recipient: " + $m.$recipient + ")";
                    }
                }
            }
        }
        return "";
    }

    // d) Search for all messages sent to a particular recipient (returns array of message texts)
    public String[] searchAllByRecipient(String $recipient) {
        java.util.ArrayList<String> $list = new java.util.ArrayList<>();
        // check sentMessages
        for (int i = 0; i < $sentCount; i++) {
            if ($recipients[i] != null && $recipients[i].equals($recipient)) {
                $list.add($sentMessages[i]);
            }
        }
        // check stored JSON (it contains stored messages)
        Message[] $stored = Message.readStoredFromJSON($jsonFile);
        for (Message $m : $stored) {
            if ($m != null && $m.$recipient.equals($recipient)) {
                $list.add($m.$messageText);
            }
        }
        // also check storedMessages array (in-memory)
        for (int i = 0; i < $storedCount; i++) {
            int $idx = $sentCount + $disregardedCount + i;
            if ($recipients[$idx] != null && $recipients[$idx].equals($recipient)) {
                $list.add($storedMessages[i]);
            }
        }
        return $list.toArray(new String[0]);
    }

    // Delete a message using message hash (returns true when deletion succeeded)
    public boolean deleteByHash(String $hash) {
        // find hash in messageHashes array
        for (int i = 0; i < $hashCount; i++) {
            if ($messageHashes[i] != null && $messageHashes[i].equals($hash)) {
                // remove from messageHashes and shift arrays for consistent state
                removeIndex($messageHashes, i, $hashCount);
                $hashCount--;
                // also remove corresponding id if exists at same index
                if (i < $idCount) {
                    String $idToDelete = $messageIDs[i];
                    removeIndex($messageIDs, i, $idCount);
                    $idCount--;
                    // also remove from stored JSON file by rewriting without that ID
                    removeIdFromJson($idToDelete);
                }
                // remove from text arrays if found there 
                for (int s = 0; s < $sentCount; s++) {
                    if ($sentMessages[s] != null && createHashFromMessageIndex(s).equals($hash)) {
                        removeIndex($sentMessages, s, $sentCount);
                        removeIndex($recipients, s, $sentCount);
                        $sentCount--;
                        return true;
                    }
                }
                // check disregarded
                for (int d = 0; d < $disregardedCount; d++) {
                    int $idx = $sentCount + d;
                    if (createHashFromMessageIndex($idx).equals($hash)) {
                        removeIndex($disregardedMessages, d, $disregardedCount);
                        removeIndex($recipients, $idx, $sentCount + $disregardedCount); 
                        $disregardedCount--;
                        return true;
                    }
                }
                // check stored in-memory
                for (int st = 0; st < $storedCount; st++) {
                    int $idx = $sentCount + $disregardedCount + st;
                    if (createHashFromMessageIndex($idx).equals($hash)) {
                        removeIndex($storedMessages, st, $storedCount);
                        removeIndex($recipients, $idx, $sentCount + $disregardedCount + $storedCount);
                        $storedCount--;
                        return true;
                    }
                }
                // otherwise we succeeded by removing ID/hash but couldn't find text; still return true
                return true;
            }
        }
        return false;
    }

    // helper: remove index from array and shift left
    private void removeIndex(String[] $arr, int $idx, int $count) {
        int count = 0;
        for (int i = $idx; i < countSafe(countMinusOne(count)); i++) {
            if (i + 1 < $arr.length) $arr[i] = $arr[i + 1];
        }
        
        if (countSafe(countMinusOne(count)) < $arr.length) $arr[countSafe(countMinusOne(count))] = null;
    }
    private int countMinusOne(int c) { return c - 1; }
    private int countSafe(int c) { return c < 0 ? 0 : c; }

    // Remove ID line from JSON file by rewriting file skipping lines that contain the ID
    private void removeIdFromJson(String $idToDelete) {
        if ($idToDelete == null || $idToDelete.isEmpty()) return;
        java.util.ArrayList<String> $lines = new java.util.ArrayList<>();
        try (java.io.BufferedReader $br = new java.io.BufferedReader(new java.io.FileReader($jsonFile))) {
            String $line;
            while (($line = $br.readLine()) != null) {
                if ($line.contains("\"MessageID\":\"" + $idToDelete + "\"")) {
                    
                } else {
                    $lines.add($line);
                }
            }
        } catch (IOException e) {
            
        }
        // rewrite file
        try (java.io.FileWriter $fw = new java.io.FileWriter($jsonFile, false)) {
            for (String l : $lines) {
                $fw.write(l);
                $fw.write(System.lineSeparator());
            }
        } catch (IOException ex) {}
    }

    // Display a report listing Hash, Recipient, Message
    public String displayReport() {
        StringBuilder $sb = new StringBuilder();
        // sent messages
        for (int i = 0; i < $sentCount; i++) {
            String $hash = createHashFromMessageIndex(i);
            String $rec = ($recipients[i] != null) ? $recipients[i] : "UNKNOWN";
            String $msg = ($sentMessages[i] != null) ? $sentMessages[i] : "";
            $sb.append("Hash: ").append($hash).append(" | Recipient: ").append($rec)
               .append(" | Message: ").append($msg).append(System.lineSeparator());
        }
        // stored messages from file
        Message[] $stored = Message.readStoredFromJSON($jsonFile);
        for (Message m : $stored) {
            $sb.append("Hash: ").append(m.$messageHash).append(" | Recipient: ").append(m.$recipient)
               .append(" | Message: ").append(m.$messageText).append(System.lineSeparator());
        }
        return $sb.toString();
    }

    // helper to build the same hash string for an index 
    private String createHashFromMessageIndex(int $globalIndex) {
        if ($globalIndex < $hashCount && $messageHashes[$globalIndex] != null) return $messageHashes[$globalIndex];
        
        if ($globalIndex < $idCount && $messageIDs[$globalIndex] != null) {
            String $id = $messageIDs[$globalIndex];
            String $firstTwo = $id.length() >= 2 ? $id.substring(0,2) : "00";
            int $count = $globalIndex;
            String $wordPart = "MSG";
            if ($globalIndex < $sentCount && $sentMessages[$globalIndex] != null) {
                String[] w = $sentMessages[$globalIndex].split("\\s+");
                String $first = w.length > 0 ? w[0] : "";
                String $last = w.length > 1 ? w[w.length - 1] : $first;
                $wordPart = ($first + $last).toUpperCase();
            }
            return ($firstTwo + ":" + $count + ":" + $wordPart).toUpperCase();
        }
        return "N/A";
    }

    // Clear arrays and JSON file 
    public void resetAll() {
        for (int i=0;i<CAP;i++) {
            $sentMessages[i] = null;
            $disregardedMessages[i] = null;
            $storedMessages[i] = null;
            $messageHashes[i] = null;
            $messageIDs[i] = null;
            $recipients[i] = null;
        }
        $sentCount = $disregardedCount = $storedCount = $hashCount = $idCount = 0;
        // clear JSON file content
        try (java.io.FileWriter $fw = new java.io.FileWriter($jsonFile, false)) {
            
        } catch (IOException ex) {}
    }
}
