/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 4th, 2021
 *
 */

import java.util.*;

public class Graph {

    private Map<String, List<String> > map = new HashMap<>();   // HashMap that contains the edges the graph

    /*
     * Adds a new vertex to the graph
     */
    public void addVertex(String source) { map.put(source, new LinkedList<String>()); }

    /*
     * Adds an edge into the map
     */
    public void addEdge(String source, String destination, boolean bidirectional)
    {
        if (!map.containsKey(source)) { addVertex(source); }
        if (!map.containsKey(destination)) { addVertex(destination); }
        map.get(source).add(destination);
        if (bidirectional == true) { map.get(destination).add(source); }
    }

    /*
     * Counts the number of vertices
     */
    public int countVertices() { return map.keySet().size(); }

    /*
     * Counts the number of edges
     */
    public int countEdges(boolean bidirection)
    {
        int count = 0;
        for (String i : map.keySet()) { count = count + map.get(i).size(); }
        if (bidirection == true) { count = count / 2; }
        return count;
    }

    /*
     * Returns true/false if the graph contains a vertex at the given location
     */
    public boolean containsVertex(String vertexLocation) { if (map.containsKey(vertexLocation)) { return true; } else { return false; } }

    /*
     * Check if a graph has an edge or not
     */
    public void containsEdge(String srcVertex, String dstVertex)
    {
        if (map.get(srcVertex).contains(dstVertex)) { System.out.println("The graph has an edge between "+ srcVertex + " and " + dstVertex + "."); }
        else { System.out.println("There is no edge between "+ srcVertex + " and " + dstVertex + "."); }
    }

    /*
     * Essentially replaces the old graph with a new update graph with its new links
     */
    public void updateGraph(Map<String,Node> nodes, Graph graph)
    {
        int numberForm = 0;
        for(String node : nodes.keySet())
        {
            graph.addVertex(node);
            for(String link : nodes.get(node).getLinks().keySet())
            {
                graph.addEdge(node, link, false);   // Bidirectional was already setup therefore false
            }
        }
    }

    /*
     * Updates the graph(if asked) then proceeds to print it
     */
    public void printGraph(Map<String,Node> nodes, Graph graph, Boolean update)
    {
        if(update){ graph.updateGraph(nodes, graph); }   // update the graph if asked to

        System.out.print("\n");   // make sure we start after a new line
        for (String sourceNode : map.keySet())  // loop through all vertex's
        {
            System.out.print(sourceNode.toString() + ": "); // Prints the current vertex (node)

            if(sourceNode.length() < 15)    // This is set up so all the vertexes are aligned
            {
                int numSpace = sourceNode.length() - 15;
                String spacing = String.format("%1$"+numSpace+"s", "");
                System.out.print(spacing);    // Prints our spacing
            }

            if(map.get(sourceNode).isEmpty()) { System.out.print("none" + " "); }   // if the vertex has no edges then print accordingly
            int index = 1;  // Sets an index so then the " | " character doesn't print after the last edge is printed
            for (String connectedTo : map.get(sourceNode)) {    // loop through all a nodes connected node
                System.out.print(connectedTo.toString() + " "); // Prints the connected node
                if (map.get(sourceNode).size() != index) {  // If we haven't printed the last edge then print the " | " character
                    System.out.print("| "); // Print the pipe character
                }
                index++;    // Increment our index
            }
            System.out.print("\n"); // print a new line (formatting)
        }
        System.out.print("\n"); // print a new line (formatting)
    }


    /*
     * Connects a vertex(source) to its connecting node in a digit form
     */
    public static void addNumEdge(ArrayList<ArrayList<Integer>> numberGraph, int source, int destination) { numberGraph.get(source).add(destination); }

    /*
     * Prints the shortest distance between the source vertex and the destination vertex
     */
    public void outputShortestDistance(Map<String,Node> nodes, String sourceIn, String destIn) {

        this.updateGraph(nodes, this);  // make sure our graph is up-to-date :)

        Map<String, Integer> nodesToNum = new HashMap<>();    // a hashmap that will store our city name and its corresponding number value
                                                              // Ex. < Vancouver, 0 >
        int number = 0; // value to be stored with corresponding city
        for (String sourceNode : map.keySet())  // loop through the entire graph
        {
            nodesToNum.put(sourceNode, number); // put the city name + corresponding number into hashmap
            //System.out.println(sourceNode +": " +number);  // used to debug (make sure that the numbers are correct)
            number++;   // increment to the next corresponding digit
        }

        int vertices = countVertices();     // get and store the number of vertices in the graph
        ArrayList<ArrayList<Integer>> numberGraph = new ArrayList<ArrayList<Integer>>(vertices);    // Create a graph to store the number version
        for (int i = 0; i < vertices; i++) { numberGraph.add(new ArrayList<Integer>()); }   // Add an array of integers into our graph
                                                                                            // this is for our node connections
        for(String node : nodes.keySet())   // loop through all the cities (nodes)
        {
            for(String link : nodes.get(node).getLinks().keySet())  // loops through all the city connections (links)
            {
                for(String cityName : nodesToNum.keySet())   // loops through all the cities in the nodesToNum hashmap
                {
                    if(cityName.equals(link))   // if the link of the connected node is equal to the city node
                    {                           // we essentially want to store the links in the string hashmap
                                                // into our number hashmap (as its corresponding number)
                        int nodesNum = nodesToNum.get(node);    // get the number of the corresponding node (our source node)
                        int linkNum = nodesToNum.get(cityName); // get the link that connects to our source node (our destination node)
                        //System.out.println(node +": " +nodesNum +" connects to " +num +": " +linkNum);
                        addNumEdge(numberGraph, nodesNum, linkNum); // now add them to our number graph.
                    }                                               // Example:
                }                                                   // Vancouver(0) --> Tokyo(2)
            }                                                       // <0> --> <index 0> --> 2
        }                                                           // Vancouver(0) --> Chongqing(5)
                                                                    // <0> --> <index 0> ---> 2
                                                                    // <0> --> <index 1> ---> 5
        int numSource = 0, numDest = 0;     // Create the number of our source and destination
        for(String cityName : nodesToNum.keySet()) {    // just like before loops through all the cities in the nodesToNum hashmap
            if(cityName.equals(sourceIn)) { numSource = nodesToNum.get(cityName); }   // get the sourceIn corresponding number
            else if (cityName.equals(destIn)) { numDest = nodesToNum.get(cityName); }   // get the destIn corresponding number
        }
        
        int nodePath[] = new int[vertices];     // array of all the previous found nodes
        int distance[] = new int[vertices];     // the distance of the stored node from the source

        /* Attempts to run a BFS Algorithm but if it fails then we should let the user know and leave */
        if (BreadthFirstSearch(numberGraph, numSource, numDest, vertices, nodePath, distance) == false) {
            System.out.println("Given source: " +sourceIn +" and destination: " +destIn +" a safe path could not be found");
            return;
        }

        /* Otherwise, lets go print it :D */
        LinkedList<Integer> path = new LinkedList<Integer>();   // Store a linked list of the path that was taken
        int nodeLink = numDest;    // set our number destination to our link variable
        path.add(nodeLink);        // add our link variable to the end of the path
        while (nodePath[nodeLink] != -1) {    // Keep looping until we've reached a node with no valid links
            path.add(nodePath[nodeLink]);     // we added a connection between our link and a different node
            nodeLink = nodePath[nodeLink];       // we will now look from the new added node
        }

        // Print the distance
        System.out.println("\nThe path distance is " + distance[numDest] +" hop(s)!");

        // Print path
        int index = 1;      // Sets an index so then the " --> " character doesn't print after the last edge is printed
        for (int i = path.size() - 1; i >= 0; i--) {    // loop through our path array
            for (Map.Entry<String, Integer> numMap : nodesToNum.entrySet()) {   // loop through our numberr hashmap
                if(numMap.getValue() == path.get(i)) {  // does our path number value equal to our location in our number map?
                    System.out.print(numMap.getKey());  // Yes! That means we know the string value of that number print it!
                    if(path.size() != index) { System.out.print(" --> ");}  // If we haven't printed the last edge then print the " --> " character
                    index++;    // increment our index
                }
            }
        }
        System.out.println("\n"); // Formatting
    }
    /* Searches to find if a pathway is safe. This is private because this is a local function ONLY! */
    private static boolean BreadthFirstSearch(ArrayList<ArrayList<Integer>> numberGraph, int numSource, int numDest, int numVertices, int numPath[], int distance[]) {

        LinkedList<Integer> vertexQ = new LinkedList<Integer>();  // A queue which contains a list of all the adjacent vertex
        boolean visited[] = new boolean[numVertices];   // A boolean array that knows if a vertex is reachable

        for (int i = 0; i < numVertices; i++) { // Set defaults of all vertices
            visited[i] = false;                 // by default no vertex has been visited
            distance[i] = -1;                   // Set a unreachable distance
            numPath[i] = -1;                    // Path is unreachable as well
        }

        visited[numSource] = true;  // this is the start so its obviously has been visited
        distance[numSource] = 0;    // the distance to itself is 0 (not very far ay?) :)
        vertexQ.add(numSource);     // Add the source to the list

        while (!vertexQ.isEmpty()) {    // while the queue has a node to search for
            int nodeIndex = vertexQ.remove();   // remove the node from the queue
            for (int i = 0; i < numberGraph.get(nodeIndex).size(); i++) {   // loop through the entire graph
                if (visited[numberGraph.get(nodeIndex).get(i)] == false) {  // has the connected node been visited?
                    visited[numberGraph.get(nodeIndex).get(i)] = true;      // no? Well change it cause now we have :D
                    distance[numberGraph.get(nodeIndex).get(i)] = distance[nodeIndex] + 1;  // Increment the distance at the specified node
                    numPath[numberGraph.get(nodeIndex).get(i)] = nodeIndex;      // the connected node has found a safe passage to the previous one
                    vertexQ.add(numberGraph.get(nodeIndex).get(i));      // we will now restart but at the newly safe node

                    if (numberGraph.get(nodeIndex).get(i) == numDest) { return true; } // if our newly found safe node is the destination then we are done!
                }
            }
        }
        return false;   // We failed to find a safe way to reach the destination :(
    }
}
