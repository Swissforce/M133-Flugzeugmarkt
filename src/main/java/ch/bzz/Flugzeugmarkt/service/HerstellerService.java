package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.DataHandler;
import ch.bzz.Flugzeugmarkt.model.Hersteller;

import javax.validation.Valid;
import javax.ws.rs.*;
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
    public Response listHerstellers() {
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
    ) {
        int status = 200;
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
        } finally {
            Response response = Response
                    .status(status)
                    .entity(DataHandler.stringVonJSON(hersteller))
                    .build();

            return response;
        }
    }


    @POST
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertHersteller(
            @Valid @BeanParam Hersteller hersteller
    ){
        int status = 200;

        DataHandler.insertHersteller(hersteller);

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateHersteller(
            @FormParam("alteHerstellerUUID") String herstellerUUID,
            @Valid @BeanParam Hersteller hersteller
    ){
        int status = 200;

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

        Response response = Response
                .status(status)
                .entity("")
                .build();

        return response;
    }


}