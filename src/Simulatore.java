import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe Simulatore per la simulazione di un aeroporto con gestione dei voli.
 *
 * <h1>Spiegazione dei membri</h1>
 *
 * <div>
 *     <h5>Variabili di istanza</h5>
 * </div>
 * <p>
 *     <b>self</b>: Istanza di Aereoporto rappresentante l'aeroporto simulato.
 * </p>
 * <p>
 *     <b>allFlights</b>: Lista di tutti i voli.
 * </p>
 * <p>
 *     <b>clock</b>: Istanza di TimerScheduler per la gestione del tempo.
 * </p>
 * <p>
 *     <b>allIatas</b>: Lista di tutti i codici IATA degli aeroporti.
 * </p>
 * <p>
 *     <b>manualTimer</b>: Flag per la gestione manuale del timer.
 * </p>
 * <p>
 *     <b>SERIAL_FILE</b>: Percorso del file di serializzazione dello stato del simulatore.
 * </p>
 * <p>
 *     <b>CLOCK_FREQUENCY</b>: Frequenza di aggiornamento del simulatore in millisecondi.
 * </p>
 *
 * <div>
 *     <h5>Costruttori</h5>
 * </div>
 * <p>
 *     <i>Costruttore di default</i> <b>Simulatore</b>(): Inizializza il simulatore e carica tutti i codici IATA.
 * </p>
 * <p>
 *     <b>Simulatore</b>(Aereoporto self): Inizializza il simulatore con un aeroporto specifico e carica tutti i codici IATA.
 * </p>
 * <p>
 *     <b>Simulatore</b>(String IATA): Inizializza il simulatore con un codice IATA specifico e carica tutti i codici IATA.
 * </p>
 *
 * <div>
 *     <h5>Metodi</h5>
 * </div>
 * <p>
 *     <b>getSelf</b>(): Restituisce l'istanza di Aereoporto.
 * </p>
 * <p>
 *     <b>setSelf</b>(Aereoporto self): Imposta l'istanza di Aereoporto.
 * </p>
 * <p>
 *     <b>getAllFlights</b>(): Restituisce la lista di tutti i voli.
 * </p>
 * <p>
 *     <b>setAllFlights</b>(ArrayList<Volo> allFlights): Imposta la lista di tutti i voli.
 * </p>
 * <p>
 *     <b>getClock</b>(): Restituisce l'istanza di TimerScheduler.
 * </p>
 * <p>
 *     <b>setClock</b>(TimerScheduler clock): Imposta l'istanza di TimerScheduler.
 * </p>
 * <p>
 *     <b>getAllIatas</b>(): Restituisce la lista di tutti i codici IATA.
 * </p>
 * <p>
 *     <b>setAllIatas</b>(ArrayList<String> allIatas): Imposta la lista di tutti i codici IATA.
 * </p>
 * <p>
 *     <b>isManualTimer</b>(): Verifica se il timer è impostato manualmente.
 * </p>
 * <p>
 *     <b>isManualTimer</b>(boolean manualTimer): Imposta se il timer è manuale.
 * </p>
 * <p>
 *     <b>serialize</b>(): Serializza lo stato del simulatore in un file.
 * </p>
 * <p>
 *     <b>deserialize</b>(): Deserializza lo stato del simulatore da un file.
 * </p>
 * <p>
 *     <b>run</b>(): Metodo principale del thread che gestisce la generazione dei voli e la simulazione.
 * </p>
 * <p>
 *     <b>loadAllIatas</b>(): Carica tutti i codici IATA dal file di configurazione.
 * </p>
 * <p>
 *     <b>main</b>(String[] args): Metodo principale per l'avvio della simulazione.
 * </p>
 */
public class Simulatore extends Thread {
    private Aereoporto self;
    private ArrayList<Volo> allFlights = new ArrayList<>();
    private TimerScheduler clock;
    private ArrayList<String> allIatas = new ArrayList<>();
    private boolean manualTimer = false;
    public static final String SERIAL_FILE = "Setup/appStateSimulatore.ser";
    private final static int CLOCK_FREQUENCY = 1500; // Frequenza di aggiornamento del simulatore

    /**
     * Costruttore di default. Inizializza il simulatore e carica tutti i codici IATA.
     */
    public Simulatore() throws IOException {
        this.loadAllIatas();
    }

    /**
     * Costruttore che accetta un'istanza di Aereoporto. Inizializza il simulatore con un aeroporto specifico.
     * @param self Istanza di Aereoporto.
     */
    public Simulatore(Aereoporto self) throws IOException {
        this.self = self;
        this.loadAllIatas();
    }

    /**
     * Costruttore che accetta un codice IATA. Inizializza il simulatore con un codice IATA specifico.
     * @param IATA Codice IATA dell'aeroporto.
     */
    public Simulatore(String IATA) throws IOException {
        this.self = new Aereoporto(IATA);
        this.loadAllIatas();
    }

    // Getter e Setter
    public synchronized Aereoporto getSelf() {
        return self;
    }

    public void setSelf(Aereoporto self) {
        this.self = self;
    }

    public ArrayList<Volo> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(ArrayList<Volo> allFlights) {
        this.allFlights = allFlights;
    }

    public TimerScheduler getClock() {
        return clock;
    }

    public void setClock(TimerScheduler clock) {
        this.clock = clock;
    }

    public ArrayList<String> getAllIatas() {
        return allIatas;
    }

    public void setAllIatas(ArrayList<String> allIatas) {
        this.allIatas = allIatas;
    }

    public boolean isManualTimer() {
        return manualTimer;
    }

    public void isManualTimer(boolean manualTimer) {
        this.manualTimer = manualTimer;
    }

    /**
     * Serializza lo stato del simulatore in un file.
     * @return true se la serializzazione è avvenuta con successo, false altrimenti.
     */
    public boolean serialize() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIAL_FILE))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Deserializza lo stato del simulatore da un file.
     * @return Istanza di Simulatore deserializzata.
     */
    public static Simulatore deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIAL_FILE))) {
            return (Simulatore) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    Thread generatoreVoli = new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    synchronized (getClock()) {
                        if (!getClock().isPaused()) {
                            for (int i = 0; i < new Random().nextInt(1, 5); i++) {
                                if (!getSelf().isFull()) {
                                    Volo v;
                                    if (new Random().nextBoolean())
                                        v = new FlightsGenerator().generateOnce(getSelf().getIATA(), getClock().getDateTime());
                                    else v = new FlightsGenerator().generateArrival(getSelf().getIATA());
                                    synchronized (getSelf()) {
                                        getSelf().addVolo(v);
                                    }
                                } else {
                                    System.err.println("L'aereoporto non ha più slot disponibili per i voli");
                                }
                            }
                            synchronized (getSelf()) {
                                System.out.println(getSelf());
                            }
                            getSelf().clearFlights(getClock().getDateTime());
                        }

                        Thread.sleep(CLOCK_FREQUENCY);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    });

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        String line = br.readLine();
                        if (line.equals("s")) {
                            System.err.println("Modifica sul timer");
                            synchronized (getClock()) {
                                getClock().isPaused(!getClock().isPaused());
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        this.setClock(new TimerScheduler(new DateTime(), 0, 0, 12, 0, CLOCK_FREQUENCY));
        this.getClock().start();
        generatoreVoli.start();
    }

    /**
     * Carica tutti i codici IATA dal file di configurazione.
     * @throws FileNotFoundException Se il file non viene trovato.
     * @throws IOException Se si verifica un errore di input/output.
     */
    public void loadAllIatas() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(Aereoporto.AEREOPORTI_SETUP_FILE));
        String line;
        br.readLine();
        String[] data;
        while ((line = br.readLine()) != null) {
            data = line.split(",");
            if (!data[0].isEmpty() || !data[0].isBlank()) allIatas.add(data[0]);
        }
    }

    /**
     * Metodo principale per l'avvio della simulazione.
     * @param args Argomenti della riga di comando.
     * @throws IOException Se si verifica un errore di input/output.
     * @throws InterruptedException Se il thread viene interrotto.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Simulatore sim = new Simulatore();
        System.out.println("[start] inizializza Simulatore");
        if (new File(SERIAL_FILE).exists()) System.out.println("[carica] carica dati salvati");
        System.out.println("[exit] esci dal programma");
        boolean exit = false;
        do {
            String line = br.readLine().toLowerCase().trim();
            switch (line) {
                case "start" -> {
                    boolean exit2 = false;
                    do {
                        System.out.println("Scegli un aereoporto dai seguenti:");
                        System.out.println(sim.getAllIatas().toString());
                        System.out.println("per ulteriori info, scrivi l'iata seguito da \"?\" (es. xyz?)");
                        String input = br.readLine().toUpperCase().trim();
                        String iata = input.replace("?", "");
                        if (sim.getAllIatas().contains(iata) && input.contains("?")) {
                            Aereoporto tmp = new Aereoporto(iata);
                            System.out.println("L'aereoporto selezionato è strutturato cosi: \n" + tmp.toString());
                        } else if (sim.getAllIatas().contains(input)) {
                            System.out.println("Inizializzo l'aereoporto indicato...");
                            sim = new Simulatore(input);
                            System.out.println("Aereoporto creato:\n" + sim.getSelf().toString());
                            exit2 = true;
                        } else {
                            System.out.println("Input non valido\nscrivi \"esci\" per uscire oppure riprova con \"invio\"");
                            if (br.readLine().toLowerCase().trim().contains("esci")) exit2 = true;
                        }
                    } while (!exit2);
                    exit = true;
                }
                case "carica" -> {
                    if (new File(SERIAL_FILE).exists()) {
                        System.out.println("Carico dai dati salvati in seriale");
                        sim = deserialize();
                        exit = true;
                    } else {
                        System.out.println("Non ci provare, non esiste il file, riprova con altro");
                    }
                }
                case "exit" -> System.exit(0);
                default -> System.out.println("Input non valido, riprova");
            }
        } while (!exit);
        // qui inizio la simulazione, genero voli casuali e faccio scorrere il tempo, libero quando il tempo è maggiore di quello del volo
        sim.start();
    }
}
