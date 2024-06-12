import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Classe FlightsGenerator per la generazione di voli casuali.
 *
 * <h1>Spiegazione dei membri</h1>
 *
 * <div>
 *     <h5>Variabili di istanza</h5>
 * </div>
 * <p>
 *     <b>airportsIATA</b>: Array di stringhe contenente i codici IATA degli aeroporti.
 * </p>
 *
 * <div>
 *     <h5>Costruttori</h5>
 * </div>
 * <p>
 *     <i>Costruttore di default</i> <b>FlightsGenerator</b>(): Inizializza i codici IATA degli aeroporti da un file di setup o da una lista di default.
 * </p>
 *
 * <div>
 *     <h5>Metodi</h5>
 * </div>
 * <p>
 *     <b>isAnAirport</b>(String airport): Verifica se un codice IATA è presente nell'array di codici IATA.
 * </p>
 * <p>
 *     <b>generateOnce</b>(): Genera un volo casuale con orari di partenza e arrivo casuali, capacità e numero di passeggeri casuali.
 * </p>
 * <p>
 *     <b>generateOnce</b>(String departureIATA): Genera un volo casuale con partenza da un aeroporto specificato.
 * </p>
 * <p>
 *     <b>generateOnce</b>(String departureIATA, String arrivalIATA): Genera un volo casuale con partenza e arrivo specificati.
 * </p>
 * <p>
 *     <b>generateOnce</b>(String departureIATA, String arrivalIATA, DateTime Departure): Genera un volo casuale con partenza, arrivo e orario di partenza specificati.
 * </p>
 * <p>
 *     <b>generateMultiple</b>(int amount): Genera più voli casuali.
 * </p>
 * <p>
 *     <b>generateMultiple</b>(int amount, String departureIATA): Genera più voli casuali con partenza da un aeroporto specificato.
 * </p>
 */

public class FlightsGenerator {
    public String[] airportsIATA;

    /**
     * Costruttore di default. Inizializza i codici IATA degli aeroporti da un file di setup o da una lista di default.
     */
    public FlightsGenerator() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Aereoporto.AEREOPORTI_SETUP_FILE));
            String line = br.readLine();
            List<String> IATAs = new ArrayList<>();
            while ((line = br.readLine()) != null) IATAs.add(line.split(",")[0]);
            airportsIATA = new String[IATAs.size()];
            for (int i = 0; i < IATAs.size(); i++) airportsIATA[i] = IATAs.get(i);
            br.close();
        } catch (Exception e) {
            airportsIATA = new String[]{
                    "JFK", "LHR", "CDG", "FRA", "DXB", "HND", "LAX", "PEK", "AMS", "SYD", "YYZ", "MUC",
                    "ICN", "IST", "DEN", "MAD", "SFO", "HKG", "ORD",
            };
        }
    }

    /**
     * Verifica se un codice IATA è presente nell'array di codici IATA.
     *
     * @param airport Codice IATA da verificare.
     * @return true se il codice IATA è presente, false altrimenti.
     */
    public boolean isAnAirport(String airport) {
        for (String s : airportsIATA) if (airport.equals(s)) return true;
        return false;
    }

    /**
     * Genera un volo casuale con orari di partenza e arrivo casuali, capacità e numero di passeggeri casuali.
     *
     * @return Un oggetto Volo generato casualmente.
     */
    public Volo generateOnce() {
        DateTime Departure = new DateTime();
        Random r = new Random();
        int Days = r.nextInt(0, 7);
        int Hours = r.nextInt(0, 23);
        int Minutes = r.nextInt(0, 2) * 30;
        Departure.add(0, Minutes, Hours, Days);
        int capacity = r.nextInt(3, 9) * 50;
        int passengers = r.nextInt(capacity / 15, capacity);
        int depIndex = r.nextInt(0, airportsIATA.length);
        int arrIndex;
        do {
            arrIndex = r.nextInt(0, airportsIATA.length);
        } while (depIndex == arrIndex);
        DateTime Arrival = Departure;
        Arrival.add(FlightTimeCalculator.calculateFlightTime(airportsIATA[depIndex], airportsIATA[arrIndex]));
        return new Volo(Departure, Arrival, airportsIATA[depIndex], airportsIATA[arrIndex], capacity, passengers);
    }

    /**
     * Genera un volo casuale con partenza da un aeroporto specificato.
     *
     * @param departureIATA Codice IATA dell'aeroporto di partenza.
     * @return Un oggetto Volo generato casualmente.
     */
    public Volo generateOnce(String departureIATA) {
        if (!isAnAirport(departureIATA)) return generateOnce();
        Random r = new Random();
        int depIndex = 0;
        for (int i = 0; i < airportsIATA.length; i++)
            if (airportsIATA[i].equals(departureIATA)) {
                depIndex = i;
                break;
            }
        int arrIndex;
        do {
            arrIndex = r.nextInt(0, airportsIATA.length);
        } while (arrIndex == depIndex);
        DateTime Departure = new DateTime();
        int Days = r.nextInt(0, 7);
        int Hours = r.nextInt(0, 23);
        int Minutes = r.nextInt(0, 2) * 30;
        Departure.add(0, Minutes, Hours, Days);
        int capacity = r.nextInt(3, 9) * 50;
        int passengers = r.nextInt(capacity / 15, capacity);
        DateTime Arrival = Departure;
        Arrival.add(FlightTimeCalculator.calculateFlightTime(airportsIATA[depIndex], airportsIATA[arrIndex]));
        return new Volo(Departure, Arrival, airportsIATA[depIndex], airportsIATA[arrIndex], capacity, passengers);
    }

    /**
     * Genera un volo casuale con partenza e arrivo specificati.
     *
     * @param departureIATA Codice IATA dell'aeroporto di partenza.
     * @param arrivalIATA Codice IATA dell'aeroporto di arrivo.
     * @return Un oggetto Volo generato casualmente.
     */
    public Volo generateOnce(String departureIATA, String arrivalIATA) {
        if (!isAnAirport(departureIATA) && !isAnAirport(arrivalIATA)) return generateOnce();
        if (!isAnAirport(departureIATA)) return generateOnce(arrivalIATA);
        if (!isAnAirport(arrivalIATA)) return generateOnce(departureIATA);
        Random r = new Random();
        int Days = r.nextInt(0, 7);
        int Hours = r.nextInt(0, 23);
        int Minutes = r.nextInt(0, 2) * 30;
        DateTime Departure = new DateTime();
        Departure.add(0, Minutes, Hours, Days);
        int capacity = r.nextInt(3, 9) * 50;
        int passengers = r.nextInt(capacity / 15, capacity);
        DateTime Arrival = Departure;
        Arrival.add(FlightTimeCalculator.calculateFlightTime(departureIATA, arrivalIATA));
        return new Volo(Departure, Arrival, departureIATA, arrivalIATA, capacity, passengers);
    }

    /**
     * Genera un volo casuale con partenza, arrivo e orario di partenza specificati.
     *
     * @param departureIATA Codice IATA dell'aeroporto di partenza.
     * @param arrivalIATA Codice IATA dell'aeroporto di arrivo.
     * @param Departure Orario di partenza specificato.
     * @return Un oggetto Volo generato casualmente.
     */
    public Volo generateOnce(String departureIATA, String arrivalIATA, DateTime Departure) {
        if (!isAnAirport(departureIATA) && !isAnAirport(arrivalIATA)) return generateOnce();
        if (!isAnAirport(departureIATA)) return generateOnce(arrivalIATA);
        if (!isAnAirport(arrivalIATA)) return generateOnce(departureIATA);
        int capacity = new Random().nextInt(3, 9) * 50;
        int passengers = new Random().nextInt(capacity / 15, capacity);
        DateTime Arrival = Departure;
        Arrival.add(FlightTimeCalculator.calculateFlightTime(departureIATA, arrivalIATA));
        return new Volo(Departure, Arrival, departureIATA, arrivalIATA, capacity, passengers);
    }

    /**
     * Genera una lista di voli casuali.
     *
     * @param amount Numero di voli da generare.
     * @return Una lista di oggetti Volo generati casualmente.
     */
    public ArrayList<Volo> generateMultiple(int amount) {
        ArrayList<Volo> flights = new ArrayList<>();
        for (int i = 0; i < amount; i++) flights.add(generateOnce());
        return flights;
    }

    /**
     * Genera una lista di voli casuali con partenza da un aeroporto specificato.
     *
     * @param amount Numero di voli da generare.
     * @param departureIATA Codice IATA dell'aeroporto di partenza.
     * @return Una lista di oggetti Volo generati casualmente.
     */
    public ArrayList<Volo> generateMultiple(int amount, String departureIATA) {
        ArrayList<Volo> flights = new ArrayList<>();
        for (int i = 0; i < amount; i++) flights.add(generateOnce(departureIATA));
        return flights;
    }
}

