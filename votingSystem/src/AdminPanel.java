import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class AdminPanel extends JFrame {
    private HashMap<String, User> users;

    public AdminPanel(HashMap<String, User> users) {
        this.users = users;

        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        JButton viewResultsBtn = new JButton("View Results");
        JButton addUserBtn = new JButton("Add User");
        JButton startAgainBtn = new JButton("Start Again");

        // Action for viewing results
        viewResultsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewResults();
            }
        });

        // Action for adding a new user
        addUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        // Action for starting the voting system again
        startAgainBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAndResetVotes();
            }
        });

        panel.add(viewResultsBtn);
        panel.add(addUserBtn);
        panel.add(startAgainBtn);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addUser() {
        String username = null;
        String password = null;
        String rollNo = null;
        String regNo = null;
    
        // Loop until valid username is provided
        while (true) {
            username = JOptionPane.showInputDialog("Enter the new user's username:");
            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty.");
                continue;  // Ask again
            }
            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.");
                continue;  // Ask again
            }
            break;  // Valid username
        }
    
        // Loop until valid password is provided
        while (true) {
            password = JOptionPane.showInputDialog("Enter the new user's password:");
            if (password == null || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.");
                continue;  // Ask again
            }
            break;  // Valid password
        }
    
        // Loop until valid roll number is provided
        while (true) {
            rollNo = JOptionPane.showInputDialog("Enter the new user's Roll No (1-45):");
            try {
                // Ensure roll number is a valid integer
                int rollNoInt = Integer.parseInt(rollNo);
                if (rollNoInt < 1 || rollNoInt > 45) {
                    JOptionPane.showMessageDialog(this, "Roll No. must be between 1 and 45.");
                    continue;  // Ask again
                }
    
                // Check if roll number already exists using a final variable
                String finalRollNo = rollNo;
                boolean rollNoExists = users.values().stream()
                    .anyMatch(user -> user.getRollNo() != null && user.getRollNo().equals(finalRollNo));
                if (rollNoExists) {
                    JOptionPane.showMessageDialog(this, "Roll No. already exists. Please choose a different Roll No.");
                    continue;  // Ask again
                }
    
                break;  // Valid roll number
            } catch (NumberFormatException e) {
                // Handle invalid number input
                JOptionPane.showMessageDialog(this, "Invalid Roll No. Please enter a valid number between 1 and 45.");
            } catch (Exception e) {
                // Print the error for debugging
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());  // Provide more info
            }
        }
    
        // Loop until valid registration number is provided
        while (true) {
            regNo = JOptionPane.showInputDialog("Enter the new user's Last 6 digits of Registration No:");
            if (regNo == null || regNo.length() != 6 || !regNo.matches("\\d{6}")) {
                JOptionPane.showMessageDialog(this, "Registration number must be exactly 6 digits.");
                continue;  // Ask again
            }
    
            // Check if registration number already exists using a final variable
            String finalRegNo = regNo;
            boolean regNoExists = users.values().stream()
                .anyMatch(user -> user.getRegistrationLast4() != null && user.getRegistrationLast4().equals(finalRegNo));
            if (regNoExists) {
                JOptionPane.showMessageDialog(this, "Registration No. already exists. Please choose a different Registration No.");
                continue;  // Ask again
            }
    
            break;  // Valid registration number
        }
    
        // If all inputs are valid, save the user
        users.put(username, new User(username, password, rollNo, regNo));
        saveUserToFile(username, password, rollNo, regNo);
        JOptionPane.showMessageDialog(this, "New user '" + username + "' added successfully!");
    }
    
    
    
    

    // Method to save user details to a file
    private void saveUserToFile(String username, String password, String rollNo, String regNo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + ":" + password + ":" + rollNo + ":" + regNo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // View the voting results
    private void viewResults() {
        int candidate1Votes = readVotesFromFile("Candidate1.txt");
        int candidate2Votes = readVotesFromFile("Candidate2.txt");
        int candidate3Votes = readVotesFromFile("Candidate3.txt");

        // Calculate total votes
        int totalVotes = candidate1Votes + candidate2Votes + candidate3Votes;

        // Determine the winner
        String winner = determineWinner(candidate1Votes, candidate2Votes, candidate3Votes);

        // Display the result in a message box
        JOptionPane.showMessageDialog(this,
                "Total Votes: " + totalVotes + "\n" +
                        "Candidate 1: " + candidate1Votes + " votes\n" +
                        "Candidate 2: " + candidate2Votes + " votes\n" +
                        "Candidate 3: " + candidate3Votes + " votes\n" +
                        "Winner: " + winner);
    }

    // Method to read the number of votes from a file
    private int readVotesFromFile(String fileName) {
        int votes = 0;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                votes = Integer.parseInt(reader.readLine());
                reader.close();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return votes;
    }

    // Method to determine the winner based on vote counts
    private String determineWinner(int candidate1Votes, int candidate2Votes, int candidate3Votes) {
        if (candidate1Votes > candidate2Votes && candidate1Votes > candidate3Votes) {
            return "Candidate 1";
        } else if (candidate2Votes > candidate1Votes && candidate2Votes > candidate3Votes) {
            return "Candidate 2";
        } else if (candidate3Votes > candidate1Votes && candidate3Votes > candidate2Votes) {
            return "Candidate 3";
        } else {
            return "It's a tie!";
        }
    }

    // Confirm the admin's identity before resetting votes
    private void confirmAndResetVotes() {
        String adminUsername = JOptionPane.showInputDialog("Enter Admin Username:");
        String adminPassword = JOptionPane.showInputDialog("Enter Admin Password:");

        if (adminUsername.equals("admin") && adminPassword.equals("adminpass")) {
            resetVotes();
            JOptionPane.showMessageDialog(this, "Votes have been reset successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials. Cannot reset votes.");
        }
    }

    // Reset all votes to 0 in the files
    private void resetVotes() {
        resetVoteFile("Candidate1.txt");
        resetVoteFile("Candidate2.txt");
        resetVoteFile("Candidate3.txt");

        // Reset each user's voting status (allow them to vote again)
        for (User user : users.values()) {
            user.setVoted(false, null);
        }
    }

    // Helper method to reset the vote count in the file
    private void resetVoteFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("0");  // Reset the vote count to 0
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
