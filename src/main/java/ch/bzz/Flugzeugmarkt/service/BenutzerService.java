package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.BenutzerData;
import ch.bzz.Flugzeugmarkt.model.Benutzer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
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
     * Schaut ob ein Benutzer autorisiert ist und setzt das Cookie
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

        if (benutzer.getBenutzerUUID() == null){
            status = 404;   //Not found
        }

        NewCookie cookie = new NewCookie(
                "token",
                AESEncrypt.encrypt(benutzername + ";" + passwort),
                "/",
                "",
                "Auth-Token",
                600,
                false);



        Response response = Response
                .status(status)
                .entity("")
                .cookie(cookie)
                .build();

        return response;
    }


    /**
     * Der Benutzer wird abgemeldet indem das Cookie gelöscht wird
     * @return
     */
    @DELETE
    @Path("logoff")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoff(){
        int status = 200;

        NewCookie cookie = new NewCookie(
                "token",
                "",
                "/",
                "",
                "Auth-Token",
                1,
                false);

        Response response = Response
                .status(status)
                .entity("")
                .cookie(cookie)
                .build();

        return response;
    }
}
