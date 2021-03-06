package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Diese Klasse bietet verschiedene Methoden an, die für Testing-Zwecke gedacht sind
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
    public Response test(
            @CookieParam("token") String token
    ) {
        int status;
        String returnValue = "Nicht autorisiert";

        String[] rollen = {"admin", "wartung"};
        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            returnValue = "hurrah! Der Test hat funktioniert";
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
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
