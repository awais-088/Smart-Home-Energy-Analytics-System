package gui;

import dao.TariffDAO;

import javax.swing.*;
import java.awt.*;

public class TariffForm extends JFrame {

    JLabel lblCurrentTariff;

    JTextField txtNewTariff;

    JButton btnUpdate;

    JButton btnBack;

    public TariffForm() {

        setTitle("Electricity Tariff");

        setSize(700,500);

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel panel =
                new JPanel();

        panel.setLayout(null);

        panel.setBackground(
                new Color(30,30,30)
        );

        JLabel title =
                new JLabel(
                        "Electricity Tariff Management"
                );

        title.setBounds(
                150,
                30,
                400,
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

        JLabel lblCurrent =
                new JLabel(
                        "Current Tariff (Rs/Unit)"
                );

        lblCurrent.setBounds(
                80,
                120,
                200,
                30
        );

        lblCurrent.setForeground(
                Color.WHITE
        );

        panel.add(lblCurrent);

        lblCurrentTariff =
                new JLabel();

        lblCurrentTariff.setBounds(
                300,
                120,
                200,
                30
        );

        lblCurrentTariff.setForeground(
                Color.GREEN
        );

        lblCurrentTariff.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        panel.add(lblCurrentTariff);

        JLabel lblNew =
                new JLabel(
                        "New Tariff"
                );

        lblNew.setBounds(
                80,
                200,
                200,
                30
        );

        lblNew.setForeground(
                Color.WHITE
        );

        panel.add(lblNew);

        txtNewTariff =
                new JTextField();

        txtNewTariff.setBounds(
                300,
                200,
                220,
                40
        );

        panel.add(txtNewTariff);

        btnUpdate =
                new JButton(
                        "Update Tariff"
                );

        btnUpdate.setBounds(
                300,
                280,
                220,
                45
        );

        btnUpdate.setBackground(
                new Color(
                        0,
                        150,
                        70
                )
        );

        btnUpdate.setForeground(
                Color.WHITE
        );

        panel.add(btnUpdate);

        btnBack =
                new JButton(
                        "Back To Dashboard"
                );

        btnBack.setBounds(
                300,
                350,
                220,
                45
        );

        panel.add(btnBack);

        btnUpdate.addActionListener(
                e -> updateTariff()
        );

        btnBack.addActionListener(
                e -> {

                    dispose();

                    new Dashboard();
                }
        );

        loadTariff();

        add(panel);
    }

    private void loadTariff() {

        TariffDAO dao =
                new TariffDAO();

        lblCurrentTariff.setText(
                "Rs " +
                        dao.getCurrentTariff()
        );
    }

    private void updateTariff() {

        String tariff =
                txtNewTariff.getText().trim();

        if(tariff.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter tariff value."
            );

            return;
        }

        double value;

        try {

            value =
                    Double.parseDouble(
                            tariff
                    );
        }
        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    "Enter numeric value."
            );

            return;
        }

        if(value < 1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Tariff cannot be less than 1."
            );

            return;
        }

        TariffDAO dao =
                new TariffDAO();

        if(
                dao.updateTariff(value)
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Tariff Updated Successfully!"
            );

            loadTariff();

            txtNewTariff.setText("");
        }
    }
}