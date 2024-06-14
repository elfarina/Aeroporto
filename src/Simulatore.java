import java.util.*;
import java.io.*;

public class Simulatore extends Thread {
    public ArrayList<Aereoporto> globalAirports;
    public static final int INITIAL_FLIGHT_AMMOUNT = 100;
    private static final String SETUP_FILE = "Setup/Aereoporti";
    private static final String[] airportsIATA = new String[]{
            "JFK", "LHR", "CDG", "FRA", "DXB", "HND", "LAX", "SIN", "PEK", "AMS", "SYD", "YYZ", "MUC",
            "ICN", "IST", "DEN", "MAD", "SFO", "HKG", "ORD",
    };
    private ArrayList<Volo> globalFlights;
    private ArrayList<Volo> Flights;

    public Simulatore() throws Exception {
        this.globalAirports = new ArrayList<>();
        this.Flights = new ArrayList<>();
        this.globalFlights = new ArrayList<>();
        this.start();
        this.join();
        this.mergeFlights();
    }
    public void run() {
        for(String str : airportsIATA) globalAirports.add(new Aereoporto(str));
        try {
            loadFlights(INITIAL_FLIGHT_AMMOUNT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadFlights(int totalFlights) throws Exception {
        for(int i = 0; i < totalFlights; i++) {
            Volo v = new FlightsGenerator().generateOnce();
            globalFlights.add(v);
        }
    }
    public void mergeFlights() throws Exception {
        for(Volo v : globalFlights){
            getAereoportoByIATA(v.getDepartureIATA()).addVolo(v, getTerminalType(v));
        }
    }
    public Aereoporto getAereoportoByIATA(String iata){
        for(Aereoporto a : globalAirports) if(a.getIATA().equals(iata)) return a;
        return null;
    }
    public ArrayList<Aereoporto> getGlobalAirports() {
        return globalAirports;
    }
    public Terminal.TerminalType getTerminalType(Volo volo) throws Exception {
        Aereoporto depAereoporto = getAereoportoByIATA(volo.getDepartureIATA());
        Aereoporto arrAereoporto = getAereoportoByIATA(volo.getArrivalIATA());
        if(depAereoporto != null && arrAereoporto != null) if(depAereoporto.getCountry().equals(arrAereoporto.getCountry())) return Terminal.TerminalType.NATIONAL; else return Terminal.TerminalType.INTERNATIONAL;
        else{
            throw new Exception("gli aereoporti sono null");
        }
    }
    public ArrayList<Volo> getArrivals(Aereoporto a) {
        ArrayList<Volo> arrivals = new ArrayList<>();
        for(Volo v : globalFlights) if(v.getArrivalIATA().equals(a.getIATA())) arrivals.add(v);
        return arrivals;
    }

    public static void main(String[] args) throws Exception {
        Simulatore simulatore = new Simulatore();
        for(Aereoporto a : simulatore.getGlobalAirports()) System.out.println(a);


        System.out.println("\nStampo gli arrivi al JFK\n");
        System.out.println(simulatore.getArrivals(simulatore.getAereoportoByIATA("JFK")));




    }
}
