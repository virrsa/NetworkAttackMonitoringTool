import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Node {
    //class members
    private String name;
    private String coordinates;
    private boolean active;
    private boolean firewall;
    private Map<String,Node> links = new HashMap<>();

    private boolean infected;
    private Map<String,Attack> attacks = new HashMap<>();
    private int numAttacks;
    private int alerts;
    private boolean outbreak;

    //constructor
    public Node(String nName, String nCoordinates, boolean nFirewall) {
        this.name = nName;
        this.coordinates = nCoordinates;
        this.active = true;
        this.firewall = nFirewall;

        this.infected = false;
        this.numAttacks = 0;
        this.alerts = 0;
        this.outbreak = false;
    }

    //getters, may add more as time goes on
    public String getName() { return this.name; }
    public boolean getActiveStatus() { return this.active; }
    public boolean getFirewallStatus() { return this.firewall; }
    public boolean getInfectedStatus() { return this.infected; }
    public boolean getOutbreakStatus() { return this.outbreak; }
    public String getCoordinates() { return this.coordinates; }
    public Map<String,Node> getLinks() { return this.links; }
    public Map<String, Attack> getAttacks() { return this.attacks; }


    //class methods
    //injects a virus into a node, and checks if the node generates an alert or triggers an outbreak
    public void injectVirus(String type, Attack aVirus) {

        //checks if the node has a firewall. If it does, it does not get infected.
        //instead it keeps track of the attacks attempted against the node.
        if (!this.firewall) {
            this.infected = true;
        }

        //if the node is active, inject the virus.
        if (this.active) {

            //checks if the same type of virus has already infected the node. If so, add the date and time to the array.
            //if the virus has already infected the node, add the date and time of the virus to the date/time arrays.
            if (this.attacks.containsKey(type)) {
                //ignores a virus if the same virus already exists within the list.
                if(this.attacks.get(type).getDate().contains(aVirus.getDate().get(0)) && this.attacks.get(type).getTime().contains(aVirus.getTime().get(0))) { return; }

                this.attacks.get(type).getDate().add(aVirus.getDate().get(0));
                this.attacks.get(type).getTime().add(aVirus.getTime().get(0));

                //increments number of attacks
                numAttacks++;
            }
            //if the virus hasn't infected the node, add the virus itself to the attack list.
            else {
                Attack newVirus = new Attack(aVirus);   // create the same attack at a new location in memory
                this.attacks.put(type, newVirus); // Store our location and virus into the nodes attack map

                //increments number of attacks
                numAttacks++;
            }

            //check for viruses that can cause alerts or outbreaks, or shut down the node if it has 6 viruses
            //if two or more virus injections of the same type occur within two minutes, it triggers an alert.
            if (this.attacks.get(type).getDate().size() >= 2 && this.firewall == false) {
                //checks if the previous virus has the same date as the new virus
                if (this.attacks.get(type).getDate().get(this.attacks.get(type).getDate().size()-1).equals(aVirus.getDate().get(0))) {
                    String[] parts1 = aVirus.getTime().get(0).split(":");
                    String[] parts2 = this.attacks.get(type).getTime().get(this.attacks.get(type).getTime().size()-1).split(":");
                    //checks if the viruses are within two minutes apart from each other
                    if (parts1[0].equals(parts2[0]) && Integer.valueOf(parts1[1]) - Integer.valueOf(parts2[1]) <= 2) {
                        System.out.println("Alert: Node " + this.name + " has been infected by multiple instances of the virus" + type + " on" + aVirus.getDate().get(0) + " at" + aVirus.getTime().get(0) + ".");
                        this.alerts++;
                    }
                }
            }
            //if 4 or more injections of the same type occur within four minutes, it triggers an outbreak.
            if (this.attacks.get(type).getDate().size() >= 4 && this.firewall == false) {
                //checks if the previous 3 viruses has the same date as the new virus
                if (this.attacks.get(type).getDate().get(this.attacks.get(type).getDate().size()-3).equals(aVirus.getDate().get(0))) {
                    String[] parts1 = aVirus.getTime().get(0).split(":");
                    String[] parts2 = this.attacks.get(type).getTime().get(this.attacks.get(type).getTime().size()-3).split(":");
                    //checks if the viruses are within four minutes apart from each other
                    if (parts1[0].equals(parts2[0]) && Integer.parseInt(parts1[1]) - Integer.parseInt(parts2[1]) <= 4) {
                        System.out.println("Outbreak triggered at node " + this.name + " on" +aVirus.getDate().get(0) + " at" + aVirus.getTime().get(0) + ".");
                        this.outbreak = true;
                        for(String node: this.links.keySet()) {this.links.get(node).injectVirus(type, aVirus);} // Injects a virus at all the current nodes connections
                    }
                }
            }
            //if there are a total of 6 viruses or more and at least 2 types of viruses, the node is put offline.
            else if (this.attacks.size() >= 2 && numAttacks >= 6 && this.firewall == false) {
                System.out.println("Node " + this.name + " has been permanently put offline.");
                this.active = false;
                for(String node : this.links.keySet()) {this.links.get(node).links.remove(this.name);}  // Remove all the cities connected to the current city
                this.links.keySet().removeAll(this.getLinks().keySet());    // Remove all the cities the current city connects to
            }
        }
    }
}
