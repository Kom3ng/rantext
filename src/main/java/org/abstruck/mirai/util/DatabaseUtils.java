package org.abstruck.mirai.util;

import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.console.plugin.PluginManager;
import net.mamoe.mirai.utils.MiraiLogger;
import org.abstruck.mirai.Rantext;
import org.abstruck.mirai.config.Config;

import java.sql.*;

public class DatabaseUtils {
    private static final MiraiLogger LOGGER = Rantext.INSTANCE.getLogger();
    private static long cooldown;
    private static long startTimeStamp;
    private static String sqlUrl;
    private static String sqlPassword;
    private static String sqlUsername;
    private static Connection connection;

    public static void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            reload();
        } catch (ClassNotFoundException e) {
            LOGGER.error("error when init database connection");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.error("error when reload database connection");
            throw new RuntimeException(e);
        }
    }

    public static void reload() throws SQLException {
        close();

        cooldown = Config.cooldown.get();
        startTimeStamp = Config.startTimeStamp.get();
        sqlUrl = Config.sqlUrl.get();
        sqlUsername = Config.sqlUsername.get();
        sqlPassword = Config.sqlPassword.get();

        if (sqlUrl.isEmpty()) {
            LOGGER.warning("sql url is empty");
            PluginManager.INSTANCE.disablePlugin(Rantext.INSTANCE);
        }

        connection = DriverManager.getConnection(sqlUrl,sqlUsername,sqlPassword);
    }

    public static void close() throws SQLException {
        if (connection != null) connection.close();
    }
    public static String selectSingleMessage(){
        try {
            String sqlStatement = "SELECT id,random_message FROM message WHERE last_use_timestamp < " + getEligibleTimeStamp()+" ORDER BY RAND() LIMIT 1;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String s = "UPDATE message SET last_use_timestamp = " + System.currentTimeMillis() + " WHERE id = " + id+";";
                connection.createStatement().execute(s);
                return resultSet.getString("random_message");
            }
        } catch (SQLException e){
            LOGGER.error(e);
        }

        return null;
    }

    public static String selectSingleImage(){
        try {
            String sqlStatement = "SELECT id,image_path FROM image WHERE last_use_timestamp < " + getEligibleTimeStamp()+" ORDER BY RAND() LIMIT 1;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String s = "UPDATE image SET last_use_timestamp = " + System.currentTimeMillis() + " WHERE id = " + id+";";
                connection.createStatement().execute(s);
                return resultSet.getString("image_path");
            }
        } catch (SQLException e){
            LOGGER.error(e);
        }

        return "";
    }

    private static long getEligibleTimeStamp(){
        return System.currentTimeMillis() - cooldown;
    }
}
