
package edu.csula.cs594.client;

import edu.csula.cs594.client.pojo.StatusResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/loadtest/")
public class LoadTestResource  {
    
    @GET
    @Path("start")        
    @Produces(MediaType.APPLICATION_JSON)
    public Response startTest( @QueryParam("uri") String uri, @QueryParam("count") int requestCount, @QueryParam("projectid") int projectid) {
        
        CliClient cli = new CliClient();
        cli.simulateLoad(uri, requestCount, projectid);
        
        System.out.println("Waiting...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadTestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Closing...");
        
        
        StatusResponse r = new StatusResponse();
        r.setUri(uri);
        r.setNumberOfCalls(requestCount);
        r.setStatus("done");
        r.setRequestType("startLoadTest");
        
        return Response.ok().entity(r).build();
    }


    @GET
    @Path("stop")        
    @Produces(MediaType.APPLICATION_JSON)
    public Response stopTest( @QueryParam("uri") String uri) {
        
        StatusResponse r = new StatusResponse();
        r.setUri(uri);
        r.setStatus("Not Implemented");
        r.setRequestType("stopLoadTest");
        
        return Response.ok().entity(r).build();
    }

    @GET
    @Path("status")        
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus(@QueryParam("uri") String uri) {
        
        StatusResponse r = new StatusResponse();
        r.setUri(uri);
        r.setStatus("Not Implemented");
        r.setRequestType("getStatus");
        
        return Response.ok().entity(r).build();
    }
    
    
}
