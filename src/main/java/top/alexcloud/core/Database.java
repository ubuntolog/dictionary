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

    private static ConcurrentHashMap<String, HashMap<Integer, Match>> allResults = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Query> allQueries = new ConcurrentHashMap<>();

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

    public static Query findWord(String queryText) {
        String uniqueID = UUID.randomUUID().toString();
        Query currentQuery = new Query(uniqueID, queryText, Query.QueryState.INITIAL, "Initializing a new query", 0, System.currentTimeMillis());

        allQueries.put(uniqueID, currentQuery);
        allResults.put(uniqueID, new HashMap<>());

        new Thread(() -> {
            Query runningQuery = allQueries.get(uniqueID);
            Integer resultsCount = 0;
            PreparedStatement pstmt = null;
            String sql = "SELECT entry.id AS id, entry.word AS word, entry.meaning AS meaning, dictionary.id AS dict_id, dictionary.name AS dict_name FROM entry INNER JOIN dictionary ON entry.dictionary_id = dictionary.id WHERE entry.word LIKE ? ORDER BY entry.word ASC";
            try {
                pstmt = dbConnection.prepareStatement(sql);
                pstmt.setString(1, queryText);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    allResults.get(uniqueID).put(rs.getInt("id"), new Match(rs.getString("word"), rs.getString("dict_name"), rs.getString("meaning")));
                    runningQuery.state = Query.QueryState.RUNNING;
                    runningQuery.statusMessage = "Query is running";
                    runningQuery.resultsFound = resultsCount++;
                }
                rs.close();

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                runningQuery.state = Query.QueryState.FAILED;
                runningQuery.statusMessage = "Error while executing the query: " + e.getMessage();
            } finally {
                try {
                    pstmt.close();
                    runningQuery.state = Query.QueryState.FINISHED;
                    runningQuery.statusMessage = "The query has been executed successfully";
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                    runningQuery.state = Query.QueryState.FAILED;
                    runningQuery.statusMessage = "Error while finishing the query: " + e.getMessage();
                }
            }
            allQueries.put(uniqueID, runningQuery);
        }).start();


        return allQueries.get(uniqueID);
    }

    public static Query findQueryInfo(String id) {
        if (allQueries.containsKey(id)) {
            return allQueries.get(id);
        }
        return null;
    }

    public static HashMap<Integer, Match> findQueryMatches(String id) {
        if (allResults.containsKey(id)) {
            return allResults.get(id);
        }
        return null;
    }


}

