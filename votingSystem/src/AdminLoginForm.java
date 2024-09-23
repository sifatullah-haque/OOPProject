import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class AdminLoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private HashMap<String, User> users;

    public AdminLoginForm(HashMap<String, User> users) {
        this.users = users;
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create login form UI for admin
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("Admin Username:");
        JLabel passLabel = new JLabel("Admin Password:");
        
        userField = new JTextField();
        passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                login(username, password);
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void login(String username, String password) {
        if (username.equals("admin") && password.equals("adminpass")) {
            new AdminPanel(users);  // Proceed to admin panel
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials.");
        }
    }
}
