import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;



public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JTextField rollNoField;
    private JTextField regNoField;
    private HashMap<String, User> users;

    public LoginForm(HashMap<String, User> users) {
        this.users = users;
        setTitle("Voting System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create login form UI
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel rollNoLabel = new JLabel("Roll No:");
        JLabel regNoLabel = new JLabel("Last 6 digits of Registration No:");

        userField = new JTextField();
        passField = new JPasswordField();
        rollNoField = new JTextField();
        regNoField = new JTextField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                String rollNo = rollNoField.getText();
                String regNo = regNoField.getText();
                login(username, password, rollNo, regNo);
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(rollNoLabel);
        panel.add(rollNoField);
        panel.add(regNoLabel);
        panel.add(regNoField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void login(String username, String password, String rollNo, String regNo) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.checkPassword(password) && user.getRollNo().equals(rollNo) && user.getRegistrationLast4().equals(regNo)) {
                if (user.hasVoted()) {
                    JOptionPane.showMessageDialog(this, "You have already voted.");
                } else {
                    new VotingForm(user, users);  // Proceed to vote immediately
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "User not found.");
        }
    }
}
