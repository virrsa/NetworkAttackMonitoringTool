# Network Attack Monitoring Tool
Final term project for Data Structures/NET3004.

This project creates and monitors the status of nodes in real time,
given a file with the indicated nodes and attacks against the nodes.
Nodes are stored in an undirected graph and node connections are bi-directional. 
Attacks are sorted and injected in chronological order. Any node with a firewall 
cannot be attacked and instead keeps a record of the attacks that it stops.

Information that is contained within a node can be the following:
    
    Node characteristics:
    - Name of the node
    - Coordinates of the node
    - Whether the node is active
    - Whether the node has a firewall or not
    - Links to other nodes

    Attack characteristics:
    - Whether the node has been infected
    - A list of attacks against the node, including date and time
    - How many alerts have been generated relating to an attack

There are different types of attacks which inject specific viruses in a node. 
Viruses can be injected multiple times as long as the node is active.
Characteristics of viruses can be the following:

    - There are four types of attacks: red, blue, yellow, and black.
    - Viruses cannot infect nodes with firewalls. Instead, those nodes keep
      track of what viruses were detected on the firewall.
    - Viruses cannot infect inactive nodes.
    - If two viruses or more of the same virus are injected in a node 
      within two minutes, the node will generate an alert
    - If four or more of the same viruses are injected in a node within
      four minutes, it will cause an outbreak. All adjacent nodes will be 
      injected with the same type of virus.
    - If a node recieves 6 viruses in total of at least two types attacks,
      the node will become inactive, which removes all adjacent links. It 
      can no longer be attacked.

Finally, the project also has an interactive tool where the user can input commands to
view certain statistics of the nodes. It can display the following:

    - List of nodes that are infected
	- List of nodes with a firewall
	- List of nodes with a firewall and have been attacked
	- List of nodes that have caused an outbreak
	- List of nodes that have gone offline/is inactive

When given a node within the tool, it displays the following:

    - Whether the node has a firewall
	- Displays how many times a virus of each type attacked the node
	- Displays a list of virus types, listed in decreasing order based on 
      how many times it attacked the node (greatest to least)
	- Within the list of virus types, lists the time the virus type 
      has attacked the node in chronological order
	- Displays how many alerts were generated by the node, 
      and whether the node is active.

The tool also allows for the creation of safe routes. The user specifies the source node and
the destination node. Please note that the process of a safe route can only begin if both
the source and destination node have not been infected or gone offline. Once nodes have confirmed
to fit these requirements, then the safe route can be created.
The route will be along a path of nodes that have firewalls and/or have not been infected by
viruses. When calculating the routes are complete, the tool will display the following:

    - All possible paths from the source node to the destination node
	- The fastest path to the destination node (hop-wise)
	- If no paths are possible, it will display an error instead

Here are the descriptions for the files used in the project:

main.java: This is the main file where the code is executed. It parses the attack.txt and
graph.txt files to create an undirected graph and inject the attacks in chronological order.
This file also contains the code to run the monitoring tool as well.

Attack.java: This class is used for storing attributes of the virus, including name, type,
the node it infects, and the date and time of the virus injection.

Node.java: This class is used for storing attributes of the nodes. It stores the name, coordinates
whether it has a firewall, whether the node is active, and attack information. It also has
a virus injection method, which adds any attacks to a list, and executes alerts, outbreaks,
and shutdowns accordingly. It also contains a portion of the sorting function which sorts
viruses by how many attacks each type has for the monitoring tool.

Graph.java: This class is used to store the nodes and their connections. This node also
calculates safe routes based on a source and destination node.

CompareDates.java: This class takes the attack.txt file and sorts the attacks in chronological
order, which allow the viruses to be injected at appropriate times rather than be injected
randomly.

VirusPrint.java: This class sorts the viruses based on how many attacks were attempted in
each type, and prints the results from descending order, which is most attacks of a virus type
to the least attacks of a virus type.