package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Hersteller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

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
        Response response = Response
                .status(200)
                .entity(DataHandler.stringVonJSON(DataHandler.getHerstellerMap()))
                .build();
        return response;
    }


    @GET
    @Path("check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHersteller(
            @QueryParam("herstellerUUID") String herstellerUUID
    ){
        int status = 200;
        Hersteller hersteller = null;

        try {
            UUID.fromString(herstellerUUID);          //schaut, ob die UUID formal korrekt ist, wenn nicht, schmeists IllegalArgumentException
            if (DataHandler.getHersteller(herstellerUUID) == null){     //schaut, ob die UUID mit einem Herstellerobjekt referenziert ist
                status = 404;       //Not found
            } else {
                hersteller = DataHandler.getHersteller(herstellerUUID);
            }
        } catch (IllegalArgumentException e){
            status = 400;       //Bad Request
        }
        finally {
            Response response = Response
                    .status(status)
                    .entity(DataHandler.stringVonJSON(hersteller))
                    .build();

            return response;
        }
    }
}
