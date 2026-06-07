package dao;
import java.sql.ResultSet;
import database.DBConnection;
import model.User;
import session.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    public boolean registerUser(User user) {

        String query =
                "INSERT INTO users(fullname,email,password) VALUES(?,?,?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1,user.getFullName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());

            int rows =
                    ps.executeUpdate();

            return rows > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
    public boolean loginUser(
            String email,
            String password
    ) {

        String query =
                "SELECT * FROM users WHERE email=? AND password=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1,email);
            ps.setString(2,password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                Session.userId =
                        rs.getInt("id");

                System.out.println(
                        "Logged User ID = "
                                + Session.userId
                );
                return true;
            }

            return false;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
    public boolean resetPassword(
            String email,
            String password
    ) {

        String query =
                "UPDATE users SET password=? WHERE email=?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(
                    1,
                    password
            );

            ps.setString(
                    2,
                    email
            );

            return ps.executeUpdate() > 0;

        }
        catch(Exception e){

            e.printStackTrace();

            return false;
        }
    }
}