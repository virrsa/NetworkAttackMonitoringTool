/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 5th, 2021
 *
 *  This project creates and monitors the status of nodes in real time, given a file with the indicated nodes
 *  and attacks against the nodes.
 *  Nodes are stored in an undirected graph and node connections are bi-directional. Attacks are sorted and injected
 *  in chronological order. Any node with a firewall cannot be attacked and instead keeps a record of the attacks that it
 *  stops.
 *  If two viruses or more of the same virus are injected in a node within two minutes, the node will generate an alert.
 *  If four or more of the same viruses are injected in a node within four minutes, it will cause an outbreak.
 *  All adjacent nodes will be injected with the same type of virus.
 *  If a node receives 6 viruses in total of at least two types attacks, the node will become inactive, which removes all
 *  adjacent links. It can no longer be attacked.
 *
 *  Finally, the project provides a tool that you can view statistics of the amount of nodes who were affected by attacks,
 *  which nodes have generated alerts, caused outbreaks, went inactive, and more. You can also specify a node to look at the
 *  virus statistics, whether it has a firewall, how many alerts it has generated and whether it is active.
 *  Lastly, the tool allows you to create a safe route between two nodes. It will find the shortest path between the two
 *  nodes, granted the nodes in that path have not been infected.
 *  NOTE: If the source or destination node have been infected, a safe route cannot be created.
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileNotFoundException;

public class main {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        File attackInput = new File("Attack.txt");
        File graphInput = new File("Graph.txt");
        Scanner attackScanner = new Scanner(attackInput);
        Scanner graphScanner = new Scanner(graphInput);

        //adds nodes to the hashmap given a Graph.txt file
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
            String[] parts = i.split(",");
            dateList.add(parts[2] + parts[3]); // parts[2] = yyyy-MM-dd ; parts[3] = HH:mm:ss
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

        Graph graph = new Graph();  // create a graph
        graph.printGraph(nodes, graph, true); // Want to see the connections uncomment me then!

        //injects attacks into nodes given an Attack.txt file.
        for(String i : dateList)    // Loops through our data list
        {
            String jLineCopy = null;    // To avoid copying the same line twice we will remove them
            for(String j : attackfileClone)     // Loops through our lines we saved from attackfile.txt
            {
                jLineCopy = j;      // Whatever line j is at copy it into jLineCopy
                String[] parts = j.split(",");
                if(i.compareTo(parts[2] + parts[3]) == 0)  // If the dates are the same then we can inject it!
                {
                    String node = parts[0];
                    String type = parts[1];
                    String date = parts[2];
                    String time = parts[3];

                    Attack virus = new Attack(node, type, date, time);
                    nodes.get(node).injectVirus(type, virus, graph, nodes);
                    break;  // We don't need to keep looping through our attackFileClone
                }
            }
            attackfileClone.remove(jLineCopy);  // delete the line we found se we don't need it anymore
        }

        System.out.print("\n"); //output spacing purposes

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Network Attack Monitoring Tool.");
        while (true) {
            System.out.println("Would you like to view node statistics, virus statistics or create a safe route? STAT/VIRUS/SAFE/END (Statistics/Viruses/Safe Routes):");
            String userIn = input.nextLine().toUpperCase();

            if (userIn.equals("STAT")) {
                while (true) {
                    System.out.println("Enter INF/FIR/FIA/OUT/INA/END (Nodes Infected/Nodes with Firewall/Firewalls Attacked/Nodes with outbreaks/Inactive Nodes):");
                    userIn = input.nextLine().toUpperCase();

                    if (userIn.equals("INF")) {
                        int count = 0;
                        //displays number of nodes that have been infected
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getInfectedStatus()) {
                                System.out.println("Node " + node.getKey() + " has been infected.");
                                count++;
                            }
                        System.out.println("Number of nodes that have been infected: " + count);
                    }
                    else if (userIn.equals("FIR")) {
                        int count = 0;
                        //displays number of nodes that have a firewall
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus()) {
                                System.out.println("Node " + node.getKey() + " has a firewall.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall: " + count);
                    }
                    else if (userIn.equals("FIA")) {
                        int count = 0;
                        //displays nodes that have a firewall and have been attacked
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus() == true && node.getValue().getAttacks().size() > 0) {
                                System.out.println("Node " + node.getKey() + " has been attacked.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall and has been attacked: " + count);
                    }
                    else if (userIn.equals("OUT")) {
                        int count = 0;
                        //displays nodes that have gotten an outbreak
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getOutbreakStatus()) {
                                System.out.println("Node " + node.getKey() + " caused an outbreak.");
                                count++;
                            }
                        System.out.println("Number of nodes that had an outbreak: " + count);
                    }
                    else if (userIn.equals("INA")) {
                        int count = 0;
                        //displays number of nodes that are inactive
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (!node.getValue().getActiveStatus()) {
                                System.out.println("Node " + node.getKey() + " is inactive.");
                                count++;
                            }
                        System.out.println("Number of nodes that are inactive: " + count);
                    }
                    else if (userIn.equals("END") || userIn.equals("EXIT")) {
                        break;
                    }
                }
            }
            //displays virus statistics given a node
            else if (userIn.equals("VIRUS")) {
                while(true) {
                    System.out.println("What node would you like get virus statistics on? (END to exit) ");
                    userIn = input.nextLine().toUpperCase();

                    if (userIn.equals("END") || userIn.equals("EXIT")) {
                        break;
                    }

                    userIn = formatCity(userIn); // Fixes the format of our city name
                    //check if the node that was entered was valid
                    try {
                        if (nodes.get(userIn).getFirewallStatus()) {
                            System.out.println("Node " + userIn + " has a firewall, thus has not been infected. Here are the records of the viruses that it blocked:");
                        }
                        /* Gets the size of all the attacks if there is no attacks then size is set by default to zero */
                        nodes.get(userIn).sortArrays();
                        if (nodes.get(userIn).getActiveStatus()) {
                            System.out.println("Node " + userIn + " has generated " + nodes.get(userIn).getAlerts() + " alerts and is currently active.");
                        }
                        else {
                            System.out.println("Node " + userIn + " has generated " + nodes.get(userIn).getAlerts() + " alerts and is currently inactive.");
                        }

                    }
                    //if the input is a node that doesn't exist, catch the exception and ask the node once again
                    catch(Exception e) {
                        System.out.println("Please enter a valid node.");
                    }
                }
            }
            //creates safe routes
            else if (userIn.equals("SAFE")) {
                System.out.println("Which source node would you like to use for the safe route?");
                String sourceInTemp = input.nextLine().toUpperCase();
                String sourceIn = formatCity(sourceInTemp); // Fixes the format of our city name
                System.out.println("Which destination node would you like to use for the safe route?");
                String destInTemp = input.nextLine().toUpperCase();
                String destIn = formatCity(destInTemp); // Fixes the format of our city name
                //check if the input is a valid node. If not, inform the user
                try {

                    Node sNode = nodes.get(sourceIn);
                    Node dNode = nodes.get(destIn);
                    //if one or both of the nodes are infected, the safe route cannot be generated
                    if (sNode.getInfectedStatus() || dNode.getInfectedStatus()) { System.out.println("Safe route cannot be generated. One or both nodes are currently infected.");}
                    else { graph.outputShortestDistance(nodes, sourceIn, destIn); }
                }
                catch(Exception e) {
                    System.out.println("Please enter a valid node.");
                }
            }
            //exits out of the program
            else if (userIn.equals("END") || userIn.equals("EXIT")) {
                break;
            }
        }
        System.out.println("Exiting out of Network Attack Monitoring Tool.");
    }

    /* Fix the format of the user input to be exactly what our code expects
    * Example (Note our code converts userInput into all caps):
    * SAO PAULO --> Sao Paulo
    */
    public static String formatCity(String city) {
        if(city.contains(" ")) {
            String[] parts = city.split(" ");
            String fixedCity = "";
            for (int i = 0; i < parts.length; i++){
                parts[i] = parts[i].substring(0,1).toUpperCase() + parts[i].substring(1).toLowerCase();
                //if this is the final word, no need to add a space after it
                if (i == parts.length - 1) {
                    fixedCity = fixedCity + parts[i];
                    break;
                }
                fixedCity = fixedCity + parts[i] + " ";
            }
            return fixedCity;
        } else { return city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase(); }
    }
}
