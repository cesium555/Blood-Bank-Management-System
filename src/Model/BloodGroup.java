package Model;

/**
 *
 * @author mgras
 */
public class BloodGroup {
    private int bloodId;
    private String bloodGroup;
    private int quantity;

    public BloodGroup(int bloodId, String bloodGroup, int quantity) {
        this.bloodId = bloodId;
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
    }

    public BloodGroup() {
        this.bloodId = 0;
        this.bloodGroup = "";
        this.quantity = 0;
    }

    // Getters and Setters
    public int getBloodId() { return bloodId; }
    public void setBloodId(int bloodId) { this.bloodId = bloodId; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return bloodId + "|" + bloodGroup + "|" + quantity;
    }

    public static BloodGroup parse(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 3) {
            return new BloodGroup(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
        }
        return null;
    }
}