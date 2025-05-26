
package com.mycompany.ivyleaguechat;

import java.util.Scanner;

public class UserDetails {
    private boolean loginSuccess = false;

    public boolean isLoginSuccess() {  // Add this getter method
        return loginSuccess;
    }
    
    public void setLoginSuccess(boolean status){
        this.loginSuccess = status;
    }

    public boolean checkUsername(String username) {
        return username != null && username.length() == 5 && username.contains("_");
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        String specialChars = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.contains(String.valueOf(c))) hasSpecial = true;

            if (hasUpper && hasDigit && hasSpecial) return true;
        }

        return false;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() != 12 || !phoneNumber.startsWith("+27")) return false;

        String numberPart = phoneNumber.substring(3);
        for (char c : numberPart.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return true;
    }

    public String registerUser(String username, String password, String phoneNumber) {
        if (!checkUsername(username)) {
            return "Username must include an underscore and be exactly 5 characters.\n";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password must be at least 8 characters long and include an uppercase letter, a digit, and a special character.\n";
        }

        if (!checkPhoneNumber(phoneNumber)) {
            return "Phone number must start with +27 and be exactly 12 characters long (e.g., +27831234567).\n";
        }

        return "Registration successful!";
    }

    public void loginUser(String registeredUsername, String registeredPassword, Scanner inputScanner) {
        int attemptsLeft = 3;

        while (attemptsLeft > 0) {
            System.out.print("Enter Username: ");
            String inputUsername = inputScanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String inputPassword = inputScanner.nextLine().trim();

            if (inputUsername.equals(registeredUsername) && inputPassword.equals(registeredPassword)) {
                loginSuccess = true;
                break; // exit loop after successful login
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.println("Incorrect credentials. Attempts left: " + attemptsLeft);
                }
            }
        }

        if (!loginSuccess) {
            System.out.println("Too many failed login attempts. Goodbye.");
        }
    }

    public String loginStatus(String firstName) 
    {
        if (loginSuccess) {
            return """
                   Login Successful!
                   
                   Welcome back, It's good to see you """ + firstName + "!";
        }   
        else 
        {
        return "Login Failed.";
        }
    }
}