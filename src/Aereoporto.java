import java.io.*;
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
public class Aereoporto extends Thread implements Iterable<Volo>, Serializable{

    // Costanti per i file di setup degli aeroporti, terminal e gate
    public static final String AEREOPORTI_SETUP_FILE = "Setup/Aereoporti";
    public static final String TERMINAL_SETUP_FILE = "Setup/Aereoporti-Terminal";
    public static final String GATES_SETUP_FILE = "Setup/Aereoporti-Terminal-Gates";
    public static final String SERIAL_FILE = "Setup/appState.ser";

    // Variabili  per i dettagli dell'aeroporto
    private String IATA;
    private String country;
    private String city;

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
     * Costruttore che si inizializza da solo prendendo i dati dal file di Setup
     * Utilizzo i thread per la gestione concorrente dei dati dal file
     * @param IATA, il nome dell'aereoporto, essendo univoco lo cerca dal file e usa la funzione loadSetup().
     */
    public Aereoporto(String IATA){
        terminals = new ArrayList<>();
        this.flyMap = new HashMap<>();
        this.IATA = IATA;
        try{
            this.start();
            this.join();
        }catch(Exception e){
            e.printStackTrace();
        }
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
     * Funzione che carica lo stato dell'oggetto dal file seriale.
     * @return L'oggetto Aereoporto deserializzato.
     */
    public static Aereoporto deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIAL_FILE))) {
            return (Aereoporto) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Funzione che salva lo stato dell'oggetto sul file seriale.
     */
    public void serialize() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIAL_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utilizzo un Thread per la gestione concorrente dei dati dal file
     */
    @Override
    public void run(){
        try {
            loadAereoporto();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carica i dati di setup dell'aeroporto dai file di configurazione.
     * @throws Exception Se si verifica un errore durante la lettura dei file.
     */
    private void loadAereoporto() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(AEREOPORTI_SETUP_FILE));
        String line = br.readLine();
        String[] data;
        while((line = br.readLine()) != null){
            data = line.split(",");
            if(getIATA().equals(data[0])){
                setIATA(data[0]);
                setCountry(data[1]);
                setCity(data[2]);
                loadTerminals();
                return;
            }
        }
        System.out.println(this.toString());
    }
    private void loadTerminals() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(TERMINAL_SETUP_FILE));
        String line; br.readLine();
        String[] data;
        while((line = br.readLine()) != null){
            data = line.split(",");
            if(getIATA().equals(data[0]) && !containsTerminal(data[1])) {
                String tmp = data[3].replace("gates{", "");
                tmp = tmp.replace("}", "");
                List<String> gatesName = Arrays.asList(tmp.split(" "));
                this.terminals.add(new Terminal(data[1], data[2], gatesName));
            }
        }
        br.close();
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

    //TODO commentare
    public void addVolo(Volo volo, Terminal t){
        //TODO logica, controlla che il gate sia libero altrimenti non aggiungere
        Gate g = getRandomGate(t);
        g.isOccuped(true);
        t.replaceGate(g);
        replaceTerminal(t);
        flyMap.put(volo, g);
    }
    public void addVolo(Volo volo, Terminal.TerminalType type){
        Terminal t = getRandomTerminal(type);
        Gate g = getRandomGate(t);
        g.isOccuped(true);
        t.replaceGate(g);
        replaceTerminal(t);
        flyMap.put(volo, g);
    }
    //TODO commentare
    public boolean removeVolo(Volo volo){
        //TODO logica, rendere il gate libero e rimuovere il volo
        Gate g = flyMap.get(volo);
        g.isOccuped(false);
        Terminal t = findTerminalByVolo(volo);
        t.replaceGate(g);
        replaceTerminal(t);
        flyMap.remove(volo);
        return false;
    }
    //TODO commentare
    public Terminal findTerminalByVolo(Volo volo){
        Gate g = flyMap.get(volo);
        for(Terminal t : terminals){if(t.contains(g.getGateId())) return t;}
        return null;
    }
    //TODO aggiungere una funzione per restituire un gate libero, param nazionale / internzionale o il terminal stesso
    public Terminal getRandomTerminal(Terminal.TerminalType type){
        if(getTerminals().size() == 1) return getTerminals().get(0);
        if(getTerminals().size() == 2 && getTerminals().get(0).getTerminalType() == type) return getTerminals().get(0);
        else if(getTerminals().size() == 2) return getTerminals().get(1);
        ArrayList<Terminal> tmpTerm = getTerminals();
        for(Terminal t : getTerminals()) if(t.getTerminalType().equals(type)) tmpTerm.add(t);
        return tmpTerm.get(new Random().nextInt(0, tmpTerm.size()));
    }
    public Gate getRandomGate(Terminal terminal){
//        if(terminal.isAllOccuped()) return null;
        Gate g = terminal.getGates().get(new Random().nextInt(0, (terminal.getGates().size()-1)));
        if(g.isOccuped()) return getRandomGate(terminal);
        return g;
    }


    public void replaceTerminal(Terminal newTerminal){
        for(Terminal t : terminals) if(t.getName().equals(newTerminal.getName())) t = newTerminal;
    }


    /**
     * Restituisce una rappresentazione sotto forma di stringa dell'aeroporto.
     * @return Una stringa rappresentante l'aeroporto.
     */
    @Override
    public String toString() {
        String tmp = "[" + IATA + " - " + country + " - " + city + "]\n";
        for(Terminal t : terminals) tmp = tmp + t.toString() + "\n";
        if(!flyMap.isEmpty()) for(Volo v : flyMap.keySet()) tmp = tmp + v.toString() + " - Gate: " + flyMap.get(v).getGateId() + "\n";
        return tmp;
    }

    /**
     * Metodo principale per testare la classe Aereoporto.
     * @param args Argomenti della linea di comando (non utilizzati).
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Creazione di un oggetto Aereoporto di esempio
        Aereoporto a = new Aereoporto("JFK");
        Aereoporto b = new Aereoporto("SYD");
        Aereoporto c = new Aereoporto("FRA");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
//        Aereoporto aeroporto1 = new Aereoporto("JFK", "United States", "New York");
//        aeroporto1.addTerminal(new Terminal("T1", "Terminal 1", Arrays.asList("G1", "G2", "G3")));
//        aeroporto1.addTerminal(new Terminal("T2", "Terminal 2", Arrays.asList("G4", "G5", "G6")));
//
//        // Serializzazione dell'oggetto aeroporto1
//        aeroporto1.serialize();
//
//        // Deserializzazione in un nuovo oggetto
//        Aereoporto aeroporto2 = Aereoporto.deserialize();
//
//        // Confronto degli oggetti
//        System.out.println("Originale:");
//        System.out.println(aeroporto1);
//
//        System.out.println("Deserializzato:");
//        System.out.println(aeroporto2);
//
//        // Verifica che i dati degli oggetti siano identici
//        if (aeroporto1.toString().equals(aeroporto2.toString())) {
//            System.out.println("La serializzazione e deserializzazione hanno avuto successo!");
//        } else {
//            System.out.println("Qualcosa è andato storto con la serializzazione/deserializzazione.");
//        }
    }
}
