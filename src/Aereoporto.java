import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

public class Aereoporto implements Iterable<Volo>, Serializable {
    public static final String AEREOPORTI_SETUP_FILE = "Setup/Aereoporti";
    public static final String TERMINAL_SETUP_FILE = "Setup/Aereoporti-Terminal";
    public static final String GATES_SETUP_FILE = "Setup/Aereoporti-Terminal-Gates";
    private BufferedReader br;
    private FileReader fr;
    private static String IATA;
    private static String country;
    private static String city;
    private ArrayList<Terminal> terminals;
    private Map<Volo, Gate> flyMap;
    public Aereoporto() {
        terminals = new ArrayList<>();
        flyMap = new HashMap<>();
    }
    public Aereoporto(String IATA, String nation, String city) {
        this.IATA = IATA;
        this.country = nation;
        this.city = city;
        terminals = new ArrayList<>();
        flyMap = new HashMap<>();
    }
    public void associaVoloGate(Volo volo, Gate gate) {
        flyMap.put(volo, gate);
    }
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
    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
    }

    @Override
    public Iterator<Volo> iterator() {
        return this.flyMap.keySet().iterator();
    }

    public void loadSetup() throws Exception{
        br = new BufferedReader(new FileReader(AEREOPORTI_SETUP_FILE));
        String line;
        br.readLine(); //salto la prima riga (info sulla struttura)
        String[] data;
        //carico gli aereoporti dal file
        while((line = br.readLine()) != null){
            data = line.split(",");
            if(this.IATA.equals(data[0])){
                //leggo i dati e li assegno alle variabili dell'aereoporto poi posso esplorare i terminal
                setCountry(data[1]); setCity(data[2]);
                System.out.println(Arrays.toString(data));
                break;
            }
        }
        br.close();
        br = new BufferedReader(new FileReader(TERMINAL_SETUP_FILE));
        br.readLine(); //salto la prima riga (info sulla struttura)
        //carico i terminal dal file e i rispettivi gate
        while((line = br.readLine()) != null){
            data = line.split(",");
            if(this.IATA.equals(data[0]) && !containsTerminal(data[1])){
                String tmp = data[3].replace("gates{", "");
                tmp = tmp.replace("}", "");
                List<String> gatesName = Arrays.asList(tmp.split(" "));
                this.terminals.add(new Terminal(data[1], data[2], gatesName));
            }
        }
        //TODO creare la mappa dei terminal, leggendo dal file GATE_SETUP_FILE
    }
    public boolean containsTerminal(String terminalName){
        Iterator<Terminal> it = terminals.iterator();
        while(it.hasNext()) if(it.next().getName().equals(terminalName)) return true;
        return false;
    }
    public Terminal getTerminal(String terminalName){
        Iterator<Terminal> it = terminals.iterator();
        while(it.hasNext()) {
            Terminal t = it.next(); if(t.getName().equals(terminalName)) return t;
        }
        return null;
    }
    public int getTerminalIndex(String terminalName){
        return getTerminals().indexOf(getTerminal(terminalName));
    }
    public boolean containsTerminal(Terminal terminal){ return getTerminals().contains(terminal); }

    @Override
    public String toString() {
        String tmp = "[" + IATA + " - " + country + " - " + city + "]\n";
        for(Terminal t : terminals) tmp = tmp + t.toString() + "\n";
        return tmp;
    }
    public static void main(String[] args){
        Aereoporto a = new Aereoporto();
        a.setIATA("JFK");
        try{
            a.loadSetup();
        }catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(a.toString());
    }

}
