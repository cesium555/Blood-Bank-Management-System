/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
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

    public static User validateLogin(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Error: Email and password required");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static boolean registerUser(String email, String password, String confirmPassword) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Error: Email and password required");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            System.out.println("Error: Passwords don't match");
            return false;
        }

        // Check if email exists
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null && user.getEmail().equals(email)) {
                    System.out.println("Error: Email already registered");
                    return false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Add new user
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int newId = getNextId();
            User newUser = new User(newId, email, password, "User");
            writer.write(newUser.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    private static int getNextId() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.parse(line);
                if (user != null && user.getUserId() > maxId) {
                    maxId = user.getUserId();
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return maxId + 1;
    }
}