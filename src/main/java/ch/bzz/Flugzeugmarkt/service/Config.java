package ch.bzz.Flugzeugmarkt.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Konfiguriert den Webservice
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

@ApplicationPath("/markt")
public class Config extends Application {

    private static final String PROPERTIES_FILENAME = "flugzeugmarkt.properties";
    private static Properties properties = null;

    /**
     * Definiert alle Service-Klassen
     * Nur dadurch weiss das Jersey-Framework, in welchen Klassen es nach Services suchen muss.
     *
     * @return alle Service-Klassen
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet providers = new HashSet<Class<?>>();
        providers.add(TestService.class);
        providers.add(AirlineService.class);
        providers.add(HerstellerService.class);
        return providers;
    }

    /**
     * Gets the value of a property
     *
     * @param property the key of the property to be read
     * @return the value of the property
     */
    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return getResourcePath() + File.separator + value;
    }

    /**
     * gets the resource path
     */
    private static Path getResourcePath() {
        String pathname = Config.class.getClassLoader().getResource(PROPERTIES_FILENAME).getFile();
        Path path = new File(pathname).toPath().getParent();
        return path;
    }

    /**
     * reads the properties file
     */
    private static void readProperties() {

        InputStream inputStream = null;
        try {
            inputStream = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {

            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Sets the properties
     *
     * @param properties the value to set
     */
    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
