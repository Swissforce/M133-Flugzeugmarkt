package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.BenutzerData;
import ch.bzz.Flugzeugmarkt.model.Benutzer;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Bietet die Services der Modelklasse Benutzer.java an
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.07.2021
 */

@Path("benutzer")
public class BenutzerService {

    /**
     * Schaut ob ein Benutzer autorisiert ist
     * @param benutzername
     * @param passwort
     * @return Text
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("benutzername") String benutzername,
            @FormParam("passwort") String passwort
    ) {
        int status = 200;

        Benutzer benutzer = BenutzerData.checkBenutzer(benutzername, passwort);

        if (benutzer.getBenutzerUUID().isEmpty()){
            status = 404;   //Not found
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


}
