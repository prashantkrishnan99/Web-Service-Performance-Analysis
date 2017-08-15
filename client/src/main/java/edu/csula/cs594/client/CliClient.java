package edu.csula.cs594.client;

import edu.csula.cs594.client.core.DataConsumer;
import edu.csula.cs594.client.core.DatabaseClient;
import edu.csula.cs594.client.core.HttpClient;
import edu.csula.cs594.client.pojo.QueueItem;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliClient {

    private static final Logger logger = LoggerFactory.getLogger(CliClient.class);    
    public static DatabaseClient dbClient = DatabaseClient.getInstance();
    public static final BlockingQueue<QueueItem> queue = new LinkedBlockingQueue<>();
    public static final DataConsumer consumer;    
    public static final LongAdder connectionCount = new LongAdder();    
    public static final AtomicLong maxConnectionCount = new AtomicLong(0);
    
    static {
        consumer = new DataConsumer(queue, dbClient);            
        new Thread(consumer).start();        
    }
    
    public void simulateLoad(String uri, int numberOfCalls, int projectid) {
        HttpClient client = new HttpClient();    

        try {
            for (int i = 0; i < numberOfCalls; i++) {
                if (i % 1000 == 0) {
                    logger.info(i + " of " + numberOfCalls + " api calls made");
                }
                client.makeHttpCall(uri, projectid);
            }
        } catch (InterruptedException | ExecutionException | IOException | SQLException e) {
            logger.error("Failed to make REST API call", e);
        }

        
        try {
            client.close();                        
        } catch (Exception e) {
            logger.error("An exceptino occurred while closing the connection...", e);
        }        
        
    }
    
    public void close()  {

    }
    
    public static void main(String[] args) throws InterruptedException {

        String uri = "http://localhost:8080/webservice/v1/hello";
        int numberOfCalls = 10000;
        int projectid = 0; // TODO: get from database
        
        CliClient cli = new CliClient();
        cli.simulateLoad(uri, numberOfCalls, projectid);
        
        System.out.println("Waiting...");
        Thread.sleep(60000);
        System.out.println("Closing...");
        
        try {
            dbClient.getOverAllAvg();        
        } catch (Exception e) {
            // Ignore
        } finally {
            dbClient.close();
        }
        
        logger.info("Maximum connection count at any moment during the test is " + maxConnectionCount.get() + " connections.");
        

        System.exit(0);        
        
    }
    
}
