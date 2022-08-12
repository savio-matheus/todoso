package backend.APIv1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/olar")
public class HelloWorld {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getOlar() {
		return "<h1>Hello, World!</h1>";
	}
}
