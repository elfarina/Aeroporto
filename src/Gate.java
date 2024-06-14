import java.io.Serializable;

public class Gate implements Serializable {
    private String gateId;
    private boolean occuped = false;
    public Gate() {
        this.gateId = "-";
    }
    public Gate(String gateId) {
        this.gateId = gateId;
    }
    public boolean isOccuped() {
        return occuped;
    }
    public void isOccuped(boolean o) {
        this.occuped = o;
    }
    public String getGateId() {
        return gateId;
    }
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }
    public String toString() {
        if(isOccuped()) return gateId + " [OCCUPED]";
        else return gateId + " [FREE]";
    }
//    private boolean occuped = false;
//    private String gateId;
//    private Map<String, Integer> gateDistances = new HashMap<String, Integer>();
//    public Gate(String gateId) {
//        this.occuped = false;
//        this.gateId = gateId;
//    }
//    public Gate(String gateId, Map<String, Integer> gateDistances) {
//        this.occuped = false;
//        this.gateId = gateId;
//        this.gateDistances = gateDistances;
//    }
//    public boolean isOccuped() {
//        return occuped;
//    }
//    public void isOccuped(boolean occuped) {
//        this.occuped = occuped;
//    }
//    public String getGateId() {
//        return gateId;
//    }
//    public void setGateId(String gateId) {
//        this.gateId = gateId;
//    }
//    public Map<String, Integer> getGateDistances() {
//        return gateDistances;
//    }
//   public void setGateDistances(Map<String, Integer> gateDistances) {
//        this.gateDistances = gateDistances;
//   }

}
