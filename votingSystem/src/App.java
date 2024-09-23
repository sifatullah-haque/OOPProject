import java.util.HashMap;

import java.io.*;

public class App {
    public static void main(String[] args) {
        HashMap<String, User> users = loadUsersFromFile();  // Load users from file

        addDummyUsers(users);  // Add dummy users if they don't exist

        new MainMenu(users);  // Open the main menu (Voter | Admin)
    }

    // Method to load users from the file
    private static HashMap<String, User> loadUsersFromFile() {
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(":");
                if (userInfo.length == 4) {
                    String username = userInfo[0];
                    String password = userInfo[1];
                    String rollNo = userInfo[2];
                    String regNo = userInfo[3];
                    users.put(username, new User(username, password, rollNo, regNo));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add default admin user (with no roll number or registration)
        users.put("admin", new User("admin", "adminpass", null, null));

        return users;
    }

    // Method to add dummy users to the system
    private static void addDummyUsers(HashMap<String, User> users) {
        // Check if dummy users already exist to avoid duplication
        if (!users.containsKey("user1")) {
            users.put("user1", new User("user1", "password1", "5", "123456"));
            saveUserToFile("user1", "password1", "5", "123456");
        }
        if (!users.containsKey("user2")) {
            users.put("user2", new User("user2", "password2", "10", "654321"));
            saveUserToFile("user2", "password2", "10", "654321");
        }
        if (!users.containsKey("user3")) {
            users.put("user3", new User("user3", "password3", "20", "112233"));
            saveUserToFile("user3", "password3", "20", "112233");
        }
    }

    // Method to save a user to the file
    private static void saveUserToFile(String username, String password, String rollNo, String regNo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + ":" + password + ":" + rollNo + ":" + regNo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
