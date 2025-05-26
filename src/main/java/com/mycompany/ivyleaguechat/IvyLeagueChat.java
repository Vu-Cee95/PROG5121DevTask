package com.mycompany.ivyleaguechat;

import javax.swing.JOptionPane;

public class IvyLeagueChat {
    public static void main(String[] args) {
        UserDetails regForm = new UserDetails();
        MessageInfo messageInfo = new MessageInfo();

        String registeredUsername = "", registeredPassword = "";
        String firstName = "", lastName = "", phoneNumber = "";

        while (true) {
            int welcome = JOptionPane.showConfirmDialog(null,
                "WELCOME TO IVY LEAGUE CHAT!\n\nWould you like to register an account?",
                "Welcome",
                JOptionPane.YES_NO_OPTION);

            if (welcome != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break;
            }

            // Validate First Name
            do {
                firstName = JOptionPane.showInputDialog("Enter your First Name (or type 'quit' to cancel):");
                if (firstName == null || firstName.equalsIgnoreCase("quit")) break;
                if (firstName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "First name can't be empty or spaces only. Please enter a valid name.");
                }
            } while (firstName.trim().isEmpty());

            if (firstName == null || firstName.equalsIgnoreCase("quit")) continue;

            // Validate Last Name
            do {
                lastName = JOptionPane.showInputDialog("Enter your Last Name (or type 'quit' to cancel):");
                if (lastName == null || lastName.equalsIgnoreCase("quit")) break;
                if (lastName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Last name can't be empty or spaces only. Please enter a valid name.");
                }
            } while (lastName.trim().isEmpty());

            if (lastName == null || lastName.equalsIgnoreCase("quit")) continue;

            String username;
            while (true) {
                username = JOptionPane.showInputDialog("Create a Username (must include an underscore and be 5 characters):");
                if (username == null || username.equalsIgnoreCase("quit")) break;
                if (regForm.checkUsername(username)) break;
                JOptionPane.showMessageDialog(null, "Invalid username. Must contain '_' and be exactly 5 characters.");
            }
            if (username == null || username.equalsIgnoreCase("quit")) continue;

            String password;
            while (true) {
                password = JOptionPane.showInputDialog("Create a Password (min 8 chars, incl uppercase, digit, special char):");
                if (password == null || password.equalsIgnoreCase("quit")) break;
                if (regForm.checkPasswordComplexity(password)) break;
                JOptionPane.showMessageDialog(null, "Invalid password. Must be 8+ chars with uppercase, digit, and special char.");
            }
            if (password == null || password.equalsIgnoreCase("quit")) continue;

            while (true) {
                phoneNumber = JOptionPane.showInputDialog("Enter your Phone Number (e.g., +27831234567):");
                if (phoneNumber == null || phoneNumber.equalsIgnoreCase("quit")) break;
                if (regForm.checkPhoneNumber(phoneNumber)) break;
                JOptionPane.showMessageDialog(null, "Invalid phone number. Must start with +27 and be 12 characters.");
            }
            if (phoneNumber == null || phoneNumber.equalsIgnoreCase("quit")) continue;

            String registrationStatus = regForm.registerUser(username, password, phoneNumber);
            JOptionPane.showMessageDialog(null, registrationStatus);

            if (registrationStatus.equals("Registration successful!")) {
                registeredUsername = username;
                registeredPassword = password;

                JOptionPane.showMessageDialog(null,
                    "ACCOUNT DETAILS:\n\n" +
                    "First Name: " + firstName + "\n" +
                    "Last Name: " + lastName + "\n" +
                    "Username: " + username + "\n" +
                    "Password: " + "*".repeat(password.length()) + "\n" +
                    "Phone Number: " + phoneNumber + "\n" +
                    "************************************");

                int loginPrompt = JOptionPane.showConfirmDialog(null,
                    "Would you like to login now?", "Login", JOptionPane.YES_NO_OPTION);

                if (loginPrompt == JOptionPane.YES_OPTION) {
                    int attemptsLeft = 3;
                    while (attemptsLeft > 0) {
                        String enteredUser, enteredPass;

                        // Validate Username
                        do {
                            enteredUser = JOptionPane.showInputDialog("Enter Username:");
                            if (enteredUser == null) break;
                            if (enteredUser.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Username cannot be empty. Please enter a valid username.");
                            }
                        } while (enteredUser.trim().isEmpty());

                        // Validate Password
                        do {
                            enteredPass = JOptionPane.showInputDialog("Enter Password:");
                            if (enteredPass == null) break;
                            if (enteredPass.trim().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Password cannot be empty. Please enter a valid password.");
                            }
                        } while (enteredPass.trim().isEmpty());

                        if (enteredUser == null || enteredPass == null) continue;

                        if (enteredUser.equals(registeredUsername) && enteredPass.equals(registeredPassword)) {
                            regForm.setLoginSuccess (true);
                            JOptionPane.showMessageDialog(null, regForm.loginStatus(firstName));

                            try {
                                String msgCountStr = JOptionPane.showInputDialog("How many messages would you like to send?");
                                if (msgCountStr == null) break;
                                int messageCount = Integer.parseInt(msgCountStr);

                                int messagesSent = 0;
                                boolean stop = false;

                                while (!stop) {
                                    String option = JOptionPane.showInputDialog(
                                        "Welcome to IVY LEAGUE CHAT!!\n" +
                                        "1) Send Messages\n" +
                                        "2) Show recently sent messages\n" +
                                        "3) Quit");

                                    if (option == null) break;

                                    switch (option) {
                                        case "1":
                                            if (messagesSent < messageCount) {
                                                String message;
                                                do {
                                                    message = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
                                                    if (message == null) break;

                                                    if (message.trim().isEmpty()) {
                                                        JOptionPane.showMessageDialog(null, "Please enter a message.");
                                                    } else if (message.length() > 250) {
                                                        JOptionPane.showMessageDialog(null, "Message is too long. Limit is 250 characters.");
                                                    } else {
                                                        MessageInfo newMsg = new MessageInfo(phoneNumber, message);
                                                        String sendStatus = newMsg.sendMessage();
                                                        if (sendStatus.equals("Message sent!")) messagesSent++;
                                                        break;
                                                    }
                                                } while (true);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "You've reached your message limit.");
                                            }
                                            break;

                                        case "2":
                                            JOptionPane.showMessageDialog(null, "Coming soon.");
                                            break;

                                        case "3":
                                            stop = true;
                                            JOptionPane.showMessageDialog(null, "Goodbye!");
                                            break;

                                        default:
                                            JOptionPane.showMessageDialog(null, "Invalid option. Enter 1, 2, or 3.");
                                    }
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Invalid number entered.");
                            }
                            break;
                        } else {
                            attemptsLeft--;
                            if (attemptsLeft > 0) {
                                JOptionPane.showMessageDialog(null,
                                    "Incorrect credentials. Attempts left: " + attemptsLeft);
                            } else {
                                JOptionPane.showMessageDialog(null, "Too many failed login attempts. Goodbye.");
                                break;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Thank you for visiting Ivy League Chat.");
                }
            }
        }
    }
}
