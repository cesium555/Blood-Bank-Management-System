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

public class AgentController {

    private static final String FILE_PATH = "data/agents.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static boolean addAgent(String name, String organization, String phone) {
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            System.out.println("Error: Name min 3 characters");
            return false;
        }
        if (organization == null || organization.trim().isEmpty()) {
            System.out.println("Error: Organization required");
            return false;
        }
        if (phone == null || !isValidPhone(phone)) {
            System.out.println("Error: Phone must be 10 digits");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int newId = getNextId();
            Agent agent = new Agent(newId, name.trim(), organization.trim(), phone.trim());
            writer.write(agent.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Agent> getAllAgents() {
        List<Agent> agents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Agent agent = Agent.parse(line);
                if (agent != null) {
                    agents.add(agent);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return agents;
    }

    public static List<Agent> searchAgentByName(String name) {
        List<Agent> filtered = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) return filtered;

        for (Agent agent : getAllAgents()) {
            if (agent.getName().toLowerCase().contains(name.toLowerCase())) {
                filtered.add(agent);
            }
        }
        return filtered;
    }

    private static boolean isValidPhone(String phone) {
        return phone.matches("^\\d{10}$");
    }

    private static int getNextId() {
        int maxId = 0;
        List<Agent> list = getAllAgents();
        for (Agent a : list) {
            if (a.getAgentId() > maxId) maxId = a.getAgentId();
        }
        return maxId + 1;
    }
}