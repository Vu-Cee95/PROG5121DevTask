package com.mycompany.ivyleaguechat;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageInfo {
    private static final String MESSAGE_FILE = "messages.json";
    private static final List<MessageInfo> messages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    private String recipientCell;
    private String messageContent;
    private String messageId;
    private String messageHash;
    private String timestamp;

    public MessageInfo() {} // Default constructor

    public MessageInfo(String recipientCell, String messageContent) {
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageId = generateMessageId();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String generateMessageId() {
        Random random = new Random();
        long id = 1000000000L + (long)(random.nextDouble() * 899999999L);
        return String.valueOf(id).substring(0, 10);
    }

    public boolean checkMessageID() {
        return this.messageId != null && this.messageId.length() == 10;
    }

    public int checkRecipientCell() {
        if (this.recipientCell == null) return 0;

        if (this.recipientCell.length() != 12 || !this.recipientCell.startsWith("+27")) {
            return 0;
        }

        String numberPart = this.recipientCell.substring(3);
        for (char c : numberPart.toCharArray()) {
            if (!Character.isDigit(c)) return 0;
        }

        return 1;
    }

    public String createMessageHash() {
        if (this.messageContent == null || this.messageContent.trim().isEmpty()) {
            return "00:0:EMPTY";
        }

        String[] words = this.messageContent.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        String idPrefix = this.messageId.length() >= 2 ? this.messageId.substring(0, 2) : "00";

        return idPrefix + ":" + totalMessagesSent + ":" +
               firstWord.toUpperCase() + lastWord.toUpperCase();
    }

    public String sendMessage() {
        if (this.messageContent == null || this.messageContent.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }

        if (checkRecipientCell() == 0) {
            return "Invalid recipient number. Must start with +27 and be 12 digits long.";
        }

        this.messageHash = createMessageHash();

        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int choice = JOptionPane.showOptionDialog(null,
            "What would you like to do with this message?",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        switch (choice) {
            case 0: // Send
                messages.add(this);
                totalMessagesSent++;
                JOptionPane.showMessageDialog(null,
                    "Message sent!\n\n" +
                    "Message ID: " + this.messageId + "\n" +
                    "Recipient: " + this.recipientCell + "\n" +
                    "Hash: " + this.messageHash + "\n" +
                    "Timestamp: " + this.timestamp + "\n\n" +
                    "====================================================\n\n" +

                    "Message: " + "\n" + this.messageContent + "\n" +

                    "\n===================================================="
                );
                return "Message sent!";

            case 1: // Store
                return storeMessage();

            case 2: // Disregard
                return "Message disregarded.";

            default:
                return "Message processing cancelled.";
        }
    }

    public static String printMessages() {
        if (messages.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (MessageInfo msg : messages) {
            sb.append(msg.toString()).append("\n");
        }
        return sb.toString();
    }
    
   public int returnTotalMessagesSent() {
    return totalMessagesSent;
    }
    

    public String storeMessage() {
        JSONObject messageJson = new JSONObject();
        messageJson.put("id", this.messageId);
        messageJson.put("recipient", this.recipientCell);
        messageJson.put("message", this.messageContent);
        messageJson.put("hash", this.messageHash);
        messageJson.put("timestamp", this.timestamp);

        try {
            JSONArray jsonArray;
            if (Files.exists(Paths.get(MESSAGE_FILE))) {
                String content = new String(Files.readAllBytes(Paths.get(MESSAGE_FILE)));
                jsonArray = new JSONArray(content.isEmpty() ? "[]" : content);
            } else {
                jsonArray = new JSONArray();
            }

            jsonArray.put(messageJson);

            try (FileWriter file = new FileWriter(MESSAGE_FILE)) {
                file.write(jsonArray.toString(4));
            }

            return "Message stored successfully!";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }
}
