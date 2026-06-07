package gui;
import gui.LoginForm;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.ApplianceDAO;
import dao.UsageDAO;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Smart Home Energy Analytics System");

        setSize(1000,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(30,30,30)
        );

        createUI();

        setVisible(true);
    }

    private void createUI() {

        ApplianceDAO dao =
                new ApplianceDAO();
        UsageDAO usageDAO =
                new UsageDAO();

        JPanel topPanel = new JPanel();

        topPanel.setBackground(
                new Color(20,20,20)
        );

        JLabel title =
                new JLabel(
                        "Smart Home Energy Analytics System"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        topPanel.add(title);

        add(topPanel,BorderLayout.NORTH);

        JPanel centerPanel =
                new JPanel();

        centerPanel.setBackground(
                new Color(40,40,40)
        );

        centerPanel.setLayout(
                new GridLayout(
                        2,
                        2,
                        20,
                        20
                )
        );

        centerPanel.setBorder(
                new EmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );


        centerPanel.add(
                createCard(
                        "Total Appliances",
                        String.valueOf(
                                dao.countAppliances()
                        )
                )
        );

        centerPanel.add(
                createCard(
                        "Total Units",
                        String.format(
                                "%.2f",
                                usageDAO.getTotalUnits()
                        )
                )
        );

        centerPanel.add(
                createCard(
                        "Total Cost",
                        "Rs "
                                + String.format(
                                "%.2f",
                                usageDAO.getTotalCost()
                        )
                )
        );

        centerPanel.add(
                createCard(
                        "Predicted Monthly Bill",
                        "Rs " +
                                String.format(
                                        "%.2f",
                                        usageDAO.getPredictedMonthlyBill()
                                )
                )
        );

        add(centerPanel,BorderLayout.CENTER);

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                new Color(25,25,25)
        );


        JButton applianceBtn =
                new JButton(
                        "Manage Appliances"
                );
        applianceBtn.addActionListener(
                e -> new ApplianceForm()
        );

        JButton usageBtn =
                new JButton(
                        "Track Usage"
                );
        usageBtn.addActionListener(
                e -> new UsageForm()
        );

        JButton reportBtn =
                new JButton(
                        "Reports"
                );
        reportBtn.addActionListener(
                e -> new ReportForm()
        );

        JButton tariffBtn =
                new JButton(
                        "Tariff"
                );

        tariffBtn.addActionListener(
                e -> new TariffForm()
        );
        JButton logoutBtn =
                new JButton(
                        "Logout"
                );
        logoutBtn.addActionListener(
                e -> {

                    dispose();

                    new LoginForm();

                }
        );

        bottomPanel.add(applianceBtn);

        bottomPanel.add(usageBtn);

        bottomPanel.add(reportBtn);
        bottomPanel.add(tariffBtn);
        bottomPanel.add(logoutBtn);

        add(bottomPanel,
                BorderLayout.SOUTH);
    }

    private JPanel createCard(
            String title,
            String value
    ) {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BorderLayout()
        );

        panel.setBackground(
                new Color(60,60,60)
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
                        value,
                        SwingConstants.CENTER
                );

        lblValue.setForeground(
                Color.GREEN
        );

        lblValue.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        panel.add(
                lblTitle,
                BorderLayout.NORTH
        );

        panel.add(
                lblValue,
                BorderLayout.CENTER
        );

        return panel;
    }
}