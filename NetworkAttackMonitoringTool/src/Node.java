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
    private Map<String,Node> links = new HashMap<>();;

    private boolean infected;
    private Map<String,Attack> attacks = new HashMap<>();
    private int alerts;

    //constructor
    public Node(String nName, String nCoordinates, boolean nFirewall) {
        this.name = nName;
        this.coordinates = nCoordinates;
        this.active = true;
        this.firewall = nFirewall;

        this.infected = false;
        this.alerts = 0;
    }

    //getters, may add more as time goes on
    public String getName() { return this.name; }
    public boolean getActiveStatus() { return this.active; }
    public boolean getFirewallStatus() { return this.firewall; }
    public String getCoordinates() { return this.coordinates; }
    public Map<String,Node> getLinks() { return this.links; }
    public Map<String, Attack> getAttacks() { return this.attacks; }


    //class methods
    public void injectVirus(String type, Attack aVirus) {
        //TODO: add detection for viruses within a certain period of time to sound off an alert
        if (this.firewall == false) {
            this.infected = true;
        }

        //checks if the same type of virus has already infected the node. If so, add the date and time to the array.
        //if the virus has already infected the node, add the date and time of the virus to the date/time arrays.
        if (this.attacks.containsKey(type)) {
            this.attacks.get(type).getDate().add(aVirus.getDate().get(0));
            this.attacks.get(type).getTime().add(aVirus.getTime().get(0));
        }
        //if the virus hasn't infected the node, add the virus itself to the attack list.
        else {
            this.attacks.put(type, aVirus); // Store our location and virus into the nodes attack map
        }

    }
}
