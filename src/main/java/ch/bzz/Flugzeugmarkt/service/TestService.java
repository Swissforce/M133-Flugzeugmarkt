package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;

import javax.ws.rs.CookieParam;
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
    public Response restore(
            @CookieParam("token") String token
    ){
        int status;
        String returnValue = "Nicht autorisiert";

        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            DataHandler.restoreData();
            returnValue = "Die Daten wurden zurueckgesetzt";
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
                .build();
        return response;

    }
}
