package ch.bzz.Flugzeugmarkt.service;

import ch.bzz.Flugzeugmarkt.data.BenutzerData;
import ch.bzz.Flugzeugmarkt.model.Benutzer;

/**
 * Beschreibung der Klasse
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.07.2021
 */

public class CheckCookie {
    /**
     * Schaut, ob der Token im Cookie die benötigte Rolle hat
     * @param token
     * @param rolle
     * @return status
     */
    public static int checkCookie(String token, String rolle){
        int status = 403;
        String decrypted = AESEncrypt.decrypt(token);
        String values[] = decrypted.split(";");

        if (BenutzerData.checkBenutzer(values[0], values[1]).getRolle().equals(rolle)){
            status = 200;
        }

        return status;
    }

    /**
     * Schaut, ob der Token im Cookie einer der benötigten Rollen hat
     * @param token
     * @param rollen
     * @return status
     */
    public static int checkCookie(String token, String[] rollen){
        int status = 403;

        if (token != null){
            String decrypted = AESEncrypt.decrypt(token);
            String values[] = decrypted.split(";");

            Benutzer benutzer = BenutzerData.checkBenutzer(values[0], values[1]);

            for (String rolle : rollen){
                if (benutzer.getRolle().equals(rolle)){
                    status = 200;
                    break;
                }
            }
        }
        else {
            status = 401;
        }
        return status;
    }
}
