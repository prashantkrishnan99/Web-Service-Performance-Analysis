
package edu.csula.cs594.client;

import edu.csula.cs594.client.core.DatabaseClient;
import edu.csula.cs594.client.pojo.ProjectResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/project/")
public class ProjectResource  {
    
    @GET
    @Path("create")        
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProject( @QueryParam("uri") String uri, @QueryParam("projectname") String projectName, @QueryParam("count") int count) {

        DatabaseClient dbClient = DatabaseClient.getInstance();

        try {
            dbClient.createProject(projectName, uri, count);
            
            ProjectResponse result = new ProjectResponse();
            result.setProjectname(projectName);
            result.setUri(uri);
            result.setRequestCount(count);
            result.setStatus("OK");
            
            return Response.ok().entity(result).build();
        } catch (SQLException ex) {
            // Logger.getLogger(ProjectResource.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        ProjectResponse result = new ProjectResponse();
        result.setProjectname(projectName);
        result.setUri(uri);
        result.setStatus("ERROR");

        return Response.serverError().entity(result).build();
        
    }


    @GET
    @Path("list")        
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProjects() {
        
        DatabaseClient dbClient = DatabaseClient.getInstance();
                        
        List<ProjectResponse> projects;
        try {
            projects = dbClient.getProjectList();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
        
        return Response.ok().entity(projects).build();
    }
       
}
