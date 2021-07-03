package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;
import ch.bzz.Flugzeugmarkt.model.Hersteller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * Bietet die Services der Modelklasse Hersteller.java an
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

@Path("hersteller")
public class HerstellerService {

    /**
     * Gibt eine Liste aller Hersteller als JSON aus
     * @return JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listHerstellers(
            @CookieParam("token") String token
    ) {
        int status;
        String returnValue = "";

        String[] rollen = {"admin", "wartung", "benutzer"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            returnValue = DataHandler.stringVonJSON(DataHandler.getHerstellerMap());
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
                .build();
        return response;
    }


    /**
     * Gibt einen spezifizierten Hersteller als JSON aus
     * @param herstellerUUID
     * @return JSON
     */
    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHersteller(
            @QueryParam("herstellerUUID") String herstellerUUID,
            @CookieParam("token") String token
    ) {
        int status;
        String returnValue = "";

        String[] rollen = {"admin", "wartung"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            Hersteller hersteller = null;

            try {
                UUID.fromString(herstellerUUID);          //schaut, ob die UUID formal korrekt ist, wenn nicht, schmeists IllegalArgumentException
                if (DataHandler.getHersteller(herstellerUUID) == null) {     //schaut, ob die UUID mit einem Herstellerobjekt referenziert ist
                    status = 404;       //Not found
                } else {
                    hersteller = DataHandler.getHersteller(herstellerUUID);
                }
            } catch (IllegalArgumentException e) {
                status = 400;       //Bad Request
            }
            returnValue = DataHandler.stringVonJSON(hersteller);
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
                .build();

        return response;

    }


    /**
     * Fügt einen neuen Hersteller hinzu
     * @param hersteller
     * @return Text
     */
    @POST
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertHersteller(
            @Valid @BeanParam Hersteller hersteller,
            @CookieParam("token") String token
    ){
        int status;
        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            DataHandler.insertHersteller(hersteller);
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Aktualisiert einen Hersteller
     * Es müssen nur die veränderten Daten als Parameter übergeben werden
     * @param herstellerUUID
     * @param hersteller
     * @return Text
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateHersteller(
            @FormParam("alteHerstellerUUID") String herstellerUUID,
            @Valid @BeanParam Hersteller hersteller,
            @CookieParam("token") String token
    ){
        int status;

        String[] rollen = {"admin", "wartung"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            try {
                UUID.fromString(herstellerUUID);

                if (DataHandler.getHersteller(herstellerUUID) != null){
                    if (hersteller.getHerstellerUUID() != null){
                        DataHandler.getHersteller(herstellerUUID).setHerstellerUUID(hersteller.getHerstellerUUID());
                    }
                    if (hersteller.getName() != null){
                        DataHandler.getHersteller(herstellerUUID).setName(hersteller.getName());
                    }
                    if (hersteller.getGruendungsdatum() != null){
                        DataHandler.getHersteller(herstellerUUID).setGruendungsdatum(hersteller.getGruendungsdatum());
                    }

                    Hersteller alterHersteller = DataHandler.getHerstellerMap().remove(herstellerUUID);     //damit sich der Key auch ändert
                    DataHandler.insertHersteller(alterHersteller);
                }
                else {
                    status = 404;   //Not found
                }
            }
            catch (IllegalArgumentException e){
                status = 400;   //Bad Request
            }
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Entfernt einen Hersteller
     * @param herstellerUUID
     * @return Text
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteHersteller(
            @QueryParam("herstellerUUID") String herstellerUUID,
            @CookieParam("token") String token
    ){
        int status;
        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            try {
                UUID.fromString(herstellerUUID);

                if (DataHandler.getHersteller(herstellerUUID) != null){

                    for (Map.Entry<String, Flugzeug> flugzeug : DataHandler.getFlugzeugMap().entrySet()){
                        if (flugzeug.getValue().getHersteller() == DataHandler.getHersteller(herstellerUUID)){  //Wenn das Flugzeug eine Referenz zum Hersteller hat
                            flugzeug.getValue().setHersteller(null);       //Entfernt die Referenz vom Flugzeug zum Hersteller
                        }
                    }

                    DataHandler.rmHersteller(herstellerUUID);
                }
                else {
                    status = 404;   //Not found
                }
            }
            catch (IllegalArgumentException e){
                status = 400;   //Bad Request
            }
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }
}