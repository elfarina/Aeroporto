import java.util.HashMap;
import java.util.Map;

public class Gate {
    private String gateId;
    private Map<String, Integer> gateDistances = new HashMap<String, Integer>();
    public Gate(String gateId) {
        this.gateId = gateId;
    }
    public Gate(String gateId, Map<String, Integer> gateDistances) {
        this.gateId = gateId;
        this.gateDistances = gateDistances;
    }
    public String getGateId() {
        return gateId;
    }
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }
    public Map<String, Integer> getGateDistances() {
        return gateDistances;
    }
   public void setGateDistances(Map<String, Integer> gateDistances) {
        this.gateDistances = gateDistances;
   }
}
