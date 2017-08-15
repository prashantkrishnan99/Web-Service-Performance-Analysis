package edu.csula.cs594.client.core;

import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import edu.csula.cs594.client.CliClient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);    
    
    AsyncHttpClient asyncHttpClient;

    public HttpClient() {
        
        Builder builder = new AsyncHttpClientConfig.Builder();
        builder.setCompressionEnforced(false)
                .setAllowPoolingConnections(true)
                .setRequestTimeout(30000)
                .build();
        
        asyncHttpClient = new AsyncHttpClient(builder.build());
        
        logger.info("Max connections: " + asyncHttpClient.getConfig().getMaxConnections());
        
    }

    public void makeHttpCall(String url, int projectid) throws InterruptedException, ExecutionException, IOException, SQLException {
        // get start time
        final long start = System.currentTimeMillis();

        // make http request
        CliClient.connectionCount.increment();        
        Future<Long> f = asyncHttpClient.prepareGet(url).execute(new LocalAsyncHandler(CliClient.queue, url, start, projectid));               
    }

    public void close() {
        asyncHttpClient.close();        
    }
    
}
