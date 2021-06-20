package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Airline;
import ch.bzz.Flugzeugmarkt.model.Flugzeug;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * Bietet die Services der Modelklasse Airline.java
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

@Path("airline")
public class AirlineService {

    /**
     * Gibt eine Liste aller Airlines als JSON aus
     * @return JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAirlines(){

        Response response = Response
                .status(200)
                .entity(DataHandler.stringVonJSON(DataHandler.getAirlineMap()))
                .build();
        return response;
    }


    /**
     * Gibt eine spezifizierte Airline als JSON aus
     * @param airlineUUID
     * @return JSON
     */
    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkAirline(
            @QueryParam("airlineUUID") String airlineUUID
    ){
        int status = 200;
        Airline airline = null;

        try {
            UUID.fromString(airlineUUID);          //schaut, ob die UUID formal korrekt ist, wenn nicht, schmeists IllegalArgumentException
            if (DataHandler.getAirline(airlineUUID) == null){     //schaut, ob die UUID mit einem Airlineobjekt referenziert ist
                status = 404;       //Not found
            } else {
                airline = DataHandler.getAirline(airlineUUID);
            }
        } catch (IllegalArgumentException e){
            status = 400;       //Bad Request
        }
        finally {
            Response response = Response
                    .status(status)
                    .entity(DataHandler.stringVonJSON(airline))
                    .build();

            return response;
        }
    }


    /**
     * Fügt eine neue Airline hinzu
     * @param airline
     * @return Text
     */
    @POST
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertAirline(
            @Valid @BeanParam Airline airline
    ){
        int status = 200;

        DataHandler.insertAirline(airline);

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Aktualisiert eine Airline
     * Es müssen nur die veränderten Daten als Parameter übergeben werden
     * @param airlineUUID
     * @param airline
     * @return Text
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAirline(
            @FormParam("alteAirlineUUID") String airlineUUID,
            @Valid @BeanParam Airline airline
    ){
        int status = 200;

        try {
            UUID.fromString(airlineUUID);

            if (DataHandler.getAirline(airlineUUID) != null){
                if (airline.getAirlineUUID() != null){
                    DataHandler.getAirline(airlineUUID).setAirlineUUID(airline.getAirlineUUID());
                }
                if (airline.getGruendungsdatum() != null){
                    DataHandler.getAirline(airlineUUID).setGruendungsdatum(airline.getGruendungsdatum());
                }
                if (airline.getName() != null){
                    DataHandler.getAirline(airlineUUID).setName(airline.getName());
                }

                Airline alteAirline = DataHandler.getAirlineMap().remove(airlineUUID);      //damit sich der Key auch ändert
                DataHandler.insertAirline(alteAirline);

            }
            else {
                status = 404;   //Not found
            }
        }
        catch (IllegalArgumentException e){
            status = 400;   //Bad Request
        }

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    /**
     * Entfernt eine Airline
     * @param airlineUUID
     * @return Text
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAirline(
            @QueryParam("airlineUUID") String airlineUUID
    ){
        int status = 200;

        try {
            UUID.fromString(airlineUUID);

            if (DataHandler.getAirline(airlineUUID) != null){

                if (DataHandler.getAirline(airlineUUID).getFlugzeuge() != null){    //Wenn die Airline Flugzeuge hat
                    for (Map.Entry<String,Flugzeug> flugzeug : DataHandler.getAirline(airlineUUID).getFlugzeuge().entrySet()){
                        flugzeug.getValue().setAirline(null);       //Entfernt die Referenz vom Flugzeug zur Airline
                    }
                }

                DataHandler.rmAirline(airlineUUID);
            }
            else {
                status = 404;   //Not found
            }
        }
        catch (IllegalArgumentException e){
            status = 400;   //Bad Request
        }
        finally {
            Response response = Response
                    .status(status)
                    .entity("")
                    .build();

            return response;
        }
    }

}