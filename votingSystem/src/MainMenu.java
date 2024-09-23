import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MainMenu extends JFrame {
    private HashMap<String, User> users;

    public MainMenu(HashMap<String, User> users) {
        this.users = users;

        setTitle("Voting System - Main Menu");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons for Voter and Admin
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JButton voterBtn = new JButton("Voter");
        JButton adminBtn = new JButton("Admin");

        // When "Voter" is clicked, open the voter login form
        voterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm(users);  // Voter login form
                dispose();  // Close the main menu
            }
        });

        // When "Admin" is clicked, open the admin login form
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginForm(users);  // Admin login form
                dispose();  // Close the main menu
            }
        });

        panel.add(voterBtn);
        panel.add(adminBtn);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
