import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class VotingForm extends JFrame {
    private User user;
    private HashMap<String, User> users;
    private String[] candidates = {"Candidate 1", "Candidate 2", "Candidate 3"};

    public VotingForm(User user, HashMap<String, User> users) {
        this.user = user;
        this.users = users;

        // Check if the user has already voted
        if (hasUserVoted(user.getUsername())) {
            JOptionPane.showMessageDialog(this, "Murubbi Murubbi, uhuu uhuu uhuuu...");
            dispose(); // Close the window as the user is not allowed to vote again
            return;
        }

        setTitle("Voting System");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("Select a candidate:");

        JComboBox<String> candidateList = new JComboBox<>(candidates);
        JButton submitBtn = new JButton("Submit Vote");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCandidate = (String) candidateList.getSelectedItem();
                confirmVote(selectedCandidate);
            }
        });

        panel.add(label);
        panel.add(candidateList);
        panel.add(submitBtn);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Check if the user has already voted by looking up their username in the file
    private boolean hasUserVoted(String username) {
        try {
            File file = new File("votedUsers.txt");
            if (!file.exists()) {
                return false; // If the file doesn't exist, no user has voted yet
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(username)) {
                    reader.close();
                    return true; // The user has already voted
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void confirmVote(String candidate) {
        int confirm = JOptionPane.showConfirmDialog(this, user.getUsername() + " has voted for " + candidate + ". Confirm?");
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Vote confirmed!");

            // Save the user's vote to the file system
            saveVoteToFile(candidate);

            // Record that the user has voted
            recordUserVote(user.getUsername());

            dispose();  // Close the window after submission
        }
    }

    // Save vote to the file system based on candidate selected
    private void saveVoteToFile(String candidate) {
        String fileName = "";

        // Determine which file to update based on the selected candidate
        switch (candidate) {
            case "Candidate 1":
                fileName = "Candidate1.txt";
                break;
            case "Candidate 2":
                fileName = "Candidate2.txt";
                break;
            case "Candidate 3":
                fileName = "Candidate3.txt";
                break;
        }

        // Read the current vote count, increment by 1, and write it back to the file
        try {
            int currentVotes = 0;
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                currentVotes = Integer.parseInt(reader.readLine());
                reader.close();
            }

            // Increment the vote count by 1
            currentVotes += 1;

            // Write the updated vote count back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(String.valueOf(currentVotes));
            writer.close();

            System.out.println("Vote recorded for " + candidate + ": " + currentVotes + " votes");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Record the user as having voted by writing their username to votedUsers.txt
    private void recordUserVote(String username) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("votedUsers.txt", true)); // Append mode
            writer.write(username);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



