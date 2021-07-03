package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.UUID;

/**
 * Bietet die Services der Modelklasse Flugzeug.java
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 05.06.2021
 */

@Path("flugzeug")
public class FlugzeugService {

    /**
     * Gibt die gespeicherten Daten aus
     * @return Text
     */
    @GET
    @Path("testliste")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test(
            @CookieParam("token") String token
    ){
        int status;
        String alles = "";

        String[] rollen = {"admin", "wartung", "benutzer"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            alles = "\nDIES DEMONSTRIERT, DASS DAS JSON RICHTIG GEPARSED UND GESPEICHERT WURDE!\n";
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
        }

        Response response = Response
                .status(status)
                .entity(alles)
                .build();

        return response;

    }


    /**
     * Gibt eine Liste aller Flugzeuge als JSON aus
     * @return JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllFlugzeuge(
            @CookieParam("token") String token
    ){
        int status;
        String returnValue = "";

        String[] rollen = {"admin", "wartung", "benutzer"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            returnValue = DataHandler.stringVonJSON(DataHandler.getFlugzeugMap());
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
                .build();

        return response;
    }


    /**
     * Gibt ein spezifiziertes Flugzeug als JSON aus
     * @param flugzeugUUID
     * @return JSON
     */
    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkFlugzeug(
            @QueryParam("flugzeugUUID") String flugzeugUUID,
            @CookieParam("token") String token
    ){
        int status;
        String returnValue = "";

        String[] rollen = {"admin", "wartung"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
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

            returnValue = DataHandler.stringVonJSON(flugzeug);
        }

        Response response = Response
                .status(status)
                .entity(returnValue)
                .build();

        return response;

    }


    /**
     * Fügt ein neues Flugzeug hinzu
     * @param herstellerUUID
     * @param airlineUUID
     * @param flugzeug
     * @return Text
     */
    @POST
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertFlugzeug(
            @FormParam("herstellerUUID") String herstellerUUID,
            @FormParam("airlineUUID") String airlineUUID,
            @Valid @BeanParam Flugzeug flugzeug,
            @CookieParam("token") String token
    ){
        int status;

        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            do {
                try {
                    //schaut, ob die UUIDs Formal korrekt sind & existieren
                    UUID.fromString(herstellerUUID);
                    if (airlineUUID.equals("-")){
                        airlineUUID = null;
                    }
                    if (airlineUUID != null) {
                        UUID.fromString(airlineUUID);
                    }
                    if (flugzeug.getFlugzeugUUID().equals("")){
                        flugzeug.setFlugzeugUUID(UUID.randomUUID().toString());
                    }

                    if (DataHandler.getHersteller(herstellerUUID) != null){         //schaut, ob der Hersteller existiert
                        flugzeug.setHersteller(DataHandler.getHersteller(herstellerUUID));  //setzt dem Flugzeug den Hersteller
                    }
                    else {      //Wenn hier, dann ist der Hersteller noch nicht vorhanden. Er muss zuerst noch erstellt werden
                        status = 404;   //Not found
                        break;
                    }

                    if (airlineUUID != null && DataHandler.getAirline(airlineUUID) != null){  //schaut ob die Airline existiert
                        flugzeug.setAirline(DataHandler.getAirline(airlineUUID));       //setzt dem Flugzeug die Airline
                        DataHandler.getAirline(airlineUUID).addFlugzeug(flugzeug);      //fügt das Flugzeug der Airline hinzu
                    }
                    else if (airlineUUID == null){        //wenn keine Airline vorhanden ist, dann gehört das Flugzeug dem Hersteller
                        DataHandler.getHersteller(herstellerUUID).addZuverkaufendeFlugzeuge(flugzeug);
                    }
                    else {      //Wenn hier, dann ist die Airline noch nicht vorhanden. Sie muss zuerst noch erstellt werden
                        status = 404;   //Not found
                        break;
                    }

                    DataHandler.insertFlugzeug(flugzeug);   //fügt das Flugzeug der HashMap hinzu


                } catch (IllegalArgumentException e){
                    status = 400;       //Bad Request
                }
            }
            while (false);
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Aktualisiert ein Flugzeug
     * Es müssen nur die veränderten Daten als Parameter übergeben werden
     * @param flugzeugUUID
     * @param airlineUUID
     * @param herstellerUUID
     * @param flugzeug
     * @return Text
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateFlugzeug(
            @FormParam("alteFlugzeugUUID") String flugzeugUUID,
            @FormParam("neueAirlineUUID") String airlineUUID,
            @FormParam("neueHerstellerUUID") String herstellerUUID,
            @Valid @BeanParam Flugzeug flugzeug,
            @CookieParam("token") String token
    ){
        int status;

        String[] rollen = {"admin", "wartung"};
        status = CheckCookie.checkCookie(token, rollen);

        if (status == 200){
            do {
                try {
                    UUID.fromString(flugzeugUUID);  //Schaut ob die gegebene UUID nicht null und formal korrekt ist

                    Flugzeug flugzeugImSpeicher = DataHandler.getFlugzeug(flugzeugUUID);      //Dies ist das Flugzeugobjekt, das abgeändert werden soll

                    if (flugzeugImSpeicher != null){

                        if (herstellerUUID != null){
                            if (DataHandler.getHersteller(herstellerUUID) == null){
                                status = 404;   //Not found
                                break;
                            }
                            else {
                                UUID.fromString(herstellerUUID);
                                flugzeug.setHersteller(DataHandler.getHersteller(herstellerUUID));
                                if (flugzeugImSpeicher.getAirline() == null){       //Wenn das Flugzeug neu der Airline gehört, und nicht mehr dem Hersteller, dann löscht es das Flugzeug aus dem Hersteller
                                    DataHandler.getHersteller(flugzeugImSpeicher.getHersteller().getHerstellerUUID()).rmZuverkaufendeFlugzeuge(flugzeugImSpeicher.getHersteller().getHerstellerUUID());
                                }
                                if (airlineUUID == null && flugzeugImSpeicher.getAirline() != null){        //Wenn das Flugzeug jetzt neu dem Hersteller gehört
                                    DataHandler.getAirline(flugzeugImSpeicher.getAirline().getAirlineUUID()).rmFlugzeug(flugzeugImSpeicher.getFlugzeugUUID());   //Löscht das Flugzeug aus der alten Airline
                                    DataHandler.getHersteller(herstellerUUID).addZuverkaufendeFlugzeuge(flugzeug);      //Fügt das Flugzeug der Liste vom Hersteller hinzu
                                }
                            }
                        }

                        if (airlineUUID != null){
                            if (airlineUUID.equals("-")){
                                airlineUUID = null;
                            }
                        }

                        if (airlineUUID != null){
                            if (DataHandler.getAirline(airlineUUID) == null){
                                status = 404;   //Not found
                                break;
                            }
                            else {
                                UUID.fromString(airlineUUID);
                                if (flugzeugImSpeicher.getAirline() != null){
                                    DataHandler.getAirline(flugzeugImSpeicher.getAirline().getAirlineUUID()).rmFlugzeug(flugzeugImSpeicher.getFlugzeugUUID());   //Löscht das Flugzeug aus der alten Airline
                                }
                                flugzeug.setAirline(DataHandler.getAirline(airlineUUID));   //Fügt die Airline dem Flugzeug hinzu
                                DataHandler.getAirline(airlineUUID).addFlugzeug(flugzeug);  //Fügt das Flugzeug der Airline hinzu
                            }
                        }

                        if (flugzeug.getFlugzeugUUID() == null){
                            flugzeug.setFlugzeugUUID(flugzeugImSpeicher.getFlugzeugUUID());
                        } else {
                            UUID.fromString(flugzeug.getFlugzeugUUID());    //Schaut ob die gegebene UUID formal korrekt ist
                        }


                        if (flugzeug.getFlugzeugtyp() == null){
                            flugzeug.setFlugzeugtyp(flugzeugImSpeicher.getFlugzeugtyp());
                        }

                        if (flugzeug.getHerstellungsdatum() == null){
                            flugzeug.setHerstellungsdatum(flugzeugImSpeicher.getHerstellungsdatum());
                        }


                        DataHandler.rmFlugzeug(flugzeugImSpeicher.getFlugzeugUUID());       //Löscht das alte Flugzeug aus dem Speicher (weil evtl. die UUID geändert wurde)
                        DataHandler.insertFlugzeug(flugzeug);       //Fügt das neue Flugzeug dem Speicher hinzu


                    }
                    else {
                        status = 404;   //Not found
                    }


                }
                catch (IllegalArgumentException e){
                    status = 400;   //Bad Request
                }
            }
            while (false);
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Entfernt ein Flugzeug
     * @param flugzeugUUID
     * @return Text
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteFlugzeug(
            @QueryParam("flugzeugUUID") String flugzeugUUID,
            @CookieParam("token") String token
    ){
        int status;

        status = CheckCookie.checkCookie(token, "admin");

        if (status == 200){
            try {
                UUID.fromString(flugzeugUUID);

                if (DataHandler.getFlugzeug(flugzeugUUID) != null){
                    Flugzeug flugzeug = DataHandler.getFlugzeug(flugzeugUUID);

                    //Wenn das Flugzeug in der HashMap des Herstellers vorhanden ist
                    if (DataHandler.getHersteller(flugzeug.getHersteller().getHerstellerUUID()).getZuverkaufendeFlugzeuge().containsKey(flugzeugUUID)){
                        DataHandler.getHersteller(flugzeug.getHersteller().getHerstellerUUID()).rmZuverkaufendeFlugzeuge(flugzeugUUID);
                    }

                    //Wenn das Flugzeug in der HashMap der Airline vorhanden ist
                    else if (DataHandler.getAirline(flugzeug.getAirline().getAirlineUUID()).getFlugzeuge().containsKey(flugzeugUUID)){
                        DataHandler.getAirline(flugzeug.getAirline().getAirlineUUID()).rmFlugzeug(flugzeugUUID);
                    }

                    DataHandler.rmFlugzeug(flugzeugUUID);
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