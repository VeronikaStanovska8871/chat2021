package sk.kosickaakademia.stanovska.chat;

import sk.kosickaakademia.stanovska.entity.User;

import java.sql.*;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String username = "mysqluser";
    private String password = "Kosice2021!";
    private final String insertNewUser = "INSERT INTO user (login, password) VALUES (?,?)";
    private final String loginUser = "Select * FROM user WHERE login LIKE ? and password LIKE ?";

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public void test() {
        try {
            Connection con = getConnection();
            if (con == null) {
                System.out.println("Error");
            } else {
                System.out.println("Success");
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertNewUser(String login, String password) {
        if (login == null || login.equals("") || password == null || password.length() < 6) {
            return false;
            String hashPassword = new Util().getMd5(password);
            try {
                Connection con = getConnection();
                if (con == null) {
                    System.out.println("Connection Error");
                    return false;
                }
                PreparedStatement ps = con.prepareStatement(insertNewUser);
                ps.setString(1, login);
                ps.setString(2, hashPassword);
                int result = ps.executeUpdate();
                con.close();
                if (result == 0)
                    return false;
                else {
                    System.out.println("User " + login + " has been added to the database !");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("User already exists");
            }
            return true;
        }

        public User loginUser(String login, String password){
            if (login == null || login.equals("") || password == null || password.length() < 6)
                return null;
            String hashPassword = new Util().getMd5(password);
            try {
                Connection con = getConnection();
                if (con == null) {
                    System.out.println("Connection error!");
                    return null;
                }
                PreparedStatement ps = con.prepareStatement(loginUser);
                ps.setString(1, login);
                ps.setString(2, hashPassword);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Success login!");
                    int id = rs.getInt("id");
                    User user = new User(id, login, hashPassword);
                    con.close();
                    return user;
                } else {
                    con.close();
                    System.out.println("Incorect credentials!");
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        }
        public boolean sendMessage (int from, String toUser, String text){
        if (text == null || text.equals("")){
            return false;
        }
        int to = getUserId(toUser);
        if (to == -1)
            return false;
        try{
            Connection con = getConnection();
            if (con == null){
                System.out.println("Connection issue");
                return false;
            }
            PreparedStatement ps=con.prepareStatement(newMessage);
            ps.setInt(1,from);
            ps.setInt(2,to);
            ps.setString(3, text);
            int result = ps.executeUpdate();
            con.close();
            if (result<1){
                System.out.println("Message not delivered");
                return false;
            }else {
                System.out.println("Message sent");
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}


