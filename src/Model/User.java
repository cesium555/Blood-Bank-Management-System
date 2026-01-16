
package Model;

/**
 *
 * @author mgras
 */

public class User {
    private int userId;
    private String email;
    private String password;
    private String userType;

    public User(int userId, String email, String password, String userType) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    @Override
    public String toString() {
        return userId + "|" + email + "|" + password + "|" + userType;
    }

    public static User parse(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 4) {
            return new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        }
        return null;
    }
}