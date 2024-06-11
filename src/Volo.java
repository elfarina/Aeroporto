import java.util.Map;

public class Volo {
    private DateTime departureTime;
    private DateTime arrivalTime;
    private String departureIATA;
    private String arrivalIATA;
    private int capacity;
    private int passengers;
    public Volo(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA, int capacity, int passengers) {
        this.departureTime = depTime;
        this.arrivalTime = arriTime;
        this.departureIATA = depIATA;
        this.arrivalIATA = arrIATA;
        this.capacity = capacity;
        this.passengers = passengers;
    }
    public Volo(DateTime depTime, DateTime arriTime, String depIATA, String arrIATA) {
        this.departureTime = depTime;
        this.arrivalTime = arriTime;
        this.departureIATA = depIATA;
        this.arrivalIATA = arrIATA;
        this.capacity = 0;
        this.passengers = 0;
    }
    public Volo(String depTime, String arrTime, String IATAS){
        this.departureTime = DateTime.create(depTime);
        this.arrivalTime = DateTime.create(arrTime);
        String[] data = IATAS.split("-");
        this.departureIATA = data[0];
        this.arrivalIATA = data[1];
    }
    public Volo(){
        this.departureTime = new DateTime();
        this.arrivalTime = new DateTime();
        this.departureIATA = "";
        this.arrivalIATA = "";
        this.capacity = 0;
        this.passengers = 0;
    }
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

}
