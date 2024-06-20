import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Simulatore extends Thread {
    private Aereoporto self;
    private ArrayList<Volo> allFlights = new ArrayList<>();
    private TimerScheduler clock;
    private ArrayList<String> allIatas = new ArrayList<>();
    private boolean manualTimer = false;
    public static final String SERIAL_FILE = "Setup/appStateSimulatore.ser";

    public Simulatore() throws IOException {
        this.loadAllIatas();
    }
    public Simulatore(Aereoporto self) throws IOException {
        this.self = self;
        this.loadAllIatas();
    }

    public Simulatore(String IATA) throws IOException {
        this.self = new Aereoporto(IATA);
        this.loadAllIatas();
    }
    //getter-setter
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


    public boolean serialize(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIAL_FILE))){
            oos.writeObject(this);
            return true;
        }catch(IOException e){
            return false;
        }
    }
    public static Simulatore deserialize(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIAL_FILE))){
            return (Simulatore) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }


//    public synchronized void generateFlight() throws InterruptedException {
//        String iata = this.getSelf().getIATA();
//        ArrayList<Volo> tmpFlights = new ArrayList<>();
//        Thread t = new Thread(new Runnable(){
//           public void run(){
//               for(int i = 0; i < new Random().nextInt(1, 5); i++) tmpFlights.add(new FlightsGenerator().generateOnce(iata));
//           }
//        });
//        t.start();
//        t.join();
//        for(Volo v : tmpFlights){
//            getAllFlights().add(v);
//            getSelf().addVolo(v);
//        }
//    }
//    private Thread flightGenThread = new Thread(new Runnable(){
//        public void run(){
//            try {
//                while(true) {
//                    Volo v;
//                    if(getSelf().isFull()) System.err.println("l'aereoporto ha tutti gli slot pieni, fermo la generazione dei voli");
//                    else{
//                        if (new Random().nextBoolean() && !getSelf().isFull()) v = new FlightsGenerator().generateOnceAfter(getSelf().getIATA(), getClock().getDateTime());
//                        else v = new FlightsGenerator().generateArrival(getSelf().getIATA());
//                        if(getSelf().addVolo(v)){
//                            System.out.println("Volo creato: \n" + v);
//                            allFlights.add(v);
//                        }
//                        else{
//                            System.out.println("Impossibile aggiungere il volo: \n" + v);
//                        }
//                    }
//                    getSelf().clearFlights(getClock().getDateTime());
//                    Thread.sleep(2000);
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    });
    Thread generatoreVoli = new Thread(new Runnable(){
        public void run(){
            while(true){
                try{
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
                        Thread.sleep(2000);
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    });
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(true){
                    try {
                        String line = br.readLine();
                        if(line.equals("s")){
                            System.err.println("Modifica sul timer");
                            synchronized (getClock()){
                                getClock().isPaused(!getClock().isPaused());
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        this.setClock(new TimerScheduler(new DateTime(),0, 0, 12, 0));
        this.getClock().start();
        generatoreVoli.start();
        //t.start();
        //flightGenThread.start();
//        while(true){
//            try {
//                Thread.sleep(1000);
//                getSelf().clearFlights(getClock().getDateTime());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }


    public void loadAllIatas() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(Aereoporto.AEREOPORTI_SETUP_FILE));
        String line;
        br.readLine();
        String[] data;
        while((line = br.readLine()) != null){
            data = line.split(",");
            if(!data[0].isEmpty() || !data[0].isBlank()) allIatas.add(data[0]);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Simulatore sim = new Simulatore();
        System.out.println("[start] inizializza Simulatore");
        if(new File(SERIAL_FILE).exists()) System.out.println("[carica] carica dati salvati");
        System.out.println("[exit] esci dal programma");
        boolean exit = false;
        do{
            String line = br.readLine().toLowerCase().trim();
            switch(line){
                case "start" ->{
                    boolean exit2 = false;
                    do{
                        System.out.println("Scegli un aereoporto dai seguenti:");
                        System.out.println(sim.getAllIatas().toString());
                        System.out.println("per ulteriori info, scrivi l'iata seguito da \"?\" (es. xyz?)");
                        String input = br.readLine().toUpperCase().trim();
                        String iata = input.replace("?","");
                        if(sim.getAllIatas().contains(iata) && input.contains("?")) {
                            Aereoporto tmp = new Aereoporto(iata);
                            System.out.println("L'aereoporto selezionato è strutturato cosi: \n" + tmp.toString());
                        }else if(sim.getAllIatas().contains(input)){
                            System.out.println("Inizializzo l'aereoporto indicato...");
                            sim = new Simulatore(input);
                            System.out.println("Aereoporto creato:\n" + sim.getSelf().toString());
                            exit2 = true;
                        }else{
                            System.out.println("Input non valido\nscrivi \"esci\" per uscire oppure riprova con \"invio\"");
                            if(br.readLine().toLowerCase().trim().contains("esci")) exit2 = true;
                        }
                    }while(!exit2);
                    exit = true;
                }
                case "carica" ->{
                    if(new File(SERIAL_FILE).exists()){
                        System.out.println("Carico dai dati salvati in seriale");
                        sim = deserialize();
                        exit = true;
                    }else{
                        System.out.println("Non ci provare, non esiste il file, riprova con altro");
                    }
                }
                case "exit" ->System.exit(0);
                default -> System.out.println("Input non valido, riprova");
            }
        }while(!exit);
        //qui inizio la simulazione, genero voli casuali e faccio scorrere il tempo, libero quando il tempo è maggiore di quello del volo
        sim.start();
    }
}
