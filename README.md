# Network Attack Monitoring Tool
Final term project for Data Structures/NET3004.

This project creates and monitors the status of nodes in real time,
given a file with the indicated nodes and attacks against the nodes.

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

