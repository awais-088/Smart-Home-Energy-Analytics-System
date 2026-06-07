package gui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnRegister;

    public LoginForm() {

        setTitle("Smart Home Energy Analytics System");

        setSize(600, 580);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(new Color(30, 30, 30));

        mainPanel.setLayout(null);

        JLabel title = new JLabel(
                "Smart Home Energy Analytics System"
        );

        title.setBounds(
                50,
                40,
                500,
                40
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(title);

        JLabel subtitle = new JLabel(
                "Welcome Back"
        );

        subtitle.setBounds(
                220,
                90,
                200,
                30
        );

        subtitle.setForeground(
                Color.LIGHT_GRAY
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18
                )
        );

        mainPanel.add(subtitle);

        JLabel emailLabel = new JLabel(
                "Email"
        );

        emailLabel.setBounds(
                120,
                150,
                100,
                25
        );

        emailLabel.setForeground(
                Color.WHITE
        );

        emailLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        mainPanel.add(emailLabel);

        txtEmail = new JTextField();

        txtEmail.setBounds(
                120,
                180,
                350,
                40
        );

        txtEmail.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        mainPanel.add(txtEmail);

        JLabel passwordLabel = new JLabel(
                "Password"
        );

        passwordLabel.setBounds(
                120,
                235,
                100,
                25
        );

        passwordLabel.setForeground(
                Color.WHITE
        );

        passwordLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        mainPanel.add(passwordLabel);

        txtPassword = new JPasswordField();

        txtPassword.setBounds(
                120,
                265,
                350,
                40
        );

        txtPassword.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        mainPanel.add(txtPassword);

        btnLogin = new JButton(
                "Login"
        );

        btnLogin.setBounds(
                120,
                330,
                350,
                45
        );

        btnLogin.setBackground(
                new Color(
                        0,
                        120,
                        215
                )
        );

        btnLogin.setForeground(
                Color.WHITE
        );

        btnLogin.setFocusPainted(false);

        btnLogin.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        mainPanel.add(btnLogin);

        btnRegister = new JButton(
                "Register"
        );

        btnRegister.setBounds(
                120,
                390,
                350,
                45
        );

        btnRegister.setBackground(
                new Color(
                        60,
                        60,
                        60
                )
        );

        btnRegister.setForeground(
                Color.WHITE
        );

        btnRegister.setFocusPainted(false);

        btnRegister.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        mainPanel.add(btnRegister);
        JButton btnForgotPassword =
                new JButton(
                        "Forgot Password?"
                );

        btnForgotPassword.setBounds(
                120,
                445,
                350,
                30
        );

        btnForgotPassword.setBackground(
                new Color(
                        70,
                        70,
                        70
                )
        );

        btnForgotPassword.setForeground(
                Color.WHITE
        );

        mainPanel.add(btnForgotPassword);

        btnForgotPassword.addActionListener(
                e -> new ForgotPasswordForm()
        );
        btnLogin.addActionListener(
                e -> login()
        );

        btnRegister.addActionListener(
                e -> {

                    dispose();

                    new RegisterForm();

                }
        );

        add(mainPanel);
    }

    private void login() {

        String email =
                txtEmail.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword()
                );

        if(email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Email cannot be empty!"
            );

            return;
        }

        if(password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password cannot be empty!"
            );

            return;
        }

        UserDAO dao =
                new UserDAO();

        if(dao.loginUser(
                email,
                password
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!"
            );

            dispose();

            new Dashboard();

        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Email or Password!"
            );
        }
    }
}