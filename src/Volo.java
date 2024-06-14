import java.io.Serializable;
import java.util.Map;

/**
 * La classe Volo rappresenta un volo, contenente informazioni sulla partenza, l'arrivo, la capacità e il numero di passeggeri.
 *
 * <h1>Spiegazione dei metodi</h1>
 *
 * <div>
 *     <h5>Costruttori</h5>
 * </div>
 * <p>
 * <i>Costruttore completo</i> <b>Volo</b>(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA, int capacity, int passengers):<br>
 * Inizializza tutte le proprietà del volo.
 * </p>
 * <ul>
 *     <li><i>depTime</i> e <i>arriTime</i> sono oggetti DateTime che rappresentano rispettivamente l'orario di partenza e arrivo.</li>
 *     <li><i>depIATA</i> e <i>arrIATA</i> sono i codici IATA degli aeroporti di partenza e arrivo.</li>
 *     <li><i>capacity</i> è la capacità massima del volo.</li>
 *     <li><i>passengers</i> è il numero attuale di passeggeri.</li>
 * </ul>
 * <p><i>Costruttore parziale</i> <b>Volo</b>(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA):<br>
 * Inizializza tutte le proprietà tranne <i>capacity</i> e <i>passengers</i>, che sono impostati a 0.
 * </p>
 * <p><i>Costruttore con stringhe</i> <b>Volo</b>(String depTime, String arrTime, String IATAS):<br>
 * Converte le stringhe di data in oggetti DateTime utilizzando il metodo DateTime.create(String).<br>
 * Divide la stringa <i>IATAS</i> per ottenere i codici IATA di partenza e arrivo.
 * </p>
 * <p><i>Costruttore di default</i> <b>Volo</b>():<br>
 * Inizializza <i>departureTime</i> e <i>arrivalTime</i> con l'ora corrente utilizzando il costruttore predefinito di DateTime.<br>
 * Imposta le altre proprietà con valori di default.
 * </p>
 * <p><i>Metodi getter e setter</i>:<br>
 * Consentono di accedere e modificare le proprietà del volo.<br>
 * I metodi getter e setter per <i>departureTime</i> e <i>arrivalTime</i> gestiscono oggetti DateTime, che permettono di lavorare facilmente con date e orari.
 * </p>
 * <h5>Collegamento con DateTime</h5>
 * <p>
 * La classe Volo utilizza la classe DateTime per gestire le date e gli orari dei voli. Questo consente di sfruttare le funzionalità avanzate di manipolazione e formattazione delle date offerte da DateTime, semplificando il codice e migliorando la leggibilità. Ad esempio, il metodo DateTime.create(String) permette di creare facilmente un oggetto DateTime a partire da una stringa di data, gestendo vari formati.
 * </p>
 */
public class Volo implements Serializable {
    private DateTime departureTime;
    private DateTime arrivalTime;
    private String departureIATA;
    private String arrivalIATA;
    private int capacity;
    private int passengers;

    /**
     * Costruttore completo per creare un oggetto Volo con tutti i dettagli specificati.
     * @param depTime L'orario di partenza del volo.
     * @param arriTime L'orario di arrivo del volo.
     * @param depIATA Il codice IATA dell'aeroporto di partenza.
     * @param arrIATA Il codice IATA dell'aeroporto di arrivo.
     * @param capacity La capacità massima di passeggeri del volo.
     * @param passengers Il numero attuale di passeggeri del volo.
     */
    public Volo(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA, int capacity, int passengers) {
        this.departureTime = depTime;
        this.arrivalTime = arriTime;
        this.departureIATA = depIATA;
        this.arrivalIATA = arrIATA;
        this.capacity = capacity;
        this.passengers = passengers;
    }

    /**
     * Costruttore parziale per creare un oggetto Volo senza specificare la capacità e il numero di passeggeri.
     * @param depTime L'orario di partenza del volo.
     * @param arriTime L'orario di arrivo del volo.
     * @param depIATA Il codice IATA dell'aeroporto di partenza.
     * @param arrIATA Il codice IATA dell'aeroporto di arrivo.
     */
    public Volo(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA) {
        this.departureTime = depTime;
        this.arrivalTime = arriTime;
        this.departureIATA = depIATA;
        this.arrivalIATA = arrIATA;
        this.capacity = 0;
        this.passengers = 0;
    }

    /**
     * Costruttore per creare un oggetto Volo a partire da stringhe di orario e un codice IATA combinato.
     * Utilizza il metodo statico DateTime.create(String) per convertire le stringhe di data in oggetti DateTime.
     * @param depTime L'orario di partenza del volo (formattato come stringa).
     * @param arrTime L'orario di arrivo del volo (formattato come stringa).
     * @param IATAS Una stringa contenente i codici IATA di partenza e arrivo separati da un trattino.
     */
    public Volo(String depTime, String arrTime, String IATAS) {
        this.departureTime = DateTime.create(depTime);
        this.arrivalTime = DateTime.create(arrTime);
        String[] data = IATAS.split("-");
        this.departureIATA = data[0];
        this.arrivalIATA = data[1];
    }

    /**
     * Costruttore di default per creare un oggetto Volo con valori iniziali vuoti o di default.
     * Inizializza le variabili di tempo con l'ora corrente tramite il costruttore predefinito di DateTime.
     */
    public Volo() {
        this.departureTime = new DateTime();
        this.arrivalTime = new DateTime();
        this.departureIATA = "";
        this.arrivalIATA = "";
        this.capacity = 0;
        this.passengers = 0;
    }

    // Metodi getter e setter per le variabili del volo

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
        this.departureTime = departureTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureIATA() {
        return departureIATA;
    }

    public void setDepartureIATA(String departureIATA) {
        this.departureIATA = departureIATA;
    }

    public String getArrivalIATA() {
        return arrivalIATA;
    }

    public void setArrivalIATA(String arrivalIATA) {
        this.arrivalIATA = arrivalIATA;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
    @Override
    public String toString(){
        String tmp = departureIATA + " ["+departureTime+"]\t";
        tmp += arrivalIATA + " ["+arrivalTime+"]\t";
        tmp += "("+ passengers + "\\" + capacity +")";
        return tmp;
    }
}
