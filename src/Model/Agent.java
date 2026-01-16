package Model;

/**
 *
 * @author mgras
 */
public class Agent {
    private int agentId;
    private String name;
    private String organization;
    private String phone;

    public Agent(int agentId, String name, String organization, String phone) {
        this.agentId = agentId;
        this.name = name;
        this.organization = organization;
        this.phone = phone;
    }

    public Agent() {}

    public int getAgentId() { return agentId; }
    public void setAgentId(int agentId) { this.agentId = agentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return agentId + "|" + name + "|" + organization + "|" + phone;
    }

    public static Agent parse(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 4) {
            return new Agent(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        }
        return null;
    }
}