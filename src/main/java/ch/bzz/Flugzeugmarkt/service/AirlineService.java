package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Airline;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

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
        Map<String, Airline> airlineMap = DataHandler.getAirlineMap();
        //Map<String, Airline> test = new HashMap<>();
        Response response = Response
                .status(200)
                .entity(airlineMap)
                .build();
        return response;
    }

}
