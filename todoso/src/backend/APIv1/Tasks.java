package backend.APIv1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/task")
public class Tasks {

	@GET @Path("/{id}")
	@Produces({"application/json"})
	public String getTask(@PathParam("id") String id) {
		return "{\"id\": " + id + "}";
	}

	@POST
	@Produces({"application/json"})
	public String postTask() {
		return "{}";
	}
}
