package edu.csula.cs594.client.core;

import edu.csula.cs594.client.pojo.QueueItem;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHandler.STATE;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import edu.csula.cs594.client.CliClient;
import static edu.csula.cs594.client.CliClient.connectionCount;
import static edu.csula.cs594.client.CliClient.maxConnectionCount;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalAsyncHandler implements AsyncHandler<Long> {

    private static final Logger logger = LoggerFactory.getLogger(LocalAsyncHandler.class);
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private long startTime;
    private String url;

    BlockingQueue<QueueItem> queue;
    
    public LocalAsyncHandler(BlockingQueue<QueueItem> queue, String url, long startTime, int projectid) {
        this.queue = queue;
        this.startTime = startTime;
        this.url = url;
        
    }
    
    @Override
    public STATE onStatusReceived(HttpResponseStatus status) throws Exception {
        int statusCode = status.getStatusCode();
        // The Status have been read
        // If you don't want to read the headers,body or stop processing the response
        if (statusCode >= 500) {
            return STATE.ABORT;
        }
        return STATE.CONTINUE;
    }    
    

    @Override
    public STATE onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
         bytes.write(bodyPart.getBodyPartBytes());
         return STATE.CONTINUE;
    }
   
    @Override
    public void onThrowable(Throwable t){
        // Something wrong happened.
        CliClient.connectionCount.decrement();
    }

    @Override
    public STATE onHeadersReceived(HttpResponseHeaders headers) throws Exception {
        return STATE.CONTINUE;
    }

    @Override
    public Long onCompleted() throws Exception {
         // Will be invoked once the response has been fully read or a ResponseComplete exception
         // has been thrown.
         // NOTE: should probably use Content-Encoding from headers
         long endTime = System.currentTimeMillis();
         long duration = endTime - startTime;
         String body = bytes.toString("UTF-8");
         
         // Get current concurrent connection count and previous max concurrent connection count
         // Try and update the max value only if another thread has not already changed the max value
         // We lose some accuracy here
         long total = connectionCount.sum();
         long curr = maxConnectionCount.get();
         if (total > curr) {
             maxConnectionCount.compareAndSet(curr, total);
         }
         logger.info("Got(" + total + "): " + url + ": \"" + body + "\" in " + duration + "ms");
         
         QueueItem item = new QueueItem();
         item.setDateCreated(startTime);
         item.setDuration(duration);
         item.setUri(url);
         queue.add(item);
         CliClient.connectionCount.decrement();
         return duration;
    }
    
}
