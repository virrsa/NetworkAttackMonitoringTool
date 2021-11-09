import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.awt.desktop.AppReopenedEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class main {
    public static void main(String args[]) throws FileNotFoundException {
        File attackInput = new File("Attack.txt");
        File graphInput = new File("Graph.txt");
        Scanner attackScanner = new Scanner(attackInput);
        Scanner graphScanner = new Scanner(graphInput);

        //TODO: look up graphs in java
        //adds nodes to the arraylist given a Graph.txt file, may be changed later to another data type
        ArrayList<Node> nodes = new ArrayList<Node>(0);

        while (graphScanner.hasNext()) {
            String line = graphScanner.nextLine();
            //TODO: add links to the nodes, temporary break until then
           // Bad idea in case the number of dashes change.
           // Instead just check if the first character in the line is a dash.
            // if (line.equals("-------------------------------------")) {
           //     break;
           // }
            if(line.charAt(0) == '-')
            {
                break;
            }
            String[] parts = line.split(", ");
            String nName = parts[0];
            String nCoordinates = parts[1] + ", " + parts[2];
            //check if the node has a firewall
            if (parts.length == 4) {
                nodes.add(new Node(nName, nCoordinates, true));
            }
            else {
                nodes.add(new Node(nName, nCoordinates, false));
            }
        }

        /* Code that was used for myself to understand what was parsed
        for(int i = 0; i < nodes.size(); i++)
        {
            System.out.println(nodes.get(i).getName());
            System.out.print(""+nodes.get(i).getCoordinates());
            System.out.print("\t"+nodes.get(i).getActiveStatus());
            System.out.print("\t"+nodes.get(i).getFirewallStatus());
            System.out.println("\n");
        }
        */

        //injects attacks into nodes given an Attack.txt file.
        int location = 0;   // Location of where in the list of a virus can be stored.
                            // Example: Karachi[6] 6 = location
        String preNode = "";// The last node that was stored
        while (attackScanner.hasNext()) {
            String line = attackScanner.nextLine();
            String[] parts = line.split(", ");
            String node = parts[0];
            String type = parts[1];
            String date = parts[2];
            String time = parts[3];

            Attack virus = new Attack(node, type, date, time);

            for (int i = 0; i < nodes.size(); i++) {
                if (node.equals(nodes.get(i).getName())) {
                    if(preNode.equals(node))    // Check if we are still dealing with the same node
                    {
                        location++; // We are! That means we need to move to the next location
                    } else {location=0;}    // No! That means we need to reset for the next node
                    nodes.get(i).injectVirus(location,virus);   // Now store the location of our node with its virus
                    preNode = node; // Set our current node to the previous node
                    break;
                }
            }
        }

        /*
            This part just allows us to see our parsed data
         */
         for(int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).getName());     // print the node we are looking at right now
            if(nodes.get(i).getAttacks().size() == 0)       // does this node have no attacks?s
            {
                System.out.println("none"); // if so output none
            }
            for(int j = 0; j < nodes.get(i).getAttacks().size(); j++) // Otherwise, lets loop through our attacks
            {
                System.out.print(nodes.get(i).getAttacks().get(j).getType());   // Print the next attack
                System.out.print("\n");
            }
            System.out.println(" ");
        }
    }
}
