import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

/**
 * Classe Aereoporto per la gestione e la collezione dei dati degli aeroporti.
 *
 * <h1>Spiegazione dei membri</h1>
 *
 * <div>
 *     <h5>Costanti per i file di setup degli aeroporti</h5>
 * </div>
 * <p>
 *     <b>AEREOPORTI_SETUP_FILE</b>: Costante per il percorso del file di setup degli aeroporti.
 * </p>
 * <p>
 *     <b>TERMINAL_SETUP_FILE</b>: Costante per il percorso del file di setup dei terminal.
 * </p>
 * <p>
 *     <b>GATES_SETUP_FILE</b>: Costante per il percorso del file di setup dei gate.
 * </p>
 * <div>
 *     <h5>Variabili di istanza</h5>
 * </div>
 * <p>
 *     <b>br</b>: BufferedReader per la lettura dei file di setup.
 * </p>
 * <p>
 *     <b>fr</b>: FileReader per la lettura dei file di setup.
 * </p>
 * <p>
 *     <b>IATA</b>: Codice IATA dell'aeroporto.
 * </p>
 * <p>
 *     <b>country</b>: Paese in cui si trova l'aeroporto.
 * </p>
 * <p>
 *     <b>city</b>: Città in cui si trova l'aeroporto.
 * </p>
 * <p>
 *     <b>terminals</b>: Lista di terminal associati all'aeroporto.
 * </p>
 * <p>
 *     <b>flyMap</b>: Mappa dei voli associati ai gate.
 * </p>
 * <div>
 *     <h5>Costruttori</h5>
 * </div>
 * <p>
 *     <i>Costruttore di default</i> <b>Aereoporto</b>(): Inizializza la lista dei terminal e la mappa dei voli.
 * </p>
 * <p>
 *     <i>Costruttore con parametri</i> <b>Aereoporto</b>(String IATA, String nation, String city): Inizializza i dettagli dell'aeroporto.
 * </p>
 * <div>
 *     <h5>Metodi getter e setter</h5>
 * </div>
 * <p>
 *     Metodi per accedere e modificare le variabili di istanza dell'aeroporto.
 * </p>
 * <div>
 *     <h5>Metodi per la gestione dei terminal e dei voli</h5>
 * </div>
 * <p>
 *     <b>associaVoloGate</b>(Volo volo, Gate gate): Associa un volo ad un gate.
 * </p>
 * <p>
 *     <b>addTerminal</b>(Terminal terminal): Aggiunge un terminal alla lista dei terminal dell'aeroporto.
 * </p>
 * <p>
 *     <b>iterator</b>(): Metodo per ottenere un iteratore sui voli.
 * </p>
 * <p>
 *     <b>loadSetup</b>(): Carica i dati di setup dell'aeroporto dai file di configurazione.
 * </p>
 * <p>
 *     <b>containsTerminal</b>(String terminalName): Verifica se l'aeroporto contiene un terminal specifico.
 * </p>
 * <p>
 *     <b>getTerminal</b>(String terminalName): Restituisce il terminal con il nome specificato.
 * </p>
 * <p>
 *     <b>getTerminalIndex</b>(String terminalName): Restituisce l'indice del terminal con il nome specificato.
 * </p>
 * <p>
 *     <b>containsTerminal</b>(Terminal terminal): Verifica se l'aeroporto contiene un terminal specifico.
 * </p>
 * <div>
 *     <h5>Metodi di utilità</h5>
 * </div>
 * <p>
 *     <b>toString</b>(): Restituisce una rappresentazione sotto forma di stringa dell'aeroporto.
 * </p>
 * <div>
 *     <h5>Metodo principale per testare la classe Aereoporto</h5>
 * </div>
 * <p>
 *     <b>main</b>(String[] args): Metodo principale per testare la classe Aereoporto.
 * </p>
 */

public class Aereoporto implements Iterable<Volo>, Serializable {

    // Costanti per i file di setup degli aeroporti, terminal e gate
    public static final String AEREOPORTI_SETUP_FILE = "Setup/Aereoporti";
    public static final String TERMINAL_SETUP_FILE = "Setup/Aereoporti-Terminal";
    public static final String GATES_SETUP_FILE = "Setup/Aereoporti-Terminal-Gates";

    // BufferedReader e FileReader per la lettura dei file di setup
    private BufferedReader br;
    private FileReader fr;

    // Variabili statiche per i dettagli dell'aeroporto
    private static String IATA;
    private static String country;
    private static String city;

    // Lista di terminal associati all'aeroporto
    private ArrayList<Terminal> terminals;

    // Mappa dei voli associati ai gate
    private Map<Volo, Gate> flyMap;

    /**
     * Costruttore di default che inizializza la lista dei terminal e la mappa dei voli.
     */
    public Aereoporto() {
        terminals = new ArrayList<>();
        flyMap = new HashMap<>();
    }

    /**
     * Costruttore con parametri per inizializzare i dettagli dell'aeroporto.
     * @param IATA Il codice IATA dell'aeroporto.
     * @param nation Il paese in cui si trova l'aeroporto.
     * @param city La città in cui si trova l'aeroporto.
     */
    public Aereoporto(String IATA, String nation, String city) {
        this.IATA = IATA;
        this.country = nation;
        this.city = city;
        terminals = new ArrayList<>();
        flyMap = new HashMap<>();
    }

    /**
     * Associa un volo ad un gate.
     * @param volo Il volo da associare.
     * @param gate Il gate da associare.
     */
    public void associaVoloGate(Volo volo, Gate gate) {
        flyMap.put(volo, gate);
    }

    // Metodi getter e setter per le variabili dell'aeroporto
    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String nation) {
        this.country = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Terminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<Terminal> terminals) {
        this.terminals = terminals;
    }

    public Map<Volo, Gate> getFlyMap() {
        return flyMap;
    }

    public void setFlyMap(Map<Volo, Gate> Voli) {
        this.flyMap = Voli;
    }

    /**
     * Aggiunge un terminal alla lista dei terminal dell'aeroporto.
     * @param terminal Il terminal da aggiungere.
     */
    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    /**
     * Metodo per ottenere un iteratore sui voli.
     * @return Un iteratore sui voli.
     */
    @Override
    public Iterator<Volo> iterator() {
        return this.flyMap.keySet().iterator();
    }

    /**
     * Carica i dati di setup dell'aeroporto dai file di configurazione.
     * @throws Exception Se si verifica un errore durante la lettura dei file.
     */
    public void loadSetup() throws Exception {
        br = new BufferedReader(new FileReader(AEREOPORTI_SETUP_FILE));
        String line;
        br.readLine(); // Salto la prima riga (info sulla struttura)
        String[] data;
        // Carico gli aeroporti dal file
        while((line = br.readLine()) != null) {
            data = line.split(",");
            if(this.IATA.equals(data[0])) {
                // Leggo i dati e li assegno alle variabili dell'aeroporto poi posso esplorare i terminal
                setCountry(data[1]);
                setCity(data[2]);
                System.out.println(Arrays.toString(data));
                break;
            }
        }
        br.close();

        br = new BufferedReader(new FileReader(TERMINAL_SETUP_FILE));
        br.readLine(); // Salto la prima riga (info sulla struttura)
        // Carico i terminal dal file e i rispettivi gate
        while((line = br.readLine()) != null) {
            data = line.split(",");
            if(this.IATA.equals(data[0]) && !containsTerminal(data[1])) {
                String tmp = data[3].replace("gates{", "");
                tmp = tmp.replace("}", "");
                List<String> gatesName = Arrays.asList(tmp.split(" "));
                this.terminals.add(new Terminal(data[1], data[2], gatesName));
            }
        }
        // TODO creare la mappa dei terminal, leggendo dal file GATES_SETUP_FILE
    }

    /**
     * Verifica se l'aeroporto contiene un terminal specifico.
     * @param terminalName Il nome del terminal da verificare.
     * @return true se il terminal esiste, false altrimenti.
     */
    public boolean containsTerminal(String terminalName) {
        Iterator<Terminal> it = terminals.iterator();
        while(it.hasNext()) if(it.next().getName().equals(terminalName)) return true;
        return false;
    }

    /**
     * Restituisce il terminal con il nome specificato.
     * @param terminalName Il nome del terminal da restituire.
     * @return Il terminal trovato, o null se non esiste.
     */
    public Terminal getTerminal(String terminalName) {
        Iterator<Terminal> it = terminals.iterator();
        while(it.hasNext()) {
            Terminal t = it.next();
            if(t.getName().equals(terminalName)) return t;
        }
        return null;
    }

    /**
     * Restituisce l'indice del terminal con il nome specificato.
     * @param terminalName Il nome del terminal.
     * @return L'indice del terminal, o -1 se non esiste.
     */
    public int getTerminalIndex(String terminalName) {
        return getTerminals().indexOf(getTerminal(terminalName));
    }

    /**
     * Verifica se l'aeroporto contiene un terminal specifico.
     * @param terminal Il terminal da verificare.
     * @return true se il terminal esiste, false altrimenti.
     */
    public boolean containsTerminal(Terminal terminal) {
        return getTerminals().contains(terminal);
    }

    /**
     * Restituisce una rappresentazione sotto forma di stringa dell'aeroporto.
     * @return Una stringa rappresentante l'aeroporto.
     */
    @Override
    public String toString() {
        String tmp = "[" + IATA + " - " + country + " - " + city + "]\n";
        for(Terminal t : terminals) tmp = tmp + t.toString() + "\n";
        return tmp;
    }

    /**
     * Metodo principale per testare la classe Aereoporto.
     * @param args Argomenti della linea di comando (non utilizzati).
     */
    public static void main(String[] args) {
        Aereoporto a = new Aereoporto();
        a.setIATA("JFK");
        try {
            a.loadSetup();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(a.toString());
    }
}
