package top.alexcloud.resources;

import top.alexcloud.core.Booking;
import top.alexcloud.BackendConfiguration;
import top.alexcloud.core.Database;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("")
public class MiscResource {
    private static final ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MiscResource.class);

    private final BackendConfiguration config;

    public MiscResource(BackendConfiguration config) {
        this.config = config;
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getApiInfo() {
        Map map = new HashMap<String, Object>() {{
            this.put("version", config.getVersion());
            this.put("author", "ubuntolog");
        }};
        LOGGER.info("API info was requested");
        return Response.ok(map).build();
    }


    @GET
    @Path("/word/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getBooking(@PathParam("id") Integer id) throws SQLException {
        Database db = new Database(config);
        LOGGER.info("A word was requested");

//        List<Booking> foundBookings = db.selectBooking(id);
//        if (foundBookings.size()>0) {
//            return Response.ok(foundBookings).build();
//        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("The booking was not found").build();
//        }
    }

    @POST
    @Path("/word")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response makeBooking(@FormDataParam("query") final String query) {

        LOGGER.info("Trying to find a word / similar words");
        return Response.ok().build();
    }
}
