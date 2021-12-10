/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 10th, 2021
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
import java.util.*;
import java.io.FileNotFoundException;

public class main {
    public static void main(String args[]) throws FileNotFoundException {
        File attackInput;                               // takes in the input of the attack file
        Scanner attackScanner = null;                   // Scanner for the attack file
        Scanner fileScanner = new Scanner(System.in);   // Scanner for the string the user enters

        while (attackScanner == null) { // Loop until we have a valid file
            try {
                System.out.println("Enter attack file name: ");
                String fileName = fileScanner.nextLine(); // Set whatever the user entered as the fileName
                attackInput = new File(fileName);         // Get the attack file
                attackScanner = new Scanner(attackInput);   // Set the scanner
            } catch (Exception FileNotFoundException) { // If we fail to find the file
                System.out.println("File not Found. Expected Directory: NetworkAttackMonitoringTool-->NetworkAttackMonitoringTool");
            }
        }

        File graphInput = new File("Graph.txt");    // takes in the input of the graph file
        Scanner graphScanner = new Scanner(graphInput);     // Scanner for the graph file

        Map<String,Node> nodes = new HashMap<>();   //adds nodes to the hashmap given a Graph.txt file

        while (graphScanner.hasNext()) {                    // Loop until we've gone through the entire file
            String line = graphScanner.nextLine();         // get the next line
            if(line.charAt(0) == '-') { break; }          //breaks the loop when the connections/links portion begins
            String[] parts = line.split(", ");     // split the line and store it into a string array
            String nName = parts[0];                    // store the name into a string
            String nCoordinates = parts[1] + ", " + parts[2];
            if (parts.length == 4) { nodes.put(nName, new Node(nName, nCoordinates, true)); }   // add a node with a firewall
            else { nodes.put(nName, new Node(nName, nCoordinates, false)); }                    // add a node without a firewall
        }

        /* Adds connections/links to nodes */
        while (graphScanner.hasNext()) {                // Loop until we've gone through the entire scanner
            String line = graphScanner.nextLine();      // get the next line
            String[] parts = line.split(", ");    // split the line and store it into a string array
            nodes.get(parts[0]).getLinks().put(nodes.get(parts[1]).getName(),nodes.get(parts[1]));  // Store the link into its connecting node
            nodes.get(parts[1]).getLinks().put(nodes.get(parts[0]).getName(),nodes.get(parts[0]));  // Bidirectional (goes both ways)
        }                                                                                           // Ex. Tokyo <--> Miami

        /* Store each line of our attack.txt into a list */
        ArrayList<String> attackfile = new ArrayList<>(); // Create out attack list
        while(attackScanner.hasNext()) { attackfile.add(attackScanner.nextLine()); }    // Loop through our attack file and add it to the list

        /* Organize the attack dates into a list */
        ArrayList<String> dateList = new ArrayList<String>();    // Create our dates list
        for(String line : attackfile)  // Loop through each line of the file
        {
            String[] parts = line.split(",");   // split the line
            dateList.add(parts[2] + parts[3]); // parts[2] = yyyy-MM-dd ; parts[3] = HH:mm:ss
        }

        /* Sort the dates in order */
        dateList.sort(new CompareDates());

        /* We will delete elements in the list but want to keep the original list */
        ArrayList<String> attackfileClone = new ArrayList<String>(attackfile.size());   // Create clone array
        attackfileClone.addAll(attackfile); // Add all out data to the cloned array

        Graph graph = new Graph();  // Create a graph
        graph.printGraph(nodes, graph, true); // Print the graph!

        /* Inject attacks into nodes given an Attack.txt file */
        for(String i : dateList)    // Loops through our data list
        {
            String jLineCopy = null;    // To avoid copying the same line twice we will remove them
            for(String j : attackfileClone)     // Loops through our lines we saved from attackfile.txt
            {
                jLineCopy = j;      // Whatever line j is at copy it into jLineCopy
                String[] parts = j.split(",");
                if(i.compareTo(parts[2] + parts[3]) == 0)  // If the dates are the same then we can inject it!
                {
                    String node = parts[0]; // Store the node name
                    String type = parts[1]; // Store the type of node (red,green,blue,black)
                    String date = parts[2]; // Store the data
                    String time = parts[3]; // Store the time

                    Attack virus = new Attack(node, type, date, time);  // Create our virus
                    nodes.get(node).injectVirus(type, virus, graph, nodes); // Now inject it into the node
                    break;  // We don't need to keep looping through our attackFileClone
                }
            }
            attackfileClone.remove(jLineCopy);  // delete the line we found since we don't need it anymore
        }

        System.out.print("\n"); // console spacing purposes

        Scanner input = new Scanner(System.in);
        BoxPrint.welcomeMsg();      // Prints welcome message
        while (true) {
            BoxPrint.commandList(); // Calls to create our nice view of all the commands
            String userIn = input.nextLine().toUpperCase(); // Sets our input to uppercase

            if (userIn.equals("STAT")) {
                while (true) {
                    BoxPrint.stat();    // Prints our fancy prompt
                    userIn = input.nextLine().toUpperCase();    // Sets our user input to uppercase

                    if (userIn.equals("INF")) {
                        int count = 0;
                        /* Displays number of nodes that have been infected */
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getInfectedStatus()) {
                                System.out.println("Node " + node.getKey() + " has been infected.");
                                count++;
                            }
                        System.out.println("Number of nodes that have been infected: " + count);
                    }
                    else if (userIn.equals("FIR")) {
                        int count = 0;
                        /* Displays number of nodes that have a firewall */
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus()) {
                                System.out.println("Node " + node.getKey() + " has a firewall.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall: " + count);
                    }
                    else if (userIn.equals("FIA")) {
                        int count = 0;
                        /* Displays nodes that have a firewall and have been attacked */
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus() && node.getValue().getAttacks().size() > 0) {
                                System.out.println("Node " + node.getKey() + " has been attacked.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall and has been attacked: " + count);
                    }
                    else if (userIn.equals("OUT")) {
                        int count = 0;
                        /* Displays nodes that have gotten an outbreak */
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getOutbreakStatus()) {
                                System.out.println("Node " + node.getKey() + " caused an outbreak.");
                                count++;
                            }
                        System.out.println("Number of nodes that had an outbreak: " + count);
                    }
                    else if (userIn.equals("INA")) {
                        int count = 0;
                        /* Displays number of nodes that are inactive */
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
            /* Displays virus statistics given a node */
            else if (userIn.equals("VIRUS")) {
                while(true) {
                    System.out.println("What node would you like get virus statistics on? (END to exit) ");
                    userIn = input.nextLine().toUpperCase();

                    if (userIn.equals("END") || userIn.equals("EXIT")) { break; }

                    StopWatchInMicroSeconds timer = new StopWatchInMicroSeconds(); // Timer
                    userIn = formatCity(userIn); // Fixes the format of our city name

                    /* Check if the node that was entered was valid */
                    try {
                        timer.start();
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
                        timer.stop();   // Stop timer
                        System.out.println("Elapsed Time: " + timer.getElapsedTime() + " Microseconds or " + (timer.getElapsedTime() / 1000) +" Milliseconds\n");
                        timer.reset();
                    }
                    /* If the input is a node that doesn't exist, catch the exception and ask the node once again */
                    catch(Exception e) {
                        System.out.println("Please enter a valid node.\n");
                    }
                }
            }
            /* Creates safe routes */
            else if (userIn.equals("SAFE")) {
                System.out.println("Which source node would you like to use for the safe route?");
                String sourceInTemp = input.nextLine().toUpperCase();
                String sourceIn = formatCity(sourceInTemp); // Fixes the format of our city name
                System.out.println("Which destination node would you like to use for the safe route?");
                String destInTemp = input.nextLine().toUpperCase();
                String destIn = formatCity(destInTemp); // Fixes the format of our city name
                /* Check if the input is a valid node. If not, inform the user */
                try {
                    Node sNode = nodes.get(sourceIn);
                    Node dNode = nodes.get(destIn);
                    /* If one or both of the nodes are infected, the safe route cannot be generated */
                    if (sNode.getInfectedStatus() || dNode.getInfectedStatus()) { System.out.println("Safe route cannot be generated. One or both nodes are currently infected.");}
                    else { graph.outputShortestDistance(nodes, sourceIn, destIn); }
                }
                catch(Exception e) {
                    /* Only tell the user about an invalid entry if they didn't want to leave */
                    if(sourceIn.equals("Exit") || sourceIn.equals("End") || destIn.equals("Exit") || destIn.equals("End")) {} else { System.out.println("Please enter a valid node."); }
                }
            }
            /* Exits out of the program */
            else if (userIn.equals("END") || userIn.equals("EXIT")) break;
        }
        System.out.println("Exiting out of Network Attack Monitoring Tool.");
    }

    /* Fix the format of the user input to be exactly what our code expects
    * Example (Note our code converts userInput into all caps):
    * SAO PAULO --> Sao Paulo | VANCOUVER --> Vancouver
    */
    public static String formatCity(String city) {
        if(city.contains(" ")) {
            String[] parts = city.split(" ");
            StringBuilder fixedCity = new StringBuilder();
            for (int i = 0; i < parts.length; i++){
                parts[i] = parts[i].substring(0,1).toUpperCase() + parts[i].substring(1).toLowerCase();
                //if this is the final word, no need to add a space after it
                if (i == parts.length - 1) {
                    fixedCity.append(parts[i]);
                    break;
                }
                fixedCity.append(parts[i]).append(" ");
            }
            return fixedCity.toString();
        } else { return city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase(); }
    }
}
