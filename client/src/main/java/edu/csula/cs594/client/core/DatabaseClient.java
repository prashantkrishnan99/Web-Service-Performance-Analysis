package edu.csula.cs594.client.core;

import edu.csula.cs594.client.pojo.QueueItem;
import edu.csula.cs594.client.pojo.ProjectResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseClient {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseClient.class);
    private static final DatabaseClient dbClient = new DatabaseClient();    
    
    private Connection connect = null;

    String defaultDatabase = "";
    String dbUser = "root";
    String dbPwd = "";
    String server = "localhost";

    public static DatabaseClient getInstance() {
        return dbClient;
    }
    
    public DatabaseClient() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://" + server + "/" + defaultDatabase + "?user=" + dbUser + "&password=" + dbPwd);
            connect.setAutoCommit(true);
            connect.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        } catch (Exception e) {
            logger.error("Unable connect the database: ", e);
        }
    }

    public void recordWsDuration(QueueItem item) throws SQLException {
        PreparedStatement preparedStatement = connect
                .prepareStatement("insert into stats.deltas (uri, roundTripTime) values (?, ?)");
        preparedStatement.setString(1, item.getUri());
        preparedStatement.setInt(2, (int) item.getDuration());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void getOverAllAvg() throws SQLException {

        // logger.debug("Getting avg rtt...");
        
        PreparedStatement preparedStatement = connect.prepareStatement("select avg(roundTripTime)  from stats.deltas");

        ResultSet resultSet = preparedStatement.executeQuery();                
        
        int avgMs = -1;
        if (resultSet.next()) {
            avgMs = resultSet.getInt(1);            
        }

        if (-1 == avgMs) {
            logger.error("Unable to get average round trip time.");
        } else {
            logger.info("Average round trip time for all calls is: " + avgMs + "ms");
        }
        preparedStatement.close();
    }
    
    public List<ProjectResponse> getProjectList() throws SQLException {
        PreparedStatement preparedStatement = connect.prepareStatement("select projectname, uri, "
        		+ "requestcount, datecreated from stats.projects");

        ResultSet resultSet = preparedStatement.executeQuery();                

        List<ProjectResponse> results = new ArrayList<>();
        
        while (resultSet.next()) {
            String projectname = resultSet.getString(1);
            String uri = resultSet.getString(2);
            int count = resultSet.getInt(3);
            String date = resultSet.getString(4);
            ProjectResponse p = new ProjectResponse();
            p.setProjectname(projectname);
            p.setUri(uri);
            p.setRequestCount(count);
            p.setDate(date);
            results.add(p);
        }
        
        return results;
    }
    
    public void createProject(String projectname, String uri, int count) throws SQLException {
        PreparedStatement preparedStatement = connect
                .prepareStatement("insert into stats.projects (projectname, uri, requestCount) values (?, ?, ?)");
        preparedStatement.setString(1, projectname);
        preparedStatement.setString(2, uri);
        preparedStatement.setInt(3, count);
        preparedStatement.executeUpdate();
        preparedStatement.close();        
    }

    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            // Ignore
        }
    }
}
