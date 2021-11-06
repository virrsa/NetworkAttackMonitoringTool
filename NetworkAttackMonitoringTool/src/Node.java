import java.util.ArrayList;
import java.util.Map;
public class Node {
    //class members
    private String name;
    private String coordinates;
    private boolean active;
    private boolean firewall;
    private ArrayList<Node> links;

    private boolean infected;
    //TODO: look for a better data structure to store attacks, need to store date and time along with type of virus
    private ArrayList<String> attacks;
    private int alerts;

    public Node(String nName, String nCoordinates, boolean nFirewall) {
        this.name = nName;
        this.coordinates = nCoordinates;
        this.active = true;
        this.firewall = nFirewall;
        this.links  = new ArrayList<Node>(0);

        this.infected = false;
        this.attacks = new ArrayList<String>(0);
        this.alerts = 0;
    }

    //getters, may add more as time goes on
    public String getName() { return this.name; }

    //class methods

    /*public void injectVirus() {
        if (this.firewall == true) {
            //add virus to attacks array
        }
        else {
            this.infected = true;
            //add virus to attacks array
        }

    }*/


}
