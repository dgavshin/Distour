package sample;

import sample.settings.Configs;
import sample.settings.Const;
import sample.settings.GameMessage;
import sample.settings.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;
    private static List<String> names = new ArrayList<>();

    static {
        names.add("sender ");
        names.add("message ");
        names.add("messageDate ");
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost/" + dbName +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
                Const.USERS_EMAIL + ")" + "VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getPassword());
        prSt.setString(3, user.getEmail());
        prSt.executeUpdate();
    }

    public ResultSet getUser(User user) throws SQLException, ClassNotFoundException {

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getPassword());

        return prSt.executeQuery();
    }

    public ResultSet checkUser(User user) throws SQLException, ClassNotFoundException {

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? OR " + Const.USERS_EMAIL + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, user.getUsername());
        prSt.setString(2, user.getEmail());

        return prSt.executeQuery();
    }

    public void recordChat(String message, String sender) throws SQLException, ClassNotFoundException {
        DatabaseHandler handler = new DatabaseHandler();
        String insert = "INSERT INTO chat (sender,message,messageDate)" + "VALUES(?,?,NOW())";
        PreparedStatement prSt = handler.getDbConnection().prepareStatement(insert);
        prSt.setString(1, sender);
        prSt.setString(2, message);
        prSt.executeUpdate();
    }

    public ResultSet find(String insert) throws SQLException, ClassNotFoundException {
        String line = "SELECT *" + insert + ";";
        PreparedStatement prSt = getDbConnection().prepareStatement(line);
        System.out.println(prSt);
        return prSt.executeQuery();
    }

    public ResultSet getHost(String nickname) throws SQLException, ClassNotFoundException {
        String line = "SELECT ip, port FROM " + Const.USER_TABLE + "WHERE username = '" + nickname + "'";
        PreparedStatement prSt = getDbConnection().prepareStatement(line);
        System.out.println(prSt);
        return prSt.executeQuery();
    }

    public void writeGame(GameMessage game) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO games (gameId,exercise,player1,player2,date,typegame)" + "VALUES(?,?,?,?,NOW(),?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, game.getGameId());
        prSt.setString(2, game.getExercise());
        prSt.setString(3, game.getPlayer1());
        prSt.setString(4, game.getPlayer2());
        prSt.setString(5, game.getCategory());
        prSt.executeUpdate();
    }

    public List<GameMessage> getGame(String nickname) throws ClassNotFoundException {

        List<GameMessage> list = new ArrayList<>();
        try {
            String select = "SELECT exercise, player1, player2, winner, typegame, gameid, videoPath1 FROM games WHERE player1 = ? or player2 = ?";
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, nickname);
            prSt.setString(2, nickname);
            ResultSet set = prSt.executeQuery();
            int column = set.getMetaData().getColumnCount();
            while (set.next()) {
                String[] values = new String[8];
                for (int i = 1; i < column; i++) {
                    values[i] = set.getString(i);
                }
                GameMessage game = new GameMessage(values[6],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5]);
                list.add(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setVideoPath(String path, String nickname, String gameId) throws ClassNotFoundException {
        try {
            String update;
            if (nickname.equalsIgnoreCase(getPlayer1(gameId))) {
                update = "UPDATE games SET videoPath1 =? WHERE gameId =?";
            } else update = "UPDATE games SET videoPath2 =? WHERE gameId =?";

            System.out.println(update);
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, path);
            prSt.setString(2, gameId);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String lastNickname, String newNickname) throws SQLException, ClassNotFoundException {
        String update = "UPDATE users SET username =? WHERE username =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setString(1, newNickname);
        prSt.setString(2, lastNickname);
        prSt.executeUpdate();
    }

    public boolean checkNickname(String nickname) throws SQLException {
        try {
            String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?";

            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, nickname);

            int counter = 0;
            ResultSet set = prSt.executeQuery();
            while (set.next()) {
                counter++;
            }
            return counter < 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAvatar(String nickname) throws SQLException, ClassNotFoundException {
        String select = "SELECT avatar FROM users WHERE username =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, nickname);
        ResultSet set = prSt.executeQuery();
        int column = set.getMetaData().getColumnCount();
        String path = null;
        while (set.next())
            for (int i = 1; i <= column; i++) {
                path = set.getString(i);
            }
        return path;
    }

    public void setAvatar(String nickname, String avatar) throws SQLException, ClassNotFoundException {
        String update = "UPDATE users SET avatar =? WHERE username =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(update);
        prSt.setString(1, "c:\\users\\shokk\\desktop\\distour\\src\\sample\\assets\\avatars\\" + avatar);
        prSt.setString(2, nickname);
        prSt.executeUpdate();
    }

    private String getPlayer1(String gameId) throws SQLException, ClassNotFoundException {
        String select = "SELECT player1 FROM games WHERE gameID =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, gameId);
        ResultSet set = prSt.executeQuery();
        int column = set.getMetaData().getColumnCount();
        String nick = null;
        while (set.next())
            for (int i = 1; i <= column; i++) {
                nick = set.getString(i);
            }
        return nick;
    }
}
