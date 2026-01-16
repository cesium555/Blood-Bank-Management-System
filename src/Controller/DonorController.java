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

public class DonorController {

    private static final String FILE_PATH = "data/donors.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static boolean addDonor(String name, String ageStr, String bloodGroup, String address, String event) {
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            System.out.println("Error: Name min 3 characters");
            return false;
        }

        try {
            int age = Integer.parseInt(ageStr);
            if (age < 18 || age > 65) {
                System.out.println("Error: Age must be 18-65");
                return false;
            }

            if (bloodGroup == null || !isValidBloodGroup(bloodGroup)) {
                System.out.println("Error: Invalid blood group");
                return false;
            }

            if (address == null || address.trim().isEmpty() || event == null || event.trim().isEmpty()) {
                System.out.println("Error: Address and event required");
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                int newId = getNextId();
                Donor donor = new Donor(newId, name.trim(), age, bloodGroup.trim(), address.trim(), event.trim());
                writer.write(donor.toString());
                writer.newLine();
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid age");
            return false;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Donor donor = Donor.parse(line);
                if (donor != null) {
                    donors.add(donor);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return donors;
    }

    public static int getTotalDonors() {
        return getAllDonors().size();
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
        List<Donor> list = getAllDonors();
        for (Donor d : list) {
            if (d.getDonorId() > maxId) maxId = d.getDonorId();
        }
        return maxId + 1;
    }
}
