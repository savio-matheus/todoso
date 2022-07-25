package backend.APIv1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/olar")
public class HelloWorld {
	static final String OLAR_RESPONSE = "{\"name\":\"Welcome\", \"message\":\"Hello, World!\"}\n";

	@GET
	@Produces({"application/json"})
	public String getOlar() {
		return OLAR_RESPONSE;
	}
	
	@POST
	public String postOlar() {
		return getOlar();
	}
}
