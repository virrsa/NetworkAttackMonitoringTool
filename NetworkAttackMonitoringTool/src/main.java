import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.awt.desktop.AppReopenedEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
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

        //TODO: Idk whatever is after graphs
        //adds nodes to the hashmap given a Graph.txt file, may be changed later to another data type
        Map<String,Node> nodes = new HashMap<>();

        while (graphScanner.hasNext()) {
            String line = graphScanner.nextLine();
            //breaks the loop when the connections/links portion begins
            if(line.charAt(0) == '-')
            {
                break;
            }
            String[] parts = line.split(", ");
            String nName = parts[0];
            String nCoordinates = parts[1] + ", " + parts[2];
            //check if the node has a firewall
            if (parts.length == 4) {
                nodes.put(nName, new Node(nName, nCoordinates, true));
            }
            else {
                nodes.put(nName, new Node(nName, nCoordinates, false));
            }
        }

        //adds connections/links to nodes
        while (graphScanner.hasNext()) {
            String line = graphScanner.nextLine();
            String[] parts = line.split(", ");
            nodes.get(parts[0]).getLinks().put(nodes.get(parts[1]).getName(),nodes.get(parts[1]));
            nodes.get(parts[1]).getLinks().put(nodes.get(parts[0]).getName(),nodes.get(parts[0]));
        }

        //injects attacks into nodes given an Attack.txt file.
        while (attackScanner.hasNext()) {
            String line = attackScanner.nextLine();
            String[] parts = line.split(", ");
            String node = parts[0];
            String type = parts[1];
            String date = parts[2];
            String time = parts[3];

            Attack virus = new Attack(node, type, date, time);

            nodes.get(node).injectVirus(type, virus);
        }

        Graph graph = new Graph();
        for(String node : nodes.keySet())
        {
            graph.addVertex(node);
            for(String link : nodes.get(node).getLinks().keySet())
            {
                graph.addEdge(node, link, false);   // Biodirectional was already setup therefore false
            }
        }
        //graph.printGraph(); // Want to see the connections uncomment me then!
    }
}
