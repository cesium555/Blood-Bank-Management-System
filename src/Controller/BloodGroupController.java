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

public class BloodGroupController {

    private static final String FILE_PATH = "data/bloodgroups.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static boolean addBloodGroup(String bloodGroup, String quantityStr) {
        if (bloodGroup == null || bloodGroup.trim().isEmpty()) {
            System.out.println("Error: Blood group required");
            return false;
        }
        if (!isValidBloodGroup(bloodGroup)) {
            System.out.println("Error: Invalid blood group");
            return false;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < 0) {
                System.out.println("Error: Quantity cannot be negative");
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                int newId = getNextId();
                BloodGroup bg = new BloodGroup(newId, bloodGroup.trim(), quantity);
                writer.write(bg.toString());
                writer.newLine();
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid quantity");
            return false;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static List<BloodGroup> getAllBloodGroups() {
        List<BloodGroup> bloodGroups = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                BloodGroup bg = BloodGroup.parse(line);
                if (bg != null) {
                    bloodGroups.add(bg);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return bloodGroups;
    }

    public static int getTotalBloodBags() {
        return getAllBloodGroups().stream().mapToInt(BloodGroup::getQuantity).sum();
    }

    private static boolean isValidBloodGroup(String bloodGroup) {
        String[] valid = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        for (String v : valid) {
            if (bloodGroup.equals(v)) return true;
        }
        return false;
    }

    private static int getNextId() {
        int maxId = 0;
        List<BloodGroup> list = getAllBloodGroups();
        for (BloodGroup bg : list) {
            if (bg.getBloodId() > maxId) maxId = bg.getBloodId();
        }
        return maxId + 1;
    }
}