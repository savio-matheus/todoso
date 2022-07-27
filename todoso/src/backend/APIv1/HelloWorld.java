package backend;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/olar")
public class HelloWorld {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getOlar() {
		return "Hello, World!";
	}
}
