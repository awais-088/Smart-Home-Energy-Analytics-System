package gui;

import dao.UsageDAO;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.*;

public class ReportForm extends JFrame {

    JTable reportTable;

    DefaultTableModel tableModel;
    JTextField txtFromDate;
    JTextField txtToDate;

    JLabel lblTotalUnits;
    JLabel lblTotalCost;
    JLabel lblAverageUsage;
    JLabel lblHighestAppliance;

    JButton btnGenerate;
    JButton btnBack;

    public ReportForm() {

        setTitle("Energy Analytics Reports");

        setSize(1200, 750);

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
                new Color(25, 25, 25)
        );

        JLabel title =
                new JLabel(
                        "Energy Analytics Reports"
                );

        title.setBounds(
                400,
                20,
                450,
                40
        );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        panel.add(title);

        JLabel lblFrom =
                new JLabel("From Date");

        lblFrom.setBounds(
                80,
                100,
                150,
                30
        );

        lblFrom.setForeground(
                Color.WHITE
        );

        panel.add(lblFrom);

        txtFromDate =
                new JTextField(
                        "2026-06-01"
                );

        txtFromDate.setBounds(
                220,
                100,
                200,
                35
        );

        panel.add(txtFromDate);

        JLabel lblTo =
                new JLabel("To Date");

        lblTo.setBounds(
                500,
                100,
                150,
                30
        );

        lblTo.setForeground(
                Color.WHITE
        );

        panel.add(lblTo);

        txtToDate =
                new JTextField(
                        "2026-06-30"
                );

        txtToDate.setBounds(
                620,
                100,
                200,
                35
        );

        panel.add(txtToDate);

        btnGenerate =
                new JButton(
                        "Generate Report"
                );
        btnGenerate.addActionListener(
                e -> loadReportData()
        );

        btnGenerate.setBounds(
                900,
                95,
                180,
                40
        );

        btnGenerate.setBackground(
                new Color(
                        110,
                        80,
                        220
                )
        );

        btnGenerate.setForeground(
                Color.WHITE
        );

        panel.add(btnGenerate);

        createSummaryCards(panel);
        tableModel =
                new DefaultTableModel();

        tableModel.addColumn("Date");
        tableModel.addColumn("Appliance");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Units");
        tableModel.addColumn("Cost");

        reportTable =
                new JTable(tableModel);
        reportTable.setRowHeight(30);

        reportTable.setBackground(
                new Color(
                        45,
                        45,
                        45
                )
        );

        reportTable.setForeground(
                Color.WHITE
        );

        reportTable.setSelectionBackground(
                new Color(
                        110,
                        80,
                        220
                )
        );

        reportTable.getTableHeader()
                .setBackground(
                        new Color(
                                70,
                                70,
                                70
                        )
                );

        reportTable.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        reportTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                14
                        )
                );
        JScrollPane scrollPane =
                new JScrollPane(reportTable);

        scrollPane.setBounds(
                80,
                430,
                1030,
                150
        );

        panel.add(scrollPane);

        btnBack =
                new JButton(
                        "Back To Dashboard"
                );

        btnBack.setBounds(
                450,
                620,
                250,
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

        btnBack.addActionListener(
                e -> {

                    dispose();

                    new Dashboard();

                }
        );

        loadSummary();
        loadReportData();
        add(panel);

    }

    private void createSummaryCards(
            JPanel panel
    ) {

        lblTotalUnits =
                createCard(
                        panel,
                        "Total Units",
                        80,
                        200
                );

        lblTotalCost =
                createCard(
                        panel,
                        "Total Cost",
                        350,
                        200
                );

        lblAverageUsage =
                createCard(
                        panel,
                        "Average Usage",
                        620,
                        200
                );

        lblHighestAppliance =
                createCard(
                        panel,
                        "Highest Appliance",
                        890,
                        200
                );
    }
    private JLabel createCard(
            JPanel panel,
            String title,
            int x,
            int y
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BorderLayout()
        );

        card.setBackground(
                new Color(
                        60,
                        60,
                        60
                )
        );

        card.setBounds(
                x,
                y,
                220,
                180
        );

        JLabel lblTitle =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        lblTitle.setForeground(
                Color.WHITE
        );

        lblTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        JLabel lblValue =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        lblValue.setForeground(
                Color.GREEN
        );

        lblValue.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        card.add(
                lblTitle,
                BorderLayout.NORTH
        );

        card.add(
                lblValue,
                BorderLayout.CENTER
        );

        panel.add(card);

        return lblValue;
    }
    private void loadSummary() {

        UsageDAO dao =
                new UsageDAO();

        lblTotalUnits.setText(
                String.format(
                        "%.2f",
                        dao.getTotalUnits()
                )
        );

        lblTotalCost.setText(
                "Rs "
                        +
                        String.format(
                                "%.2f",
                                dao.getTotalCost()
                        )
        );

        lblAverageUsage.setText(
                String.format(
                        "%.2f",
                        dao.getAverageUsage()
                )
        );

        lblHighestAppliance.setText(
                dao.getHighestConsumingAppliance()
        );
    }
    private void loadReportData() {

        tableModel.setRowCount(0);

        String fromDate =
                txtFromDate
                        .getText()
                        .trim();

        String toDate =
                txtToDate
                        .getText()
                        .trim();

        if(
                !fromDate.matches(
                        "\\d{4}-\\d{2}-\\d{2}"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Use format YYYY-MM-DD"
            );

            return;
        }

        if(
                !toDate.matches(
                        "\\d{4}-\\d{2}-\\d{2}"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Use format YYYY-MM-DD"
            );

            return;
        }

        UsageDAO dao =
                new UsageDAO();

        for(
                Object[] row :
                dao.getReportData(
                        fromDate,
                        toDate
                )
        ) {

            tableModel.addRow(row);

        }
    }

}