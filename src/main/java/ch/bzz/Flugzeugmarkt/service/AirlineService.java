package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Airline;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

}
