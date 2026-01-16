package Controller;

/**
 * @author mgras
 */

import Model.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LoginController {

    private static final String FILE_PATH = "data/users.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
            if (!Files.exists(Paths.get(FILE_PATH))) {
                try (BufferedWriter w = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    w.write("1|admin@ragat.com|admin123|Admin\n");
                    w.write("2|user@ragat.com|user123|User\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // to validate email format
    private static boolean isValidEmail(String email) {
        // Check basic email format: something@something.something
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // to validate password strength
    private static boolean isValidPassword(String password) {
        // Password requirements:
        // - Minimum 6 characters
        // - At least one letter
        // - At least one number (optional but recommended)
        if (password == null || password.length() < 6) {
            return false;
        }
        
        // Check for at least one letter
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        
        return hasLetter;
    }

    // Login with comprehensive validation
    public static User validateLogin(String email, String password) {
        
        // Validate email format
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Error: Email is required");
            return null;
        }
        
        email = email.trim();
        
        if (!isValidEmail(email)) {
            System.out.println("Error: Invalid email format (example: user@ragat.com)");
            return null;
        }

        // Validate password format
        if (password == null || password.isEmpty()) {
            System.out.println("Error: Password is required");
            return null;
        }

        if (!isValidPassword(password)) {
            System.out.println("Error: Password must be at least 6 characters");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    System.out.println(" Login successful for: " + email);
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("Error: Invalid email or password");
        return null;
    }

    public static String validateLoginWithError(String email, String password) {
        
        // Validate email format
        if (email == null || email.trim().isEmpty()) {
            return "Error: Email is required";
        }
        
        email = email.trim();
        
        if (!isValidEmail(email)) {
            return "Error: Invalid email format ";
        }

        // Validate password format
        if (password == null || password.isEmpty()) {
            return "Error: Password is required";
        }

        if (!isValidPassword(password)) {
            return "Error: Password must be at least 6 characters";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return null; // Success (null = no error)
                }
            }
        } catch (IOException e) {
            return "Error: Database error - " + e.getMessage();
        }

        return "Error: Invalid email or password";
    }

    public static boolean handleLogin(String email, String password, javax.swing.JFrame loginFrame) {
        String error = validateLoginWithError(email, password);
        
        if (error == null) {
            
            User user = validateLogin(email, password);
            
            if (user != null) {
                if ("Admin".equals(user.getUserType())) {
                    new View.AdminMainFrame(user.getEmail()).setVisible(true);
                } else {
                    new View.UserMainFrame().setVisible(true);
                }
                loginFrame.dispose();
                return true;
            }
        }
        
        return false;
    }

    public static String getLoginError(String email, String password) {
        return validateLoginWithError(email, password);
    }
    
}
