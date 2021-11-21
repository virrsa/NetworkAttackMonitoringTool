import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.awt.*;
import java.awt.desktop.AppReopenedEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.stream.StreamSupport;

public class main {
    public static void main(String args[]) throws FileNotFoundException {
        File attackInput = new File("Attack.txt");
        File graphInput = new File("Graph.txt");
        Scanner attackScanner = new Scanner(attackInput);
        Scanner graphScanner = new Scanner(graphInput);

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

        // Store each line of our attack.txt into a list
        ArrayList<String> attackfile = new ArrayList<String>();
        while(attackScanner.hasNext())
        {
            attackfile.add(attackScanner.nextLine());
        }

        // Organize the attack dates into a list
        ArrayList<String> dateList = new ArrayList<String>();
        for(String i : attackfile)
        {
            String[] parts = i.split(", ");
            dateList.add(parts[2]);
        }

        // Sort the dates in order
        Collections.sort(dateList, new CompareDates());

        // We will be deleting elements in the list but what to keep the original list
        ArrayList<String> attackfileClone = new ArrayList<String>(attackfile.size());
        for(String i : attackfile)
        {
            attackfileClone.add(i); // since we aren't changing the data of i we don't need to make a copy of i itself
                                    // hence, the data of attackfileClone is at the same location of attackfile
        }

        //injects attacks into nodes given an Attack.txt file.
        for(String i : dateList)    // Loops through our data list
        {
            String jLineCopy = null;    // To avoid copying the same line twice we will remove them
            for(String j : attackfileClone)     // Loops through our lines we saved from attackfile.txt
            {
                jLineCopy = j;      // Whatever line j is at copy it into jLineCopy
                String[] parts = j.split(", ");
                if(i.compareTo(parts[2]) == 0)  // If the dates are the same then we can inject it!
                {
                    String node = parts[0];
                    String type = parts[1];
                    String date = parts[2];
                    String time = parts[3];

                   // can be removed, but if you want to see it working properly uncomment below
                   // System.out.println(parts[0] + " "+parts[2] + " "+ parts[3]);
                   // System.out.println("----------------------------");

                    Attack virus = new Attack(node, type, date, time);
                    nodes.get(node).injectVirus(type, virus);
                    break;  // We don't need to keep looping through our attackFileClone
                }
            }
            attackfileClone.remove(jLineCopy);  // delete the line we found se we don't need it anymore
        }

        Graph graph = new Graph();
        for(String node : nodes.keySet())
        {
            graph.addVertex(node);
            for(String link : nodes.get(node).getLinks().keySet())
            {
                graph.addEdge(node, link, false);   // Bidirectional was already setup therefore false
            }
        }
        //graph.printGraph(); // Want to see the connections uncomment me then!
    }
}
