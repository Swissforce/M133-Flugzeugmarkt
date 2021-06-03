package ch.bzz.Flugzeugmarkt.model;

import java.util.Vector;

/**
 * Model der Airline
 *
 * @author Martin Düppenbecker
 * @version 1.0
 * @since 03.06.2021
 */

public class Airline {
    private String name;
    private String gruendungsdatum;
    private Vector<Flugzeug> flugzeuge;


    //Konstruktoren

    /**
     * ausführlicher Konstruktor
     * @param name
     * @param gruendungsdatum
     */
    public Airline(String name, String gruendungsdatum){
        this.name = name;
        this.gruendungsdatum = gruendungsdatum;
        flugzeuge = new Vector<>();
    }

    /**
     * minimalistischer Konstruktor
     * @param name
     */
    public Airline(String name){
        this.name = name;
        flugzeuge = new Vector<>();
    }

    /**
     * leerer Konstruktor
     */
    public Airline(){
        flugzeuge = new Vector<>();
    }


    //Getter & Setter

    /**
     * gibt den Namen
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt das Gründungsdatum
     * @return gruendungsdatum
     */
    public String getGruendungsdatum() {
        return gruendungsdatum;
    }

    /**
     * setzt das Gründungsdatum
     * @param gruendungsdatum
     */
    public void setGruendungsdatum(String gruendungsdatum) {
        this.gruendungsdatum = gruendungsdatum;
    }

    /**
     * gibt ein, mit Index spezifiziertes, Flugzeug vom Vector flugzeuge zurück
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param index
     * @return flugzeug
     * @throws IllegalArgumentException
     */
    public Flugzeug getFlugzeug(int index) throws IllegalArgumentException {
        try {
            return flugzeuge.get(index);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
    }


    //Methoden

    /**
     * aktualisiert ein, mit Index spezifiziertes, Flugzeug vom Vector flugzeuge.
     * Dies geschieht, indem das alte Flugzeugobjekt mit einem neuen ersetzt wird.
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param flugzeug
     * @param index
     * @throws IllegalArgumentException
     */
    public void updateFlugzeug(Flugzeug flugzeug, int index) throws IllegalArgumentException {
        try {
            flugzeuge.get(index);   //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
        flugzeuge.set(index,flugzeug);
    }

    /**
     * entfernt ein Flugzeug vom Vector flugzeuge, mithilfe des Indexes
     * Wenn der Index out of bounds ist, wird diese Exception in eine IllegalArgumentException umgewandelt.
     * @param index
     * @throws IllegalArgumentException
     */
    public void rmFlugzeug(int index) throws IllegalArgumentException {
        try {
            flugzeuge.get(index);   //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException();
        }
        flugzeuge.remove(index);
    }

    /**
     * entfernt ein Flugzeug vom Vector flugzeuge, mithilfe der Objektes
     * Wenn das Objekt nicht im Vector flugzeuge vorhanden ist, schmeisst es eine IllegalArgumentException
     * @param flugzeug
     * @throws IllegalArgumentException
     */
    public void rmFlugzeug(Flugzeug flugzeug) throws IllegalArgumentException {
        if (flugzeuge.contains(flugzeug)){  //Schaut, ob das Flugzeug mit dem spezifizierten Index existiert
            flugzeuge.remove(flugzeug);
        }
        else {
            throw new IllegalArgumentException();
        }

    }


    //TODO Methode für den Kauf von Flugzeugen über den Hersteller
}
