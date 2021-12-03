import java.util.Map;

public class VirusPrint {
    /* Print all the attacks in order by the number of attacks */
    public static void printVirusOrder(Map<String,Node> nodes, String userIn, int redSize, int greenSize, int blueSize) {

        if(redSize == blueSize && redSize == greenSize) {
            // Red, Green, Blue
            getRedAttackVirus(nodes, userIn, redSize);
            getGreenAttackVirus(nodes, userIn, greenSize);
            getBlueAttackVirus(nodes, userIn, blueSize);
        }

        if(redSize > greenSize && redSize > blueSize) {
            // Red is the largest
            getRedAttackVirus(nodes, userIn, redSize);

            if(greenSize > blueSize) {
                // Green is median and Blue is smallest
                getGreenAttackVirus(nodes, userIn, greenSize);
                getBlueAttackVirus(nodes, userIn, blueSize);
            } else {
                // Blue is median and Green is smallest
                getBlueAttackVirus(nodes, userIn, blueSize);
                getGreenAttackVirus(nodes, userIn, greenSize);
            }
        }

        if(greenSize > redSize && greenSize > blueSize) {
            // Green is the largest
            getGreenAttackVirus(nodes, userIn, greenSize);

            if(redSize > blueSize) {
                // Red is median and Blue is smallest
                getRedAttackVirus(nodes, userIn, redSize);
                getBlueAttackVirus(nodes, userIn, blueSize);
            } else {
                // Blue is median and Green is Red smallest
                getBlueAttackVirus(nodes, userIn, blueSize);
                getRedAttackVirus(nodes, userIn, redSize);
            }
        }

        if(blueSize > greenSize && blueSize > redSize) {
            // Blue is the largest
            getBlueAttackVirus(nodes, userIn, blueSize);

            if(redSize > greenSize) {
                // Red is median and Green is smallest
                getRedAttackVirus(nodes, userIn, redSize);
                getGreenAttackVirus(nodes, userIn, greenSize);
            } else {
                // Green is median and Red is smallest
                getGreenAttackVirus(nodes, userIn, greenSize);
                getRedAttackVirus(nodes, userIn, redSize);
            }
        }

    }

    public static boolean getRedAttackVirus(Map<String,Node> nodes, String userIn, int redSize) {
        if(redSize == 0) {return false;}        // If there is no red attack then return false
        Map.Entry<String, Attack> virus = nodes.get(userIn).getSpecificAttacks(" red");     // Go and grab all the red attacks
        outputToConsole(nodes, userIn, virus);  // Output it to the console
        return true;
    }

    public static boolean getBlueAttackVirus(Map<String,Node> nodes, String userIn, int blueSize) {
        if(blueSize == 0) {return false;}       // If there is no blue attack then return false
        Map.Entry<String, Attack> virus = nodes.get(userIn).getSpecificAttacks(" blue");    // Go and grab all the blue attacks
        outputToConsole(nodes, userIn, virus);  // Output it to the console
        return true;
    }

    public static boolean getGreenAttackVirus(Map<String,Node> nodes, String userIn, int greenSize) {
        if(greenSize == 0) {return false;}      // If there is no green attack then return false
        Map.Entry<String, Attack> virus = nodes.get(userIn).getSpecificAttacks(" green");   // Go and grab all the green attacks
        outputToConsole(nodes, userIn, virus);  // Output it to the console
        return true;
    }

    /* Outputs the following information: Node name, virus type, number of injections, the date and time of each injection */
    public static void outputToConsole(Map<String,Node> nodes, String userIn, Map.Entry<String, Attack> virus) {
        System.out.println("For node " + userIn + ", virus type" + virus.getKey() + " was injected " + nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().size() + " time(s).");
        System.out.println("Virus type" + virus.getKey() + " was injected at the following times: ");
        for (int i = 0; i < nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().size(); i++) {     // loops through all the attack dates
            System.out.println(nodes.get(userIn).getAttacks().get(virus.getKey()).getDate().get(i) + " at" + nodes.get(userIn).getAttacks().get(virus.getKey()).getTime().get(i));
        }
    }
}
