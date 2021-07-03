package ch.bzz.Flugzeugmarkt.data;

import ch.bzz.Flugzeugmarkt.model.Benutzer;
import ch.bzz.Flugzeugmarkt.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Datahandler für den Benutzer
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.07.2021
 */

public class BenutzerData {
    private static final BenutzerData instance = new BenutzerData();


    /**
     * Schaut, ob der eingegebene Benutzer existiert und das richtige Passwort hat
     * @param benutzername
     * @param passwort
     * @return Benutzer
     */
    public static Benutzer checkBenutzer(String benutzername, String passwort) {
        Benutzer benutzer = new Benutzer();
        List<Benutzer> benutzerList = readJson();

        for (Benutzer entry: benutzerList) {
            if (entry.getBenutzername().equals(benutzername) && entry.getPasswort().equals(passwort)) {
                benutzer = entry;
                break;
            }
        }
        return benutzer;
    }

    /**
     * Liest die JSON-Datei mit den Benutzerlogins ein und gibt sie als Benutzer-Liste zurück
     * @return benutzerList
     */
    private static List<Benutzer> readJson() {
        List<Benutzer> benutzerList = new ArrayList<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("benutzerJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Benutzer[] benutzers = objectMapper.readValue(jsonData, Benutzer[].class);
            for (Benutzer benutzer : benutzers) {
                benutzerList.add(benutzer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return benutzerList;
    }
}
