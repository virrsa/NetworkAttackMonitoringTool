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

        //adds nodes to the arraylist given a Graph.txt file
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

    }
}
