package sorting.sorter;

import java.util.Random;

/**
 * Diese Klasse modelliert einen Tag in einem unbestimmten Jahr, d.h. ohne
 * Angabe des Jahres selbst (z.B. den 1. April oder den 29. Februar).
 * 
 * @author Axel Schmolitzky
 * @version 2023
 */
public class Tag implements Comparable<Tag>
{
    public static final int TAGE_PRO_JAHR = 366;

    public static final int[] TAGE_PRO_MONAT = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static final String[] MONATSNAME = { "Januar", "Februar", "Maerz", "April", "Mai",
            "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };

    private static final Random ZUFALL = new Random();
    
    private final int _tagNummer;

    /**
     * Erstellt ein neues Tag-Objekt.
     * 
     * @param tagNummer der Tag des Jahres im Intervall [0,365].
     * 0 steht dabei fuer den 1. Januar, 365 fuer den 31. Dezember.
     */
    public Tag(int tagNummer)
    {
        if ((tagNummer < 0) || (tagNummer >= TAGE_PRO_JAHR))
        {
            throw new IllegalArgumentException(String.valueOf(tagNummer));
        }
        _tagNummer = tagNummer;
    }

    /**
     * Liefert die Nummer eines Tages im Jahr. Der 1. Januar liefert 0,
     * der 31. Dezember 365.
     */
    public int nummer()
    {
        return _tagNummer;
    }

    /**
     * Zwei Exemplare dieser Klasse, die denselben Tag im Jahr repraesentieren,
     * werden als gleich angesehen.
     */
    public boolean equals(Object object)
    {
        boolean result = false;
        if (object instanceof Tag)
        {
            Tag uebergebenerTag = (Tag) object;
            result = (_tagNummer == uebergebenerTag._tagNummer);
        }
        return result;
    }

    /**
     * @see Object.hashCode()
     */
    public int hashCode()
    {
        return _tagNummer;
    }

    /**
     * @see Object.toString()
     */
    public String toString()
    {
        int monat = 0;
        int tagNummer = _tagNummer;
        while (tagNummer >= TAGE_PRO_MONAT[monat])
        {
            tagNummer -= TAGE_PRO_MONAT[monat++];
        }
        return (tagNummer + 1) + ". " + MONATSNAME[monat];
    }
    
    public int compareTo(Tag other)
    {
        return _tagNummer - other._tagNummer;
    }

    /**
     * @return ein zufaelliger Tag im Jahr
     */
    public static Tag gibZufaelligenTag()
    {
        int tagNummer;
        do
        {
            tagNummer = ZUFALL.nextInt(TAGE_PRO_JAHR);
        }
        while (tagNummer == 59 && ZUFALL.nextInt(400) >= 97); 
        // Der Tag mit der Nummer 59 ist der 29. Februar. Er darf nur 
        // mit einer Wahrscheinlichkeit von 97/400 erzeugt werden, 
        // da er in 400 Jahren genau 97x vorkommt. 
        return new Tag(tagNummer);
    }

}