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
 *     <b>ask_loadSetup</b>(): Carica i dati di setup dell'aeroporto dai file di configurazione.
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
public class Aereoporto implements Serializable{

    // Costanti per i file di setup degli aeroporti, terminal e gate
    public static final String AEREOPORTI_SETUP_FILE = "Setup/Aereoporti";
    public static final String TERMINAL_SETUP_FILE = "Setup/Aereoporti-Terminal";
    public static final String SERIAL_FILE = "Setup/appState.ser";

    // Variabili  per i dettagli dell'aeroporto
    private String IATA;
    private String country;
    private String city;

    // Lista di terminal associati all'aeroporto
    private ArrayList<Terminal> terminals;

    // Mappa dei voli associati ai gate
    private Map<String, Volo> flyMap; //GateID - Volo


    public Aereoporto() {
        terminals = new ArrayList<>();
        flyMap = new HashMap<>();
    }

    public Aereoporto(String IATA){
        terminals = new ArrayList<>();
        this.flyMap = new HashMap<>();
        this.IATA = IATA;
        try{
            loadAereoporto();
        }catch(Exception e){
            e.printStackTrace();
        }
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

    public Map<String, Volo> getFlyMap() {
        return flyMap;
    }

    public void loadAereoporto() throws IOException {
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
    public boolean containsTerminal(String terminalName) {
        Iterator<Terminal> it = terminals.iterator();
        while(it.hasNext()) if(it.next().getName().equals(terminalName)) return true;
        return false;
    }
    public Terminal getTerminalByName(String terminalName) {
        for (Terminal t : terminals) {
            if (t.getName().equals(terminalName)) return t;
        }
        return null;
    }

    public boolean isFull(){
        for(Terminal t : terminals){
            for(Gate g : t.getGates()){
                if(!g.isOccuped()) return false;
            }
        }
        return true;
    }
    public boolean addVolo(Volo v){
        if(isFull()){
            System.err.println("L'aereoporto è pieno");
            return false;
        }
        try{
            Gate g = getFirstGate();
            g.isOccuped(true);
            getTerminalByGate(g).replaceGate(g);
            flyMap.put(g.getGateId(), v);
            return true;
        }catch(Exception ignored){
            System.err.println(ignored.getMessage());
        } return false;
    }
//    public synchronized boolean removeVolo(Volo v){
//        for(Terminal t : terminals){
//            for(Gate g : t.getGates()){
//                try{
//                    if(flyMap.get(g.getGateId()).equals(v)) {
//                        flyMap.remove(g.getGateId());
//                        return true;
//                    }
//                }catch(Exception ignored){
//                    System.err.println("Impossibile rimuovere il volo" + v);
//                }
//            }
//        }
//        return false;
//    }
    public synchronized boolean removeVolo(Volo v) {
        synchronized (flyMap) {
            for (Terminal t : terminals) {
                for (Gate g : t.getGates()) {
                    if (flyMap.containsKey(g.getGateId()) && flyMap.get(g.getGateId()).equals(v)) {
                        try {
                            g.isOccuped(false);  // Libera il gate
                            flyMap.replace(g.getGateId(), null);  // Rimuove il volo dalla mappa
                            return true;
                        } catch (Exception e) {
                            System.err.println("Impossibile rimuovere il volo " + v + " dal gate " + g.getGateId() + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }



    public Gate getFirstGate() throws Exception{
        for(Terminal t : getTerminals()){ try{ return getFirstGate(t); } catch (Exception ignored) { } }
        throw new Exception("Non esistono gate liberi per l'aereoporto " + getIATA());
    }
    public Gate getFirstGate(Terminal t)throws Exception {
        for(Gate g : t.getGates()) if(!g.isOccuped()) return g;
        throw new Exception ("Tutti i Gate del Terminal "+ t.getName() + " sono occupati");
    }
    public Terminal getTerminalByGate(Gate g) throws Exception{
        for(Terminal t : getTerminals()) if(t.contains(g.getGateId())) return t;
        throw new Exception("Il gate " + g.getGateId() + " non esiste nell'aereoporto " + this.getIATA());
    }
    public Terminal getTerminalByGate(String GateID) throws Exception{
        for(Terminal t : getTerminals()) if(t.contains(GateID)) return t;
        throw new Exception("Il gate " + GateID + " non esiste nell'aereoporto " + this.getIATA());
    }
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("[" + IATA + " - " + country + " - " + city + "]\n");
        for(Terminal t : terminals) tmp.append(t.toString()).append("\n");
        if(!flyMap.isEmpty()) for(String gateId : flyMap.keySet()) tmp.append(flyMap.get(gateId).toString()).append(" - Gate: ").append(gateId).append("\n");
        return tmp.toString();
    }
    public boolean contains(Terminal t){
        for(Terminal x : getTerminals()) if(x.equals(t)) return true;
        return false;
    }
    public boolean contains(Gate g){
        for(Terminal t : getTerminals()) if(t.contains(g.getGateId())) return true;
        return false;
    }

    /**
     * Rimuove i voli dai gate partendo da una data, se i voli hanno una data di Partenza (departureDate) precedente al DateTime in parametro vengono eliminati dalla flyMap
     * @param dateTime La data prima della quale verranno elimiati tutti i voli
     * @return true se è stato trovato e rimosso, false altrimenti.
     */
    public synchronized void clearFlights(DateTime dateTime) {
        synchronized (flyMap) {
            Iterator<Map.Entry<String, Volo>> iterator = flyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Volo> entry = iterator.next();
                Volo v = entry.getValue();
                System.out.println("Controllo: " + v);
                try {
                    if (v.getDepartureTime().compareTo(dateTime) <= 0 || v.getArrivalTime().compareTo(dateTime) <= 0) {
                        if (removeVolo(v)) {
                            System.err.println("Rimosso " + v.toString());
                            iterator.remove(); // Rimuove l'elemento dall'iteratore e dalla mappa
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Errore durante la rimozione del volo: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        Aereoporto a = new Aereoporto("JFK");
        System.out.println(a);
        Volo v = new FlightsGenerator().generateOnce("JFK");
        a.addVolo(v);
        a.addVolo(new FlightsGenerator().generateArrival("JFK"));
        a.addVolo(new FlightsGenerator().generateArrival("JFK"));
        a.addVolo(new FlightsGenerator().generateOnce("JFK"));
        a.addVolo(new FlightsGenerator().generateOnce("JFK"));
        System.out.println(a);
        a.clearFlights(DateTime.create("2024-08-0 00:00:00"));
        System.out.println(a);
    }

}
