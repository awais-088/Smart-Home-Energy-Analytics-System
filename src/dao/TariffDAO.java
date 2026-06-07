package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TariffDAO {

    public double getCurrentTariff() {

        String query =
                "SELECT price_per_unit FROM tariff LIMIT 1";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getDouble(
                        "price_per_unit"
                );
            }

        }
        catch(Exception e){

            e.printStackTrace();
        }

        return 0;
    }

    public boolean updateTariff(
            double newTariff
    ) {

        String query =
                "UPDATE tariff SET price_per_unit=? WHERE tariff_id=1";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setDouble(
                    1,
                    newTariff
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
}