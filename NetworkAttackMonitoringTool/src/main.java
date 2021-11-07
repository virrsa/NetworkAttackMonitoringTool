import java.io.File;
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
            if (line.equals("-------------------------------------")) {
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

        //injects attacks into nodes given an Attack.txt file.
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
                    nodes.get(i).injectVirus(virus);
                    break;
                }
            }
        }

    }
}
