package gui;

import dao.ApplianceDAO;
import dao.UsageDAO;
import model.Appliance;
import model.UsageRecord;
import dao.TariffDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
public class UsageForm extends JFrame {

    private JComboBox<Appliance> cmbAppliance;

    private JTextField txtDate;

    private JTextField txtHours;

    private JTextField txtUnits;

    private JTextField txtCost;

    private JButton btnCalculate;

    private JButton btnSave;

    private JButton btnBack;

    private JTable usageTable;

    private DefaultTableModel tableModel;

    public UsageForm() {

        setTitle("Usage Tracking");

        setSize(1200,750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel panel = new JPanel();

        panel.setLayout(null);

        panel.setBackground(
                new Color(30, 30, 30)
        );

        JLabel title =
                new JLabel(
                        "Usage Tracking"
                );

        title.setBounds(
                450,
                20,
                300,
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
        JLabel lblAppliance =
                new JLabel(
                        "Appliance"
                );

        lblAppliance.setForeground(
                Color.WHITE
        );

        lblAppliance.setBounds(
                80,
                100,
                150,
                25
        );

        panel.add(lblAppliance);

        cmbAppliance =
                new JComboBox<>();

        cmbAppliance.setBounds(
                250,
                100,
                300,
                40
        );

        panel.add(cmbAppliance);

        JLabel lblDate =
                new JLabel(
                        "Date"
                );

        lblDate.setForeground(
                Color.WHITE
        );

        lblDate.setBounds(
                80,
                160,
                150,
                25
        );

        panel.add(lblDate);

        txtDate =
                new JTextField(
                        LocalDate.now().toString()
                );

        txtDate.setBounds(
                250,
                160,
                300,
                40
        );

        panel.add(txtDate);

        JLabel lblHours =
                new JLabel(
                        "Hours Used"
                );

        lblHours.setForeground(
                Color.WHITE
        );

        lblHours.setBounds(
                80,
                220,
                150,
                25
        );

        panel.add(lblHours);

        txtHours =
                new JTextField();

        txtHours.setBounds(
                250,
                220,
                300,
                40
        );

        panel.add(txtHours);

        JLabel lblUnits =
                new JLabel(
                        "Estimated Units"
                );

        lblUnits.setForeground(
                Color.WHITE
        );

        lblUnits.setBounds(
                80,
                280,
                150,
                25
        );

        panel.add(lblUnits);

        txtUnits =
                new JTextField();

        txtUnits.setEditable(false);

        txtUnits.setBounds(
                250,
                280,
                300,
                40
        );

        panel.add(txtUnits);

        JLabel lblCost =
                new JLabel(
                        "Estimated Cost"
                );

        lblCost.setForeground(
                Color.WHITE
        );

        lblCost.setBounds(
                80,
                340,
                150,
                25
        );

        panel.add(lblCost);

        txtCost =
                new JTextField();

        txtCost.setEditable(false);

        txtCost.setBounds(
                250,
                340,
                300,
                40
        );

        panel.add(txtCost);

        btnCalculate =
                new JButton(
                        "Calculate"
                );


        btnCalculate.setBounds(
                650,
                160,
                180,
                45
        );

        btnCalculate.setBackground(
                new Color(
                        120,
                        80,
                        220
                )
        );

        btnCalculate.setForeground(
                Color.WHITE
        );

        panel.add(btnCalculate);

        btnSave =
                new JButton(
                        "Save Record"
                );

        btnSave.setBounds(
                650,
                230,
                180,
                45
        );

        btnSave.setBackground(
                new Color(
                        0,
                        150,
                        80
                )
        );

        btnSave.setForeground(
                Color.WHITE
        );

        panel.add(btnSave);

        btnBack =
                new JButton(
                        "Back To Dashboard"
                );

        btnBack.setBounds(
                650,
                300,
                180,
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

        panel.add(btnBack);

        tableModel =
                new DefaultTableModel();

        tableModel.addColumn("Appliance");
        tableModel.addColumn("Date");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Units");
        tableModel.addColumn("Cost");

        usageTable =
                new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(
                        usageTable
                );

        scrollPane.setBounds(
                50,
                450,
                1050,
                200
        );

        panel.add(scrollPane);
        loadAppliances();
        loadUsageHistory();

        btnCalculate.addActionListener(
                e -> calculateUsage()
        );

        btnSave.addActionListener(
                e -> saveUsage()
        );

        btnBack.addActionListener(
                e -> {

                    dispose();

                    new Dashboard();

                }
        );
        add(panel);
    }
    private void loadAppliances() {

        ApplianceDAO dao =
                new ApplianceDAO();

        ArrayList<Appliance> list =
                dao.getApplianceList();

        for(
                Appliance appliance :
                list
        ) {

            cmbAppliance.addItem(
                    appliance
            );
        }
    }
    private void calculateUsage() {

        if(
                cmbAppliance.getSelectedItem()
                        == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select appliance."
            );

            return;
        }

        String hoursText =
                txtHours.getText().trim();

        if(hoursText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter hours used."
            );

            return;
        }

        double hours;

        try {

            hours =
                    Double.parseDouble(
                            hoursText
                    );

        }
        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    "Hours must be numeric."
            );

            return;
        }

        if(
                hours <= 0
                        ||
                        hours > 24
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Hours must be between 1 and 24."
            );

            return;
        }

        Appliance appliance =
                (Appliance)
                        cmbAppliance
                                .getSelectedItem();

        double power =
                appliance.getPowerRating();

        double units =
                (power * hours)
                        / 1000;

        TariffDAO tariffDAO =
                new TariffDAO();

        double tariff =
                tariffDAO.getCurrentTariff();

        double cost =
                units * tariff;

        txtUnits.setText(
                String.format(
                        "%.2f",
                        units
                )
        );

        txtCost.setText(
                String.format(
                        "%.2f",
                        cost
                )
        );
    }

    private void saveUsage() {

        if(
                txtUnits.getText()
                        .isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please calculate first."
            );

            return;
        }

        Appliance appliance =
                (Appliance)
                        cmbAppliance
                                .getSelectedItem();

        UsageRecord record =
                new UsageRecord();

        record.setApplianceId(
                appliance.getApplianceId()
        );

        record.setUsageDate(
                Date.valueOf(
                        txtDate.getText()
                )
        );

        record.setHoursUsed(
                Double.parseDouble(
                        txtHours.getText()
                )
        );

        record.setUnitsConsumed(
                Double.parseDouble(
                        txtUnits.getText()
                )
        );

        record.setCost(
                Double.parseDouble(
                        txtCost.getText()
                )
        );

        UsageDAO dao =
                new UsageDAO();

        if(
                dao.addUsageRecord(
                        record
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usage Record Saved!"
            );

            tableModel.addRow(
                    new Object[]{

                            appliance
                                    .getApplianceName(),

                            record
                                    .getHoursUsed(),

                            record
                                    .getUnitsConsumed(),

                            record
                                    .getCost()
                    }
            );

            txtHours.setText("");

            txtUnits.setText("");

            txtCost.setText("");
        }
        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Save Failed."
            );
        }
    }
    private void loadUsageHistory() {

        tableModel.setRowCount(0);

        UsageDAO dao =
                new UsageDAO();

        for(
                Object[] row :
                dao.getUsageHistory()
        ) {

            tableModel.addRow(row);

        }
    }
}