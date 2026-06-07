package gui;

import dao.ApplianceDAO;
import model.Appliance;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.*;
import java.awt.*;

public class ApplianceForm extends JFrame {

    JTextField txtName;
    JTextField txtPower;
    JButton btnBack;
    JComboBox<String> cmbCategory;
    JButton btnAdd;
    JButton btnUpdate;
    JButton btnDelete;
    JButton btnRefresh;
    JButton btnSearch;
    JTextField txtSearch;
    JTable applianceTable;
    DefaultTableModel tableModel;
    int selectedApplianceId = -1;

    public ApplianceForm() {
        setTitle("Appliance Management");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        createUI();
        setVisible(true);
    }

    private void createUI() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Appliance Management");
        title.setBounds(250, 20, 350, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(title);

        JLabel lblName = new JLabel("Appliance Name");
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(80, 100, 150, 25);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(250, 100, 300, 40);
        panel.add(txtName);

        JLabel hintName = new JLabel("Example: Air Conditioner");
        hintName.setForeground(Color.LIGHT_GRAY);
        hintName.setBounds(250, 140, 300, 20);
        panel.add(hintName);

        JLabel lblPower = new JLabel("Power Rating");
        lblPower.setForeground(Color.WHITE);
        lblPower.setBounds(80, 180, 150, 25);
        panel.add(lblPower);

        txtPower = new JTextField();
        txtPower.setBounds(250, 180, 300, 40);
        panel.add(txtPower);

        JLabel hintPower = new JLabel("Enter value in Watts (Example: 1500)");
        hintPower.setForeground(Color.LIGHT_GRAY);
        hintPower.setBounds(250, 220, 300, 20);
        panel.add(hintPower);

        JLabel lblCategory = new JLabel("Category");
        lblCategory.setForeground(Color.WHITE);
        lblCategory.setBounds(80, 260, 150, 25);
        panel.add(lblCategory);

        cmbCategory = new JComboBox<>(new String[]{
                "Select Category",
                "Cooling & HVAC",
                "Kitchen Appliance",
                "Entertainment Device",
                "Lighting",
                "Laundry Equipment",
                "Home Office",
                "Cleaning Equipment",
                "Security Device",
                "Other"
        });
        cmbCategory.setBounds(250, 260, 300, 40);
        panel.add(cmbCategory);

        btnAdd = new JButton("Add Appliance");
        btnAdd.setBounds(250, 340, 300, 45);
        btnAdd.setBackground(
                new Color(
                        0,
                        150,
                        80
                )
        );
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        panel.add(btnAdd);

        btnBack = new JButton("Back To Dashboard");
        btnBack.setBounds(250, 400, 300, 45);
        btnBack.setBackground(new Color(70, 70, 70));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        panel.add(btnBack);

        // ---- Search section (was misplaced outside method) ----
        JLabel lblSearch = new JLabel("Search");
        lblSearch.setForeground(Color.WHITE);
        lblSearch.setBounds(600, 100, 100, 25);
        panel.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(700, 100, 250, 35);
        panel.add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBackground(
                new Color(
                        120,
                        80,
                        220
                )
        );
        btnSearch.addActionListener(
                e -> searchAppliance()
        );
        btnSearch.setBounds(970, 100, 120, 35);
        panel.add(btnSearch);

        // ---- Update / Delete / Refresh (was misplaced outside method) ----
        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(
                new Color(
                        255,
                        165,
                        0
                )
        );
        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(
                e -> deleteAppliance()
        );
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(
                e -> {

                    txtSearch.setText("");

                    loadTable();

                }
        );
        btnUpdate.addActionListener(
                e -> updateAppliance()
        );

        btnUpdate.setBounds(600, 340, 150, 45);
        btnDelete.setBounds(780, 340, 150, 45);
        btnDelete.setBackground(
                new Color(
                        200,
                        50,
                        50
                )
        );
        btnRefresh.setBounds(960, 340, 150, 45);
        btnRefresh.setBackground(
                new Color(
                        70,
                        130,
                        180
                )
        );

        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnRefresh);


        // ---- Table (was misplaced outside method) ----
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Power");
        tableModel.addColumn("Category");

        applianceTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(applianceTable);
        scrollPane.setBounds(50, 470, 1060, 200);
        panel.add(scrollPane);
        applianceTable.getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if(
                                    !e.getValueIsAdjusting()
                            ) {

                                fillFieldsFromTable();

                            }

                        }
                );

        // ---- Action Listeners ----
        btnAdd.addActionListener(e -> addAppliance());

        btnBack.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        loadTable();
        add(panel);
    }

    private void addAppliance() {

        String name = txtName.getText().trim();
        String power = txtPower.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter appliance name.");
            return;
        }

        if (power.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter power rating in Watts.");
            return;
        }

        double powerValue;
        try {
            powerValue = Double.parseDouble(power);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Power rating must be numeric.");
            return;
        }

        if (powerValue < 1 || powerValue > 10000) {
            JOptionPane.showMessageDialog(this, "Power rating must be between 1 and 10000 Watts.");
            return;
        }

        if (category.equals("Select Category")) {
            JOptionPane.showMessageDialog(this, "Please select category.");
            return;
        }

        Appliance appliance = new Appliance();
        appliance.setApplianceName(name);
        appliance.setPowerRating(powerValue);
        appliance.setCategory(category);

        ApplianceDAO dao = new ApplianceDAO();

        if (dao.addAppliance(appliance)) {
            JOptionPane.showMessageDialog(this, "Appliance Added Successfully!");
            txtName.setText("");
            txtPower.setText("");
            cmbCategory.setSelectedIndex(0);
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed To Add Appliance.");
        }
    }

    private void loadTable() {

        tableModel.setRowCount(0);

        ApplianceDAO dao = new ApplianceDAO();

        for (Appliance appliance : dao.getAllAppliances()) {
            tableModel.addRow(new Object[]{
                    appliance.getApplianceId(),
                    appliance.getApplianceName(),
                    appliance.getPowerRating(),
                    appliance.getCategory()
            });
        }
    }

    private void fillFieldsFromTable() {

        int row =
                applianceTable.getSelectedRow();

        if(row == -1) {

            return;

        }

        selectedApplianceId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                row,
                                0
                        ).toString()
                );

        txtName.setText(
                tableModel.getValueAt(
                        row,
                        1
                ).toString()
        );

        txtPower.setText(
                tableModel.getValueAt(
                        row,
                        2
                ).toString()
        );

        cmbCategory.setSelectedItem(
                tableModel.getValueAt(
                        row,
                        3
                ).toString()
        );
    }
    private void deleteAppliance() {

        if(selectedApplianceId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select appliance first."
            );

            return;
        }

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete this appliance?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

        if(
                option != JOptionPane.YES_OPTION
        ) {

            return;
        }

        ApplianceDAO dao =
                new ApplianceDAO();

        if(
                dao.deleteAppliance(
                        selectedApplianceId
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Deleted Successfully!"
            );

            selectedApplianceId = -1;

            txtName.setText("");

            txtPower.setText("");

            cmbCategory.setSelectedIndex(0);

            loadTable();
        }
    }

    private void updateAppliance() {

        if(selectedApplianceId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select appliance first."
            );

            return;
        }

        String name =
                txtName.getText().trim();

        String power =
                txtPower.getText().trim();

        String category =
                cmbCategory
                        .getSelectedItem()
                        .toString();

        if(name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter appliance name."
            );

            return;
        }

        double powerValue;

        try {

            powerValue =
                    Double.parseDouble(power);

        }
        catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    "Power must be numeric."
            );

            return;
        }

        Appliance appliance =
                new Appliance();

        appliance.setApplianceId(
                selectedApplianceId
        );

        appliance.setApplianceName(
                name
        );

        appliance.setPowerRating(
                powerValue
        );

        appliance.setCategory(
                category
        );

        ApplianceDAO dao =
                new ApplianceDAO();

        if(
                dao.updateAppliance(
                        appliance
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Updated Successfully!"
            );

            loadTable();

        }
    }

    private void searchAppliance() {

        String keyword =
                txtSearch.getText().trim();

        if(keyword.isEmpty()) {

            loadTable();

            return;
        }

        tableModel.setRowCount(0);

        ApplianceDAO dao =
                new ApplianceDAO();

        for(
                Appliance appliance :
                dao.searchAppliances(
                        keyword
                )
        ) {

            tableModel.addRow(
                    new Object[]{

                            appliance.getApplianceId(),

                            appliance.getApplianceName(),

                            appliance.getPowerRating(),

                            appliance.getCategory()

                    }
            );
        }
    }
}