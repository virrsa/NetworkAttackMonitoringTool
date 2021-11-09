import java.util.ArrayList;

public class Attack {
    //class members
    private String node;
    private String type;

    private ArrayList<String> date;
    private ArrayList<String> time;

    //constructor
    public Attack(String aNode, String aType, String aDate, String aTime) {
        this.node = aNode;
        this.type = aType;
        this.date = new ArrayList<String>(0);
        this.date.add(aDate);
        this.time = new ArrayList<String>(0);
        this.time.add(aTime);
    }

    //getters
    public String getNode() { return this.node; }
    public String getType() { return this.type; }
    public ArrayList<String> getDate() { return this.date; }
    public ArrayList<String> getTime() { return this.time; }
}
