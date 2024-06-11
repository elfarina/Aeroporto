import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Terminal {
    private String name;
    public enum terminalType{
        INTERNATIONAL(0),
        NATIONAL(1);
        private final int TypeVal;
        terminalType(int typeVal){
            this.TypeVal = typeVal;
        }
        public int getTypeVal(){
            return TypeVal;
        }
        public String toString(){
            return switch(this){
                case INTERNATIONAL -> "International";
                case NATIONAL -> "National";
                default -> "Unknown";
            };
        }
    };
    ArrayList<Gate> gates = new ArrayList<Gate>();
    private terminalType terminalType;
    public Terminal(String name, terminalType terminalType, ArrayList<Gate> gates){
        this.name = name;
        this.terminalType = terminalType;
        this.gates = gates;
    }
    public Terminal(String name, terminalType terminalType){
        this.name = name;
        this.terminalType = terminalType;
    }
    public Terminal(){
        this.terminalType = terminalType.NATIONAL;
        this.name = "Unsetted";
    }
    public Terminal(String name, String type){
        this.name = name;
        switch(type.toLowerCase()){
            case "international": this.terminalType = terminalType.INTERNATIONAL; break;
            case "national": this.terminalType = terminalType.NATIONAL; break;
            default: this.terminalType = terminalType.NATIONAL; break; //da gestire poi casomai
        }
    }
    public Terminal(String name, String type, List<String> gatesName){
        this.name = name;
        switch(type.toLowerCase()){
            case "international": this.terminalType = terminalType.INTERNATIONAL; break;
            case "national": this.terminalType = terminalType.NATIONAL; break;
            default: this.terminalType = terminalType.NATIONAL; break; //da gestire poi casomai
        }
        this.gates = new ArrayList<>();
        for(String g : gatesName) this.gates.add(new Gate(g));
    }
    public ArrayList<Gate> getGates(){
        return gates;
    }
    public void setGates(ArrayList<Gate> gates){
        this.gates = gates;
    }
    public void addGate(Gate gate){
        gates.add(gate);
    }
    public boolean removeGate(Gate gate){
        return gates.remove(gate);
    }
    public boolean isEmpty(){
        return gates.isEmpty();
    }
    public boolean contains(String gateId){
        for (Gate gate : gates) if (gate.getGateId().equals(gateId)) return true;
        return false;
    }

    public terminalType getTerminalType(){return terminalType;}
    public void setTerminalType(terminalType terminalType){this.terminalType = terminalType;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String toString(){
        String tmp = "Terminal: " + this.name + " (" + this.terminalType.toString() + ") {";
        for(Gate gate : gates){
            tmp += " " + gate.getGateId() + " ";
        }
        return tmp + "};";
    }
}
