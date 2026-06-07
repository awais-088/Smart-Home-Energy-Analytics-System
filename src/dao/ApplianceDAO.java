package dao;

import database.DBConnection;
import model.Appliance;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import session.Session;
public class ApplianceDAO {

    public boolean addAppliance(
            Appliance appliance
    ) {

        String query =
                "INSERT INTO appliances (appliance_name,power_rating,category,user_id) VALUES(?,?,?,?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(
                    4,
                    Session.userId
            );
            ps.setString(
                    1,
                    appliance.getApplianceName()
            );

            ps.setDouble(
                    2,
                    appliance.getPowerRating()
            );

            ps.setString(
                    3,
                    appliance.getCategory()
            );

            ps.setInt(
                    4,
                    Session.userId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }

    public int countAppliances() {

        String query =
                "SELECT COUNT(*) FROM appliances WHERE user_id=?";


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

                return rs.getInt(1);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return 0;
    }
    public ArrayList<Appliance>
    getAllAppliances() {

        ArrayList<Appliance> list =
                new ArrayList<>();

        String query =
                "SELECT *\n" +
                        "FROM appliances\n" +
                        "WHERE user_id=?";

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

                Appliance appliance =
                        new Appliance();

                appliance.setApplianceId(
                        rs.getInt(
                                "appliance_id"
                        )
                );

                appliance.setApplianceName(
                        rs.getString(
                                "appliance_name"
                        )
                );

                appliance.setPowerRating(
                        rs.getDouble(
                                "power_rating"
                        )
                );

                appliance.setCategory(
                        rs.getString(
                                "category"
                        )
                );

                list.add(appliance);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return list;
    }
    public boolean updateAppliance(Appliance appliance) {

        String query =
                "UPDATE appliances SET appliance_name=?, power_rating=?, category=? WHERE appliance_id=? AND user_id=?";


        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);


            ps.setString(
                    1,
                    appliance.getApplianceName()
            );

            ps.setDouble(
                    2,
                    appliance.getPowerRating()
            );

            ps.setString(
                    3,
                    appliance.getCategory()
            );

            ps.setInt(
                    4,
                    appliance.getApplianceId()
            );
            ps.setInt(
                    5,
                    Session.userId
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
    public boolean deleteAppliance(int applianceId) {

        try {

            Connection connection =
                    DBConnection.getConnection();

            // Delete usage records first

            PreparedStatement ps1 =
                    connection.prepareStatement(
                            "DELETE FROM usage_records WHERE appliance_id=?"
                    );

            ps1.setInt(
                    1,
                    applianceId
            );

            ps1.executeUpdate();

            // Then delete appliance

            PreparedStatement ps2 =
                    connection.prepareStatement(
                            "DELETE FROM appliances WHERE appliance_id=? AND user_id=?"
                    );


            ps2.setInt(
                    1,
                    applianceId
            );
            ps2.setInt(
                    2,
                    Session.userId
            );

            return ps2.executeUpdate() > 0;
        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
    public double getTotalInstalledPower() {

        String query =
                "SELECT SUM(power_rating) FROM appliances WHERE user_id=?";

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

    public String getHighestPowerAppliance() {

        String query =
                "SELECT appliance_name FROM appliances WHERE user_id=? ORDER BY power_rating DESC LIMIT 1";

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

                return rs.getString(
                        "appliance_name"
                );

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return "N/A";
    }

    public double getEstimatedMonthlyBill() {

        double totalPower =
                getTotalInstalledPower();

        double units =
                (totalPower * 5 * 30)
                        / 1000;

        return units * 65;
    }


    public ArrayList<Appliance> searchAppliances(
            String keyword
    ) {

        ArrayList<Appliance> list =
                new ArrayList<>();

        String query =
                """
                SELECT *
                FROM appliances
                WHERE user_id=?
                AND appliance_name LIKE ?
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
                    "%" + keyword + "%"
            );

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                Appliance appliance =
                        new Appliance();

                appliance.setApplianceId(
                        rs.getInt("appliance_id")
                );

                appliance.setApplianceName(
                        rs.getString("appliance_name")
                );

                appliance.setPowerRating(
                        rs.getDouble("power_rating")
                );

                appliance.setCategory(
                        rs.getString("category")
                );

                list.add(appliance);

            }

        }
        catch(Exception e){

            e.printStackTrace();

        }

        return list;
    }

    public ArrayList<Appliance> getApplianceList() {

        return getAllAppliances();

    }
}