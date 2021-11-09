import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Node {
    //class members
    private String name;
    private String coordinates;
    private boolean active;
    private boolean firewall;
    private ArrayList<Node> links;

    private boolean infected;
    //TODO: look for a better data structure to store attacks(?)
    // Lets use a HashMap that has a key and the attack information
    //private ArrayList<Attack> attacks; (delete this line if you agree on using Maps)
    private Map<Integer, Attack> attacks = new HashMap<>();
    private int alerts;

    public Node(String nName, String nCoordinates, boolean nFirewall) {
        this.name = nName;
        this.coordinates = nCoordinates;
        this.active = true;
        this.firewall = nFirewall;
        this.links  = new ArrayList<Node>(0);

        this.infected = false;
        //this.attacks = new ArrayList<Attack>(0); //(delete this line if you agree)
        //this.attacks.put(0, attacks); // Not needed maps don't need to be initialized anyways. (delete this line if you agree)
        this.alerts = 0;
    }

       //getters, may add more as time goes on
    public String getName() { return this.name; }
    public boolean getActiveStatus() { return this.active; }
    public boolean getFirewallStatus() { return this.firewall; }
    public String getCoordinates() { return this.coordinates; }
    public Map<Integer, Attack> getAttacks() { return this.attacks; }   // added another getter


    //class methods
    public void injectVirus(int location, Attack aVirus) {
        //TODO: add detection for viruses within a certain period of time to sound off an alert
        if (this.firewall == false) {
            this.infected = true;
        }
        this.attacks.put(location,aVirus); // Store our location and virus into the nodes attack map
    }
}
