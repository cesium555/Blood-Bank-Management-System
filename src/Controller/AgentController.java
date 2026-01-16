package Controller;

import Model.Agent;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AgentController {
    private static final String FILE_PATH = "data/agents.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
            System.out.println("📁 Agent data folder ready: " + Paths.get(FILE_PATH).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Agent folder error: " + e.getMessage());
        }
    }

    // REGISTER AGENT WITH AUTO-GENERATED PASSWORD + LOGIN CREDS
    public static String registerAgentWithLogin(String name, String organization, String phone) {
        System.out.println("🆕 Registering agent: " + name);
        
        // VALIDATION
        if (name == null || name.trim().length() < 3) {
            System.out.println("❌ Name too short");
            return null;
        }
        if (organization == null || organization.trim().isEmpty()) {
            System.out.println("❌ Organization required");
            return null;
        }
        if (phone == null || !phone.matches("^\\d{10}$")) {
            System.out.println("❌ Invalid phone");
            return null;
        }
        
        // CHECK DUPLICATES
        for (Agent agent : getAllAgents()) {
            if (agent.getPhone().equals(phone.trim())) {
                System.out.println("❌ Duplicate phone: " + phone);
                return "duplicate";
            }
        }
        
        // GENERATE PASSWORD + SAVE
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int newId = getNextId();
            String password = generateSecurePassword(phone);
            Agent agent = new Agent(newId, name.trim(), organization.trim(), phone.trim(), password);
            
            writer.write(agent.toString());
            writer.newLine();
            
            System.out.println("✅ Agent created: " + agent.toString());
            return "Login: " + phone + "\nPassword: " + password + "\nID: " + newId;
            
        } catch (IOException e) {
            System.err.println("❌ Save error: " + e.getMessage());
            return "error";
        }
    }

    // AGENT LOGIN VALIDATION
    public static Agent validateAgentLogin(String phone, String password) {
        for (Agent agent : getAllAgents()) {
            if (agent.getPhone().equals(phone) && agent.getPassword().equals(password)) {
                System.out.println("✅ Agent logged in: " + agent.getName());
                return agent;
            }
        }
        System.out.println("❌ Invalid agent login: " + phone);
        return null;
    }

    // GET ALL AGENTS
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
            System.err.println("❌ Read agents error: " + e.getMessage());
        }
        return agents;
    }

    // SEARCH BY NAME
    public static List<Agent> searchAgentByName(String name) {
        List<Agent> results = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) return results;
        
        for (Agent agent : getAllAgents()) {
            if (agent.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(agent);
            }
        }
        return results;
    }

    // PRIVATE HELPERS
    private static int getNextId() {
        int maxId = 0;
        for (Agent a : getAllAgents()) {
            if (a.getAgentId() > maxId) maxId = a.getAgentId();
        }
        return maxId + 1;
    }

    private static String generateSecurePassword(String phone) {
        // Phone "9841234567" → "9841AB4567X"
        return phone.substring(0, 4) + "AB" + phone.substring(6) + "X";
    }
}
