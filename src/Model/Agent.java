package Model;

public class Agent {
    private int agentId;
    private String name;
    private String organization;
    private String phone;
    private String password;  

    // Constructor
    public Agent(int agentId, String name, String organization, String phone, String password) {
        this.agentId = agentId;
        this.name = name;
        this.organization = organization;
        this.phone = phone;
        this.password = password;
    }

    // toString() - File format: id|name|org|phone|password
    @Override
    public String toString() {
        return agentId + "|" + name + "|" + organization + "|" + phone + "|" + password;
    }

    // parse() - File line → Agent object
    public static Agent parse(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                return new Agent(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    parts[4].trim()
                );
            }
        } catch (NumberFormatException e) {
            System.err.println("Parse error: " + line);
        }
        return null;
    }

    // Getters
    public int getAgentId() { return agentId; }
    public String getName() { return name; }
    public String getOrganization() { return organization; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
}
