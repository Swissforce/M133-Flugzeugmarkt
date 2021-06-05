package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Hersteller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Bietet die Services der Modelklasse Hersteller.java
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

@Path("hersteller")
public class HerstellerService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listHerstellers(){
        Map<String, Hersteller> herstellerMap = DataHandler.getHerstellerMap();
        Response response = Response
                .status(200)
                .entity(herstellerMap)
                .build();
        return response;
    }
}
