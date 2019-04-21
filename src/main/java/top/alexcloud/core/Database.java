package top.alexcloud.core;

import top.alexcloud.BackendConfiguration;
import top.alexcloud.resources.MiscResource;

import org.slf4j.LoggerFactory;

import java.sql.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Database {
    private static final ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MiscResource.class);
    private static final String connectionPrefix = "jdbc:sqlite:";
    private static String dbFullPath = "";
    private static String dbUser = "";
    private static String dbPassword = "";
    private static Connection dbConnection;

    private static ConcurrentHashMap<String, HashMap<Integer, String>> allResults = new ConcurrentHashMap<>();

    public Database(BackendConfiguration params) {
        dbFullPath = params.getDbFullPath();
        dbUser = params.getDbUser();
        dbPassword = params.getDbPassword();

//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (ClassNotFoundException e) {
//            LOGGER.error(e.getMessage());
//        }
        try {
            dbConnection = DriverManager.getConnection(connectionPrefix + dbFullPath);
            LOGGER.info("Connection to the database has been established '{}'", dbFullPath);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static void createTable(String sql) {
        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public static HashMap<Integer, String> findDictInfo(Integer id) {
        HashMap<Integer,String> searchResults = new HashMap<>();
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM dictionary WHERE id = ?";
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                searchResults.put(rs.getInt("id"), rs.getString("description"));
            }
            rs.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return searchResults;
    }

    public static HashMap<Integer, String> findWord(String word) {
        String uniqueID = UUID.randomUUID().toString();
        Long lastAccess = System.currentTimeMillis();
        HashMap<Integer,String> searchResults = new HashMap<>();

        new Thread(() -> {
            PreparedStatement pstmt = null;
            String sql = "SELECT * FROM entry WHERE word LIKE ? ORDER BY word ASC";
            try {
                pstmt = dbConnection.prepareStatement(sql);
                pstmt.setString(1, word);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    searchResults.put(rs.getInt("id"), rs.getString("meaning"));
                }
                rs.close();

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            } finally {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }).start();


        return searchResults;
    }



}

