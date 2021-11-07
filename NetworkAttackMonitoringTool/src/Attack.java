public class Attack {
    //class members
    private String node;
    private String type;

    private String date;
    private String time;

    //constructor
    public Attack(String aNode, String aType, String aDate, String aTime) {
        this.node = aNode;
        this.type = aType;
        this.date = aDate;
        this.time = aTime;
    }

    //getters
    public String getNode() { return this.node; }
    public String getType() { return this.type; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
}
