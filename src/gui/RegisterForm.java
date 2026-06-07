package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    private JTextField txtName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private JButton btnRegister;
    private JButton btnBack;

    public RegisterForm() {

        setTitle("Register Account");

        setSize(600,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        panel.setBackground(
                new Color(30,30,30)
        );

        JLabel title =
                new JLabel(
                        "Create New Account"
                );

        title.setBounds(
                150,
                40,
                350,
                40
        );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        panel.add(title);

        JLabel lblName =
                new JLabel(
                        "Full Name"
                );

        lblName.setBounds(
                100,
                120,
                150,
                25
        );

        lblName.setForeground(
                Color.WHITE
        );

        panel.add(lblName);

        txtName =
                new JTextField();

        txtName.setBounds(
                100,
                150,
                350,
                40
        );

        panel.add(txtName);

        JLabel lblEmail =
                new JLabel(
                        "Email"
                );

        lblEmail.setBounds(
                100,
                210,
                150,
                25
        );

        lblEmail.setForeground(
                Color.WHITE
        );

        panel.add(lblEmail);

        txtEmail =
                new JTextField();

        txtEmail.setBounds(
                100,
                240,
                350,
                40
        );

        panel.add(txtEmail);

        JLabel lblPassword =
                new JLabel(
                        "Password"
                );

        lblPassword.setBounds(
                100,
                300,
                150,
                25
        );

        lblPassword.setForeground(
                Color.WHITE
        );

        panel.add(lblPassword);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                100,
                330,
                350,
                40
        );

        panel.add(txtPassword);

        btnRegister =
                new JButton(
                        "Create Account"
                );

        btnRegister.setBounds(
                100,
                410,
                350,
                45
        );

        btnRegister.setBackground(
                new Color(
                        0,
                        120,
                        215
                )
        );

        btnRegister.setForeground(
                Color.WHITE
        );

        btnRegister.setFocusPainted(false);

        panel.add(btnRegister);

        btnBack =
                new JButton(
                        "Back To Login"
                );

        btnBack.setBounds(
                100,
                470,
                350,
                45
        );

        btnBack.setBackground(
                new Color(
                        70,
                        70,
                        70
                )
        );

        btnBack.setForeground(
                Color.WHITE
        );

        btnBack.setFocusPainted(false);

        panel.add(btnBack);

        btnRegister.addActionListener(
                e -> registerUser()
        );

        btnBack.addActionListener(
                e -> {

                    dispose();

                    new LoginForm();

                }
        );

        add(panel);
    }

    private void registerUser() {

        String name =
                txtName.getText().trim();

        String email =
                txtEmail.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword()
                );

        if(name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name cannot be empty!"
            );

            return;
        }

        if(email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email cannot be empty!"
            );

            return;
        }

        if(!email.contains("@")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Email!"
            );

            return;
        }

        if(password.length() < 5) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 5 characters!"
            );

            return;
        }

        User user =
                new User();

        user.setFullName(name);

        user.setEmail(email);

        user.setPassword(password);

        UserDAO dao =
                new UserDAO();

        if(dao.registerUser(user)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!"
            );

            dispose();

            new LoginForm();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!"
            );
        }
    }
}