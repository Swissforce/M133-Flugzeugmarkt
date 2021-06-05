package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

/**
 * Bietet die Services der Modelklasse Flugzeug.java
 * beinhaltet KEINE create-/update-/remove- etc. Methoden, da dies über Airline/Hersteller gemanaged wird
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 05.06.2021
 */

@Path("flugzeug")
public class FlugzeugService {

    @GET
    @Path("testliste")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test(){

        String alles = "\nDIES DEMONSTRIERT, DASS DAS JSON RICHTIG GEPARSED UND GESPEICHERT WURDE!\n";
        for (HashMap.Entry entry : DataHandler.getFlugzeugMap().entrySet()){
            Flugzeug x = (Flugzeug) entry.getValue();
            if (x.getAirline() != null){
                alles += "\n\nFlugzeug: \n" + "\tUUID: " + x.getFlugzeugUUID() + "\n\tTyp: " + x.getFlugzeugtyp() + "\n\tHerstellungsdatum: " + x.getHerstellungsdatum() + "\n"
                        + "\n\tHersteller: \n" + "\t\tUUID: " + x.getHersteller().getHerstellerUUID() + "\n\t\tName: " + x.getHersteller().getName() + "\n\t\tGruendungsdatum: " + x.getHersteller().getGruendungsdatum() + "\n"
                        + "\n\tAirline: \n" + "\t\tUUID: " + x.getAirline().getAirlineUUID() + "\n\t\tName: " + x.getAirline().getName() + "\n\t\tGruendungsdatum: " + x.getAirline().getGruendungsdatum() + "\n"
                        + "--------------------------------";
            }
            else {
                alles += "\n\nFlugzeug: \n" + "\tUUID: " + x.getFlugzeugUUID() + "\n\tTyp: " + x.getFlugzeugtyp() + "\n\tHerstellungsdatum: " + x.getHerstellungsdatum() + "\n"
                        + "\n\tHersteller: \n" + "\t\tUUID: " + x.getHersteller().getHerstellerUUID() + "\n\t\tName: " + x.getHersteller().getName() + "\n\t\tGruendungsdatum: " + x.getHersteller().getGruendungsdatum() + "\n"
                        + "--------------------------------";
            }

        }

        Response response = Response
                .status(200)
                .entity(alles)
                .build();

        return response;

    }


    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllFlugzeuge() {

        Response response = Response
                .status(200)
                .entity(DataHandler.stringVonJSON(DataHandler.getFlugzeugMap()))
                .build();

        return response;
    }


    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkFlugzeug(
            @QueryParam("flugzeugUUID") String flugzeugUUID
    ){
        int status = 200;
        Flugzeug flugzeug = null;

        try {
            UUID.fromString(flugzeugUUID);          //schaut, ob die UUID formal korrekt ist, wenn nicht, schmeists IllegalArgumentException
            if (DataHandler.getFlugzeug(flugzeugUUID) == null){     //schaut, ob die UUID mit einem Flugzeugobjekt referenziert ist
                status = 404;       //Not found
            } else {
                flugzeug = DataHandler.getFlugzeug(flugzeugUUID);
            }
        } catch (IllegalArgumentException e){
            status = 400;       //Bad Request
        }
        finally {
            Response response = Response
                    .status(status)
                    .entity(DataHandler.stringVonJSON(flugzeug))
                    .build();

            return response;
        }
    }
}
