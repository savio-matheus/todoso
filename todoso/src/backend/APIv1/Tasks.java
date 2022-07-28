package backend.APIv1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.owlike.genson.Genson;

import backend.app.Task;
import backend.app.TaskPersistence;

@Path("/task")
public class Tasks {

	@GET @Path("/{id}")
	@Produces({"application/json"})
	public String getTask(@PathParam("id") String id) {
		return "{\"id\": " + id + "}";
	}

	@POST
	@Produces({"application/json"})
	public Response postTask(String json) {
		Task task = new Genson().deserialize(json, Task.class);
		try {
			Long id = TaskPersistence.write(task);
			return Response
					.status(Response.Status.CREATED)
					.entity("/todoso-backend/api/v1/task/" + id)
					.build();
		}
		catch (IOException e) {
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
}
