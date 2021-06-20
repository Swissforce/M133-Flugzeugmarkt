package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Testklasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        Response response = Response
                .status(200)
                .entity("hurrah! Der Test hat funktioniert")
                .build();
        return response;
    }

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore(){
        DataHandler.restoreData();

        Response response = Response
                .status(200)
                .entity("Die Daten wurden zurückgesetzt")
                .build();
        return response;

    }
}
