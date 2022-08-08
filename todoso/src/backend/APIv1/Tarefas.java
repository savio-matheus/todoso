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

import backend.app.Tarefa;
import backend.persistencia.TarefaPersistencia;
import com.owlike.genson.stream.JsonStreamException;
import java.io.FileNotFoundException;

@Path("/tarefas")
public class Tarefas {

	@GET @Path("/{id}")
	@Produces({"application/json"})
	public Response getTask(@PathParam("id") String id) {
		try {
			Tarefa task = TarefaPersistencia.read(new Long(id));
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
		Tarefa task;
		try {
			task = new Genson().deserialize(json, Tarefa.class);
		} catch (Exception e) {
			return Response
				.status(Response.Status.BAD_REQUEST)
				.entity("{\"status\": 400}")
				.build();
		}
		try {
			Long id = TarefaPersistencia.write(task);
			return Response
					.status(Response.Status.CREATED)
					.contentLocation(new URI("/todoso-backend/api/v1/tarefas/" + id))
					.entity("{\"status\": \"201\"}")
					.build();
		}
		catch (IOException e) {
			System.out.println(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return Response
			.status(Response.Status.INTERNAL_SERVER_ERROR)
			.entity("{\"status\": 500}")
			.build();
	}
}