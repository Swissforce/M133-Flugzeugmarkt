package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

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
            alles += "\n\nFlugzeug: \n" + "\tUUID: " + x.getFlugzeugUUID() + "\n\tTyp: " + x.getFlugzeugtyp() + "\n\tHerstellungsdatum: " + x.getHerstellungsdatum() + "\n"
                    + "\n\tHersteller: \n" + "\t\tUUID: " + x.getHersteller().getHerstellerUUID() + "\n\t\tName: " + x.getHersteller().getName() + "\n\t\tGruendungsdatum: " + x.getHersteller().getGruendungsdatum() + "\n"
                    + "\n\tAirline: \n" + "\t\tUUID: " + x.getAirline().getAirlineUUID() + "\n\t\tName: " + x.getAirline().getName() + "\n\t\tGruendungsdatum: " + x.getAirline().getGruendungsdatum() + "\n"
                    + "--------------------------------";
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


}
