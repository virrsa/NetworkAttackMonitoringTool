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
    //TODO: look for a better data structure to store attacks(?)
    private ArrayList<Attack> attacks;
    private int alerts;

    public Node(String nName, String nCoordinates, boolean nFirewall) {
        this.name = nName;
        this.coordinates = nCoordinates;
        this.active = true;
        this.firewall = nFirewall;
        this.links  = new ArrayList<Node>(0);

        this.infected = false;
        this.attacks = new ArrayList<Attack>(0);
        this.alerts = 0;
    }

    //getters, may add more as time goes on
    public String getName() { return this.name; }
    public boolean getActiveStatus() { return this.active; }
    public boolean getFirewallStatus() { return this.firewall; }
    public ArrayList<Attack> getAttacks() { return this.attacks; }

    //class methods
    public void injectVirus(Attack aVirus) {
        //TODO: add detection for viruses within a certain period of time to sound off an alert
        if (this.firewall == false) {
            this.infected = true;
        }
        this.attacks.add(aVirus);
    }


}
