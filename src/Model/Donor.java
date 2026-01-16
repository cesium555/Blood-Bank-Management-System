package Model;

/**
 *
 * @author mgras
 */
public class Donor {
    private int donorId;
    private String name;
    private int age;
    private String bloodGroup;
    private String address;
    private String event;

    public Donor(int donorId, String name, int age, String bloodGroup, String address, String event) {
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.event = event;
    }

    public Donor() {}

    public int getDonorId() { return donorId; }
    public void setDonorId(int donorId) { this.donorId = donorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }

    @Override
    public String toString() {
        return donorId + "|" + name + "|" + age + "|" + bloodGroup + "|" + address + "|" + event;
    }

    public static Donor parse(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            return new Donor(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]), parts[3], parts[4], parts[5]);
        }
        return null;
    }
}