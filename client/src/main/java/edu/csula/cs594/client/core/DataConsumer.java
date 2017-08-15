package edu.csula.cs594.client.core;

import edu.csula.cs594.client.pojo.QueueItem;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataConsumer implements Runnable {

    private BlockingQueue<QueueItem> queue;
    private DatabaseClient client;

    public DataConsumer(BlockingQueue<QueueItem> q, DatabaseClient client) {
        this.queue = q;
        this.client = client;
        System.out.println("DataConsumer initialized.");
    }

    @Override
    public void run() {
        try {
            QueueItem msg;
            //consuming messages until exit message is received
            while ((msg = queue.take()) != null) {
                try {
                    if (msg != null) {
                        // System.out.println(" *** RECEIVED");
                        Thread.sleep(1);                    
                        
                        System.out.println("Received duration " + msg.getDuration() + "ms");
                        client.recordWsDuration(msg);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DataConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
