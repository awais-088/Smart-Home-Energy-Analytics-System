package gui;

import javax.swing.*;
import java.awt.*;

public class ForgotPasswordForm extends JFrame {

    JTextField txtEmail;
    JPasswordField txtPassword;

    JButton btnReset;
    JButton btnBack;

    public ForgotPasswordForm() {

        setTitle(
                "Reset Password"
        );

        setSize(
                600,
                400
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel panel =
                new JPanel();

        panel.setLayout(null);

        panel.setBackground(
                new Color(
                        25,
                        25,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "Reset Password"
                );

        title.setBounds(
                180,
                30,
                250,
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

        JLabel lblEmail =
                new JLabel(
                        "Email"
                );

        lblEmail.setBounds(
                80,
                120,
                100,
                25
        );

        lblEmail.setForeground(
                Color.WHITE
        );

        panel.add(lblEmail);

        txtEmail =
                new JTextField();

        txtEmail.setBounds(
                220,
                120,
                250,
                35
        );

        panel.add(txtEmail);

        JLabel lblPassword =
                new JLabel(
                        "New Password"
                );

        lblPassword.setBounds(
                80,
                190,
                120,
                25
        );

        lblPassword.setForeground(
                Color.WHITE
        );

        panel.add(lblPassword);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                220,
                190,
                250,
                35
        );

        panel.add(txtPassword);

        btnReset =
                new JButton(
                        "Reset Password"
                );

        btnReset.setBounds(
                220,
                260,
                250,
                40
        );

        panel.add(btnReset);

        btnBack =
                new JButton(
                        "Back"
                );

        btnBack.setBounds(
                220,
                310,
                250,
                40
        );

        panel.add(btnBack);

        btnReset.addActionListener(
                e -> resetPassword()
        );

        btnBack.addActionListener(
                e -> dispose()
        );

        add(panel);
    }

    private void resetPassword() {

        String email =
                txtEmail
                        .getText()
                        .trim();

        String password =
                String.valueOf(
                        txtPassword.getPassword()
                );

        if(email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Email"
            );

            return;
        }

        if(password.length() < 4) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 4 characters"
            );

            return;
        }

        dao.UserDAO dao =
                new dao.UserDAO();

        if(
                dao.resetPassword(
                        email,
                        password
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password Updated Successfully"
            );

            dispose();
        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Email Not Found"
            );
        }
    }
}