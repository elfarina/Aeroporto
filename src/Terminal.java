import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * La classe Terminal rappresenta un terminal in un aeroporto o in una stazione, con una serie di gate associati ad esso.
 *
 * <h1>Spiegazione dei metodi</h1>
 *
 * <div>
 *     <h5>Costruttori</h5>
 * </div>
 * <p>
 * <i>Costruttore con nome, tipo e lista di gate</i> <b>Terminal</b>(String name, terminalType terminalType, ArrayList<Gate> gates):<br>
 * Inizializza il terminal con il nome, il tipo e la lista di gate specificati.
 * </p>
 * <p>
 * <i>Costruttore con nome e tipo</i> <b>Terminal</b>(String name, terminalType terminalType):<br>
 * Inizializza il terminal con il nome e il tipo specificati.
 * </p>
 * <p>
 * <i>Costruttore di default</i> <b>Terminal</b>():<br>
 * Inizializza il terminal con valori di default.
 * </p>
 * <p>
 * <i>Costruttore con nome, tipo e elenco di nomi dei gate</i> <b>Terminal</b>(String name, String type, List<String> gatesName):<br>
 * Inizializza il terminal con il nome, il tipo e l'elenco di nomi dei gate specificati.
 * </p>
 *
 * <div>
 *     <h5>Metodi per gestire i gate</h5>
 * </div>
 * <p>
 * <i>Metodo per ottenere la lista dei gate</i> <b>getGates</b>():<br>
 * Restituisce la lista dei gate associati al terminal.
 * </p>
 * <p>
 * <i>Metodo per impostare la lista dei gate</i> <b>setGates</b>(ArrayList<Gate> gates):<br>
 * Imposta la lista dei gate associati al terminal.
 * </p>
 * <p>
 * <i>Metodo per aggiungere un gate</i> <b>addGate</b>(Gate gate):<br>
 * Aggiunge un nuovo gate alla lista dei gate associati al terminal.
 * </p>
 * <p>
 * <i>Metodo per rimuovere un gate</i> <b>removeGate</b>(Gate gate):<br>
 * Rimuove un gate dalla lista dei gate associati al terminal.
 * </p>
 * <p>
 * <i>Metodo per verificare se la lista dei gate è vuota</i> <b>isEmpty</b>():<br>
 * Verifica se la lista dei gate associati al terminal è vuota.
 * </p>
 * <p>
 * <i>Metodo per verificare se un gate è presente nella lista</i> <b>contains</b>(String gateId):<br>
 * Verifica se un gate con l'ID specificato è presente nella lista dei gate associati al terminal.
 * </p>
 *
 * <div>
 *     <h5>Metodi getter e setter</h5>
 * </div>
 * <p>
 * <i>Metodo getter per ottenere il tipo di terminal</i> <b>getTerminalType</b>():<br>
 * Restituisce il tipo di terminal (Internazionale o Nazionale).
 * </p>
 * <p>
 * <i>Metodo setter per impostare il tipo di terminal</i> <b>setTerminalType</b>(terminalType terminalType):<br>
 * Imposta il tipo di terminal.
 * </p>
 * <p>
 * <i>Metodo getter per ottenere il nome del terminal</i> <b>getName</b>():<br>
 * Restituisce il nome del terminal.
 * </p>
 * <p>
 * <i>Metodo setter per impostare il nome del terminal</i> <b>setName</b>(String name):<br>
 * Imposta il nome del terminal.
 * </p>
 *
 * <div>
 *     <h5>Metodo toString</h5>
 * </div>
 * <p>
 * <i>Metodo per ottenere una rappresentazione testuale del terminal</i> <b>toString</b>():<br>
 * Restituisce una stringa che rappresenta il terminal con il nome, il tipo e l'elenco dei gate associati.
 * </p>
 **/
public class Terminal implements Serializable {
    // Variabili di istanza
    private String name; // Nome del terminal
    /**
     * Enum per il tipo di terminal (Internazionale o Nazionale)
     */
    public enum TerminalType {
        INTERNATIONAL(0),
        NATIONAL(1);

        private final int typeVal;

        TerminalType(int typeVal) {
            this.typeVal = typeVal;
        }

        public int getTypeVal() {
            return typeVal;
        }

        @Override
        public String toString() {
            return switch (this) {
                case INTERNATIONAL -> "International";
                case NATIONAL -> "National";
            };
        }
    }

    ArrayList<Gate> gates = new ArrayList<>(); // Lista dei gate associati al terminal
    private TerminalType terminalType; // Tipo di terminal

    // Costruttori

    /**
     * Costruttore con nome, tipo e lista di gate.
     *
     * @param name         Il nome del terminal.
     * @param terminalType Il tipo di terminal.
     * @param gates        La lista dei gate associati al terminal.
     */
    public Terminal(String name, TerminalType terminalType, ArrayList<Gate> gates) {
        this.name = name;
        this.terminalType = terminalType;
        this.gates = gates;
    }

    /**
     * Costruttore con nome e tipo.
     *
     * @param name         Il nome del terminal.
     * @param terminalType Il tipo di terminal.
     */
    public Terminal(String name, TerminalType terminalType) {
        this.name = name;
        this.terminalType = terminalType;
    }

    /**
     * Costruttore di default che inizializza il terminal con valori predefiniti.
     */
    public Terminal() {
        this.terminalType = TerminalType.NATIONAL;
        this.name = "Unsetted";
    }

    /**
     * Costruttore con nome, tipo e elenco di nomi dei gate.
     *
     * @param name       Il nome del terminal.
     * @param type       Il tipo di terminal.
     * @param gatesName  L'elenco di nomi dei gate.
     */
    public Terminal(String name, String type, List<String> gatesName) {
        this.name = name;
        switch (type.toLowerCase()) {
            case "international" -> this.terminalType = TerminalType.INTERNATIONAL;
            case "national" -> this.terminalType = TerminalType.NATIONAL;
            default -> this.terminalType = TerminalType.NATIONAL; //da gestire poi casomai
        }
        this.gates = new ArrayList<>();
        for (String g : gatesName) this.gates.add(new Gate(g));
    }

    // Metodi per gestire i gate

    /**
     * Ottiene la lista dei gate associati al terminal.
     *
     * @return La lista dei gate.
     */
    public ArrayList<Gate> getGates() {
        return gates;
    }

    /**
     * rimpiazza un gate partendo dal suo ID
     * @param g il rimpiazzo del gate
     */
    public void replaceGate(Gate g) {
        for(Gate gate: gates) if(gate.getGateId().equals(g.getGateId())) gate = g;
    }

    /**
     * funzione per estrarre l'indice del gate
     *
     * @param gate da cui estrarre l'indice
     * @return l'indice del gate
     */
    public int indexOfGate(Gate gate) {
        return gates.indexOf(gate);
    }
    /**
     * Imposta la lista dei gate associati al terminal.
     *
     * @param gates La lista dei gate da impostare.
     */
    public void setGates(ArrayList<Gate> gates) {
        this.gates = gates;
    }

    /**
     * Aggiunge un nuovo gate alla lista dei gate associati al terminal.
     *
     * @param gate Il gate da aggiungere.
     */
    public void addGate(Gate gate) {
        gates.add(gate);
    }

    /**
     * Rimuove un gate dalla lista dei gate associati al terminal.
     *
     * @param gate Il gate da rimuovere.
     * @return true se il gate è stato rimosso con successo, altrimenti false.
     */
    public boolean removeGate(Gate gate) {
        return gates.remove(gate);
    }

    /**
     * Verifica se la lista dei gate associati al terminal è vuota.
     *
     * @return true se la lista è vuota, altrimenti false.
     */
    public boolean isEmpty() {
        return gates.isEmpty();
    }

    /**
     * Verifica se un gate con l'ID specificato è presente nella lista dei gate associati al terminal.
     *
     * @param gateId L'ID del gate da cercare.
     * @return true se il gate è presente, altrimenti false.
     */
    public boolean contains(String gateId) {
        for (Gate gate : gates) if (gate.getGateId().equals(gateId)) return true;
        return false;
    }

    /**
     * controlla se tutti i terminal sono occupati
     * @return true se il terminal ha tutti i gate occupati
     */
    public boolean isAllOccuped(){
        for(Gate gate : gates) if(!gate.isOccuped()) return true;
        return false;
    }

    // Metodi getter e setter

    /**
     * Ottiene il tipo di terminal.
     *
     * @return Il tipo di terminal.
     */
    public TerminalType getTerminalType() {
        return terminalType;
    }

    /**
     * Imposta il tipo di terminal.
     *
     * @param terminalType Il tipo di terminal da impostare.
     */
    public void setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    /**
     * Ottiene il nome del terminal.
     *
     * @return Il nome del terminal.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del terminal.
     *
     * @param name Il nome del terminal da impostare.
     */
    public void setName(String name) {
        this.name = name;
    }

    // Metodo toString per rappresentazione testuale dell'oggetto

    /**
     * Restituisce una stringa che rappresenta il terminal con il nome, il tipo e l'elenco dei gate associati.
     * @return Una stringa rappresentante il terminal.
     */
    @Override
    public String toString() {
        String tmp = "Terminal: " + this.name + " (" + this.terminalType.toString() + ") {";
        for (Gate gate : gates) {
            tmp += " " + gate.toString() + " ";
        }
        return tmp + "};";
    }

    public int getGateIndex(Gate gate) {
        return gates.indexOf(gate);
    }
    public int getGateIndex(String gateId){
        for(Gate g : getGates()) if(g.getGateId().equals(gateId)) return getGates().indexOf(g);
        return -1;
    }
    public void setGateStatus(Gate g, boolean occuped){
        getGates().get(getGateIndex(g)).isOccuped(occuped);
    }
    public void setGateStatus(String gateId, boolean occuped){
        getGates().get(getGateIndex(gateId)).isOccuped(occuped);
    }
}
