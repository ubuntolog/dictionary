package top.alexcloud.resources;

import top.alexcloud.BackendConfiguration;
import top.alexcloud.core.Database;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Path("")
public class MiscResource {
    private static final ch.qos.logback.classic.Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MiscResource.class);

    private final BackendConfiguration config;

    private final Database db;

    public MiscResource(BackendConfiguration config) {
        this.config = config;
        this.db = new Database(config);
    }

    protected void finalize ()  {
        db.closeConnection();
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
    @Path("/dictionary/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDictInfo(@PathParam("id") Integer id) {
//        Database db = new Database(config);
        LOGGER.info("Information about {} dictionary was requested", id);
        HashMap<Integer,String> results = db.findDictInfo(id);
        if (results.size() > 0) {
            return Response.ok(results).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Dictionary was not found").build();
        }
    }

    @POST
    @Path("/word")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response runQuery(@FormDataParam("query") final String query) {
//        Database db = new Database(config);
        HashMap<Integer,String> results = db.findWord(query);

        LOGGER.info("Trying to find a word / similar words");
//        db.closeConnection();
        return Response.ok(results).build();
    }
}
