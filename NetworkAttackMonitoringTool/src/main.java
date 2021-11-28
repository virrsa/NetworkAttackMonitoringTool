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
        //graph.printGraph(nodes, graph); // Want to see the connections uncomment me then!

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

                   // can be removed, but if you want to see it working properly uncomment below
                   // System.out.println(parts[0] + " "+parts[2] + " "+ parts[3]);
                   // System.out.println("----------------------------");

                    Attack virus = new Attack(node, type, date, time);
                    nodes.get(node).injectVirus(nodes, type, virus);
                    break;  // We don't need to keep looping through our attackFileClone
                }
            }
            attackfileClone.remove(jLineCopy);  // delete the line we found se we don't need it anymore
        }
        //graph.printGraph(nodes, graph); // Want to see the connections uncomment me then!

        System.out.println(" ");

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Network Attack Monitoring Tool.");
        while (true) {
            System.out.println("Would you like to view node statistics or viruses? STAT/VIRUS/END (Statistics/Viruses):");
            String userIn = input.nextLine();
            if (userIn.equals("STAT") || userIn.equals("stat")) {
                while (true) {
                    System.out.println("Enter INF/FIR/FIA/OUT/INA/END (Nodes Infected/Nodes with Firewall/Firewalls Attacked/Nodes with outbreaks/Inactive Nodes):");
                    userIn = input.nextLine();

                    if (userIn.equals("INF") || userIn.equals("inf")) {
                        int count = 0;
                        //displays number of nodes that have been infected
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getInfectedStatus()) {
                                System.out.println("Node " + node.getKey() + " has been infected.");
                                count++;
                            }
                        System.out.println("Number of nodes that have been infected: " + count);
                    }
                    else if (userIn.equals("FIR") || userIn.equals("fir")) {
                        int count = 0;
                        //displays number of nodes that have a firewall
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus()) {
                                System.out.println("Node " + node.getKey() + " has a firewall.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall: " + count);
                    }
                    else if (userIn.equals("FIA") || userIn.equals("fia")) {
                        int count = 0;
                        //displays nodes that have a firewall and have been attacked
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getFirewallStatus() == true && node.getValue().getAttacks().size() > 0) {
                                System.out.println("Node " + node.getKey() + " has been attacked.");
                                count++;
                            }
                        System.out.println("Number of nodes that have a firewall and has been attacked: " + count);
                    }
                    else if (userIn.equals("OUT") || userIn.equals("out")) {
                        int count = 0;
                        //displays nodes that have gotten an outbreak
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (node.getValue().getOutbreakStatus()) {
                                System.out.println("Node " + node.getKey() + " caused an outbreak.");
                                count++;
                            }
                        System.out.println("Number of nodes that had an outbreak: " + count);
                    }
                    else if (userIn.equals("INA") || userIn.equals("ina")) {
                        int count = 0;
                        //displays number of nodes that are inactive
                        for (Map.Entry<String, Node> node : nodes.entrySet())
                            if (!node.getValue().getActiveStatus()) {
                                System.out.println("Node " + node.getKey() + " is inactive.");
                                count++;
                            }
                        System.out.println("Number of nodes that are inactive: " + count);
                    }
                    else if (userIn.equals("END") || userIn.equals("end")) {
                        break;
                    }
                }
            }
            else if (userIn.equals("VIRUS") || userIn.equals("virus")) {
                while(true) {
                    System.out.println("What node would you like get virus statistics on? (END to exit) ");
                    userIn = input.nextLine();
                    if (userIn.equals("END") || userIn.equals("end")) {
                        break;
                    }
                    userIn = userIn.substring(0,1).toUpperCase() + userIn.substring(1).toLowerCase();
                    //TODO: Sort arrays by size(???)
                    //prints viruses, along with the date and time it was infected on
                    try {
                        for (Map.Entry<String, Attack> virus : nodes.get(userIn).getAttacks().entrySet()) {
                            System.out.println("For node " + userIn + ", virus type" + virus.getKey() + " was injected " + nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().size() + " time(s).");
                            System.out.println("Virus type" + virus.getKey() + " was injected at the following times: ");
                            for (int i = 0; i < nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().size(); i++) {
                                System.out.println(nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().get(i) + " at" + nodes.get(userIn).getAttacks().get(virus.getKey()).getTime().get(i));
                            }
                        }
                    }
                    //if the input is a node that doesn't exist, catch the exception and ask the node once again
                    catch(Exception e) {
                        System.out.println("Please enter a valid node.");
                    }
                }
            }
            else if (userIn.equals("END") || userIn.equals("end")) {
                break;
            }
        }
        System.out.println("Exiting out of Network Attack Monitoring Tool.");
        //TODO: Create safe routes
    }
}
