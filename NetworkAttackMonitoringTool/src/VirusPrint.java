import java.util.Map;

public class VirusPrint {
    /* Print all the attacks in order by the number of attacks */
    public static void printVirusOrder(Node node, int redSize, int greenSize, int blueSize) {

        if(redSize == blueSize && redSize == greenSize) {
            // Red, Green, Blue
            getRedAttackVirus(node,redSize);
            getGreenAttackVirus(node,greenSize);
            getBlueAttackVirus(node,blueSize);
        }

        if(redSize > greenSize && redSize > blueSize) {
            // Red is the largest
            getRedAttackVirus(node,redSize);

            if(greenSize > blueSize) {
                // Green is median and Blue is smallest
                getGreenAttackVirus(node,greenSize);
                getBlueAttackVirus(node,blueSize);
            } else {
                // Blue is median and Green is smallest
                getBlueAttackVirus(node,blueSize);
                getGreenAttackVirus(node,greenSize);
            }
        }

        if(greenSize > redSize && greenSize > blueSize) {
            // Green is the largest
            getGreenAttackVirus(node,greenSize);

            if(redSize > blueSize) {
                // Red is median and Blue is smallest
                getRedAttackVirus(node,redSize);
                getBlueAttackVirus(node,blueSize);
            } else {
                // Blue is median and Green is Red smallest
                getBlueAttackVirus(node,blueSize);
                getRedAttackVirus(node,redSize);
            }
        }

        if(blueSize > greenSize && blueSize > redSize) {
            // Blue is the largest
            getBlueAttackVirus(node,blueSize);

            if(redSize > greenSize) {
                // Red is median and Green is smallest
                getRedAttackVirus(node,redSize);
                getGreenAttackVirus(node,greenSize);
            } else {
                // Green is median and Red is smallest
                getGreenAttackVirus(node,greenSize);
                getRedAttackVirus(node,redSize);
            }
        }

    }

    public static boolean getRedAttackVirus(Node node, int redSize) {
        String type = " red";
        if(redSize == 0) {return false;}        // If there is no red attack then return false
        Map.Entry<String, Attack> virus = node.getSpecificAttacks(" red");     // Go and grab all the red attacks
        outputToConsole(node, virus, type);  // Output it to the console
        return true;
    }

    public static boolean getBlueAttackVirus(Node node, int blueSize) {
        String type = " blue";
        if(blueSize == 0) {return false;}       // If there is no blue attack then return false
        Map.Entry<String, Attack> virus = node.getSpecificAttacks(" blue");     // Go and grab all the blue attacks
        outputToConsole(node, virus, type);  // Output it to the console
        return true;
    }

    public static boolean getGreenAttackVirus(Node node, int greenSize) {
        String type = " green";
        if(greenSize == 0) {return false;}      // If there is no green attack then return false
        Map.Entry<String, Attack> virus = node.getSpecificAttacks(" green");   // Go and grab all the green attacks
        outputToConsole(node, virus, type);  // Output it to the console
        return true;
    }

    /* Outputs the following information: Node name, virus type, number of injections, the date and time of each injection */
    public static void outputToConsole(Node node, Map.Entry<String, Attack> virus, String type) {
        System.out.println("For node " + node.getName() + ", virus type" + virus.getKey() + " was injected " + node.getTypeSize(type) + " time(s).");
        System.out.println("Virus type" + virus.getKey() + " was injected at the following times: ");
        for (int i = 0; i < node.getAttacks().get(virus.getKey()).getDate().size(); i++) {     // loops through all the attack dates
            System.out.println(node.getAttacks().get(virus.getKey()).getDate().get(i) + " at" + node.getAttacks().get(virus.getKey()).getTime().get(i));
        }
    }
}
