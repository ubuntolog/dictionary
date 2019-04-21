package top.alexcloud.resources;

import top.alexcloud.BackendConfiguration;
import top.alexcloud.core.Database;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;
import top.alexcloud.core.Match;
import top.alexcloud.core.Query;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    // Get information about a dictionary (e.g. description)
    @GET
    @Path("/dictionary/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getDictInfo(@PathParam("id") Integer id) {
        LOGGER.info("Information about {} dictionary was requested", id);
        HashMap<Integer,String> results = db.findDictInfo(id);
        if (results.size() > 0) {
            return Response.ok(results).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Dictionary was not found").build();
    }

    // Start a query to find word/words in dictionaries that match our cirteria
    @POST
    @Path("/query")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response runQuery(@FormDataParam("query") final String query) {
        Query currentQuery = db.findWord(query);
        LOGGER.info("Trying to find a word / similar words");
        return Response.ok(currentQuery).build();
    }

    // Get information about a query: id, query text, state, number of found results, etc.
    @GET
    @Path("/query/{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getQueryInfo(@PathParam("id") String id) {
        LOGGER.info("Information about query {} was requested", id);
        Query requestedQuery = db.findQueryInfo(id);
        if (requestedQuery != null) {
            return Response.ok(requestedQuery).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Query was not found").build();
    }

    // Get query results
    @GET
    @Path("/query/{id}/matches")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getQueryMatches(@PathParam("id") String id) {
        LOGGER.info("Information about query matches for the query {} was requested", id);
        HashMap<Integer, Match> queryMatches = db.findQueryMatches(id);
        if (queryMatches != null) {
            return Response.ok(queryMatches).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("No query matches found").build();
    }
}
