package dao;

import database.DBConnection;
import model.UsageRecord;
import session.Session;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsageDAO {

    public boolean addUsageRecord(
            UsageRecord record
    ) {

        String query =
                "INSERT INTO usage_records(appliance_id,usage_date,hours_used,units_consumed,cost,user_id)\n" +
                        " VALUES(?,?,?,?,?,?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    record.getApplianceId()
            );

            ps.setDate(
                    2,
                    record.getUsageDate()
            );

            ps.setDouble(
                    3,
                    record.getHoursUsed()
            );

            ps.setDouble(
                    4,
                    record.getUnitsConsumed()
            );

            ps.setDouble(
                    5,
                    record.getCost()
            );
            ps.setInt(
                    6,
                    Session.userId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    public ArrayList<UsageRecord> getAllUsageRecords() {

        ArrayList<UsageRecord> list =
                new ArrayList<>();

        String query =
                "SELECT * FROM usage_records WHERE user_id=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                UsageRecord record =
                        new UsageRecord();

                record.setUsageId(
                        rs.getInt(
                                "usage_id"
                        )
                );

                record.setApplianceId(
                        rs.getInt(
                                "appliance_id"
                        )
                );

                record.setUsageDate(
                        rs.getDate(
                                "usage_date"
                        )
                );

                record.setHoursUsed(
                        rs.getDouble(
                                "hours_used"
                        )
                );

                record.setUnitsConsumed(
                        rs.getDouble(
                                "units_consumed"
                        )
                );

                record.setCost(
                        rs.getDouble(
                                "cost"
                        )
                );

                list.add(record);
            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return list;
    }

    public double getTotalUnits() {

        String query =
                "SELECT SUM(units_consumed) FROM usage_records WHERE user_id=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }
    public double getTotalCost() {

        String query =
                "SELECT SUM(cost) FROM usage_records WHERE user_id=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }
    public double getAverageUsage() {

        String query =
                "SELECT AVG(units_consumed) FROM usage_records WHERE user_id=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);
            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }
    public String getHighestConsumingAppliance() {

        String query =
                """
                SELECT a.appliance_name
                FROM usage_records u
                INNER JOIN appliances a
                ON u.appliance_id = a.appliance_id
                WHERE u.user_id = ?
                ORDER BY u.units_consumed DESC
                LIMIT 1
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);
            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getString(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return "N/A";
    }

    public ArrayList<Object[]> getUsageHistory() {

        ArrayList<Object[]> list =
                new ArrayList<>();

        String query =
                """
                SELECT
                a.appliance_name,
                u.usage_date,
                u.hours_used,
                u.units_consumed,
                u.cost
                FROM usage_records u
                JOIN appliances a
                ON u.appliance_id = a.appliance_id
                WHERE u.user_id = ?
                ORDER BY u.usage_date DESC
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);
            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                list.add(
                        new Object[]{

                                rs.getString(
                                        "appliance_name"
                                ),

                                rs.getDate(
                                        "usage_date"
                                ),

                                rs.getDouble(
                                        "hours_used"
                                ),

                                rs.getDouble(
                                        "units_consumed"
                                ),

                                rs.getDouble(
                                        "cost"
                                )
                        }
                );
            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return list;
    }

    public ArrayList<Object[]> getReportData(
            String fromDate,
            String toDate
    ) {

        ArrayList<Object[]> list =
                new ArrayList<>();

        String query =
                """
                SELECT
                u.usage_date,
                a.appliance_name,
                u.hours_used,
                u.units_consumed,
                u.cost
                FROM usage_records u
                JOIN appliances a
                ON u.appliance_id = a.appliance_id
                WHERE u.user_id = ?
                AND u.usage_date BETWEEN ? AND ?
                ORDER BY u.usage_date
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );

            ps.setString(
                    2,
                    fromDate
            );

            ps.setString(
                    3,
                    toDate
            );
            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                list.add(
                        new Object[]{

                                rs.getDate(
                                        "usage_date"
                                ),

                                rs.getString(
                                        "appliance_name"
                                ),

                                rs.getDouble(
                                        "hours_used"
                                ),

                                rs.getDouble(
                                        "units_consumed"
                                ),

                                rs.getDouble(
                                        "cost"
                                )
                        }
                );
            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return list;
    }
    public String getMostExpensiveAppliance() {

        String query =
                """
                SELECT a.appliance_name
                FROM usage_records u
                JOIN appliances a
                ON u.appliance_id = a.appliance_id
                WHERE u.user_id = ?
                ORDER BY u.cost DESC
                LIMIT 1
                """;

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getString(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return "N/A";
    }
    public double getHighestCost() {

        String query =
                "SELECT MAX(cost) FROM usage_records WHERE user_id=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    1,
                    Session.userId
            );
            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }

    public double getPredictedMonthlyBill() {

        double currentCost =
                getTotalCost();

        int currentDay =
                java.time.LocalDate.now()
                        .getDayOfMonth();

        int totalDays =
                java.time.LocalDate.now()
                        .lengthOfMonth();

        if(currentDay == 0) {

            return currentCost;

        }

        return (currentCost / currentDay)
                * totalDays;
    }

}