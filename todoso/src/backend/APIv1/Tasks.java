package backend.APIv1;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.owlike.genson.Genson;

import backend.app.Task;
import backend.app.TaskPersistence;
import java.io.FileNotFoundException;

@Path("/task")
public class Tasks {

	@GET @Path("/{id}")
	@Produces({"application/json"})
	public Response getTask(@PathParam("id") String id) {
		try {
			Task task = TaskPersistence.read(new Long(id));
			System.out.println(task.toString());
			if (task == null) {
				throw new FileNotFoundException();
			}

			return Response
				.status(Response.Status.FOUND)
				.entity(task)
				.build();
		} catch (FileNotFoundException e) {
			return Response
				.status(Response.Status.NOT_FOUND)
				.entity("{}")
				.build();
		} catch (IOException e) {
			return Response
				.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity("{}")
				.build();
		}
	}

	@POST
	@Produces({"application/json"})
	public Response postTask(String json) {
		Task task = new Genson().deserialize(json, Task.class);
		try {
			Long id = TaskPersistence.write(task);
			return Response
					.status(Response.Status.CREATED)
					.contentLocation(new URI("/todoso-backend/api/v1/task/" + id))
					.entity("{\"status\": \"OK\"}")
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
