import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * La classe DateTime rappresenta una data e ora specifica, supportando varie operazioni di manipolazione e formattazione.
 * Implementa l'interfaccia Comparable per consentire l'ordinamento delle date.
 */
public class DateTime implements Comparable<DateTime>, Serializable {
    private Date date;
    /**
     * Costruttore di base che inizializza la data corrente.
     */
    public DateTime() {
        this.date = new Date();
    }

    /**
     * Costruttore che inizializza la data con un timestamp specifico.
     * @param timestamp Il timestamp in millisecondi dall'epoch. (con epoch intendo il primo gennaio 1970, ore 00:00:00 UTC, data standard
     * nei vari sistemi informatici)
     */
    public DateTime(long timestamp) {
        this.date = new Date(timestamp);
    }

    /**
     * Costruttore che inizializza la data con un oggetto Date specifico.
     * @param date L'oggetto Date per inizializzare il DateTime.
     */
    public DateTime(Date date) {
        this.date = date;
    }

    /**
     * Crea un oggetto DateTime parsando una stringa di data in vari formati.
     * @param dateString La stringa della data da parsare.
     * @return Un oggetto DateTime.
     * @throws IllegalArgumentException se la stringa non può essere parsata.
     */
    public static DateTime create(String dateString) {

        String[] formats = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd",
                "dd/MM/yyyy HH:mm:ss",
                "dd/MM/yyyy"
        };
        for (String format : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                Date parsedDate = sdf.parse(dateString);
                return new DateTime(parsedDate);
            } catch (ParseException ignored) {
                //ignora e vai avanti, l'IllegalArgument verrà restituito poi
            }
        }
        throw new IllegalArgumentException("Unparseable date: " + dateString);
    }

    /**
     * Imposta solo la parte di data di questo oggetto DateTime.
     * @param dateString La stringa della data nel formato "yyyy-MM-dd".
     * @throws ParseException se la stringa non può essere parsata.
     */
    public void setDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = sdf.parse(dateString);
        this.date = new Date(parsedDate.getTime() + getTimeInMillis());
    }

    /**
     * Imposta solo la parte di tempo di questo oggetto DateTime.
     * @param timeString La stringa del tempo nel formato "HH:mm:ss".
     * @throws ParseException se la stringa non può essere parsata.
     */
    public void setTime(String timeString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date parsedTime = sdf.parse(timeString);
        this.date = new Date(getDateInMillis() + parsedTime.getTime() % (24 * 60 * 60 * 1000));
    }

    /**
     * Ottiene la parte di data in millisecondi dall'epoch.
     * @return La parte di data in millisecondi.
     */
    public long getDateInMillis() {
        return this.date.getTime() / (24 * 60 * 60 * 1000) * (24 * 60 * 60 * 1000);
    }

    /**
     * Ottiene la parte di tempo in millisecondi dall'epoch.
     * @return La parte di tempo in millisecondi.
     */
    public long getTimeInMillis() {
        return this.date.getTime() % (24 * 60 * 60 * 1000);
    }

    /**
     * Ottiene la data in formato "yyyy-MM-dd".
     * @return La stringa della data formattata.
     */
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.date);
    }

    /**
     * Ottiene la data nel formato specificato.
     * @param format Il formato desiderato.
     * @return La stringa della data formattata.
     */
    public String getDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(this.date);
    }

    /**
     * Ottiene il tempo in formato "HH:mm:ss".
     * @return La stringa del tempo formattata.
     */
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(this.date);
    }

    /**
     * Ottiene il tempo in un formato specificato.
     * @param format Il formato desiderato.
     * @return La stringa del tempo formattata.
     */
    public String getTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(this.date);
    }

    /**
     * Formatta la data e il tempo in un formato specificato.
     * @param format Il formato desiderato.
     * @return La stringa della data e tempo formattata.
     */
    public String format(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(this.date);
    }

    /**
     * Formatta la data e il tempo in un formato di default "yyyy-MM-dd HH:mm:ss".
     * @return La stringa della data e tempo formattata.
     */
    public String formatDefault() {
        return format("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Confronta questo oggetto DateTime con un altro.
     * @param other L'altro oggetto DateTime.
     * @return Un valore negativo, zero o positivo se questo oggetto è minore, uguale o maggiore dell'altro.
     */
    @Override
    public int compareTo(DateTime other) {
        return this.date.compareTo(other.date);
    }

    /**
     * Restituisce una stringa rappresentante questo oggetto DateTime.
     * @return La stringa rappresentante questo oggetto DateTime.
     */
    @Override
    public String toString() {
        return formatDefault();
    }

    /**
     * Metodo principale per testare la classe DateTime.
     * @param args Argomenti della linea di comando (non utilizzati).
     */
    public static void main(String[] args) {
        // Test ordinamento
        ArrayList<DateTime> list = new ArrayList<>();
        list.add(new DateTime());
        list.add(DateTime.create("20/12/2024"));
        list.add(DateTime.create("24/12/2024 12:12:12"));
        list.add(DateTime.create("10/06/2023"));
        list.add(DateTime.create("15/06/2023 10:15:00"));

        // Stampa prima dell'ordinamento
        System.out.println("Before Sorting:");
        for (DateTime dt : list) {
            System.out.println(dt);
        }

        // Ordinamento della lista
        Collections.sort(list);

        // Stampa dopo l'ordinamento
        System.out.println("\nAfter Sorting:");
        for (DateTime dt : list) {
            System.out.println(dt);
        }

        // Test compareTo
        DateTime date1 = DateTime.create("2024-06-10 15:00:00");
        DateTime date2 = DateTime.create("2024-06-10 15:30:00");

        if (date1.compareTo(date2) < 0) {
            System.out.println(date1 + " is before " + date2);
        } else if (date1.compareTo(date2) > 0) {
            System.out.println(date1 + " is after " + date2);
        } else {
            System.out.println(date1 + " is equal to " + date2);
        }
    }
}
