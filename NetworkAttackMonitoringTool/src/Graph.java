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
    public void countVertices() { System.out.println("Total number of vertices: "+ map.keySet().size()); }

    /*
     * Counts the number of edges
     */
    public void countEdges(boolean bidirection)
    {
        int count = 0;
        for (String i : map.keySet())
        {
            count = count + map.get(i).size();
        }
        if (bidirection == true)
        {
            count = count / 2;
        }
        System.out.println("Total number of edges: "+ count);
    }

    /*
     * Check if a graph has a vertex or not
     */
    public void containsVertex(String vertexLocation)
    {
        if (map.containsKey(vertexLocation)) { System.out.println("The graph contains "+ vertexLocation + " as a vertex."); }
        else { System.out.println("The graph does not contain "+ vertexLocation + " as a vertex."); }
    }

    /*
     * Check if a graph has an edge or not
     */
    public void containsEdge(String srcVertex, String dstVertex)
    {
        if (map.get(srcVertex).contains(dstVertex)) { System.out.println("The graph has an edge between "+ srcVertex + " and " + dstVertex + "."); }
        else { System.out.println("There is no edge between "+ srcVertex + " and " + dstVertex + "."); }
    }

    public void updateGraph(Map<String,Node> nodes, Graph graph)
    {
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
     * Prints the vertex with each edge
     */
    public void printGraph(Map<String,Node> nodes, Graph graph)
    {
        graph.updateGraph(nodes, graph);    // update the graph first

        System.out.print("\n");   // make sure we start after a new line
        for (String sourceNode : map.keySet())
        {
            System.out.print(sourceNode.toString() + ": ");

            if(sourceNode.length() < 15)
            {
                int numSpace = sourceNode.length() - 15;
                String s = String.format("%1$"+numSpace+"s", "");
                System.out.print(s);
            }

            if(map.get(sourceNode).isEmpty()) { System.out.print("none" + " "); }
            int index = 1;
            for (String connectedTo : map.get(sourceNode)) {
                System.out.print(connectedTo.toString() + " ");
                if (map.get(sourceNode).size() != index) {
                    System.out.print("--> ");
                }
                index++;
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
