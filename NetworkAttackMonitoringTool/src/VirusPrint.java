/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 5th, 2021
 *
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class VirusPrint {
    /* Print all the attacks in order by the number of attacks */
    public static void printVirusOrder(Node node, int redSize, int greenSize, int blueSize, int blackSize) {

        Integer[] colourArray = {redSize, greenSize, blueSize, blackSize};  // place all the sizes into an array
        Arrays.sort(colourArray, Collections.reverseOrder());               // sort the array from greatest to least

        boolean checkGreen = false, checkRed = false, checkBlue = false, checkBlack = false;    // default nothing has been checked
        for(Integer i : colourArray) {  // loop through the entire array
            if(i == greenSize && !checkGreen) { getGreenAttackVirus(node, greenSize); checkGreen = true; }  // if green is the biggest print it and set the check to true
            else if(i == redSize && !checkRed) { getRedAttackVirus(node, redSize); checkRed = true; }       // if red is the biggest print it and set the check to true
            else if(i == blueSize && !checkBlue) { getBlueAttackVirus(node, blueSize); checkBlue = true; }  // if blue is the biggest print it and set the check to true
            else if(i == blackSize && !checkBlack) { getBlackAttackVirus(node, blackSize); checkBlack = true; } // if black is the biggest print it and set the check to true
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

    public static boolean getBlackAttackVirus(Node node, int blueSize) {
        String type = " black";
        if(blueSize == 0) {return false;}       // If there is no black attack then return false
        Map.Entry<String, Attack> virus = node.getSpecificAttacks(" black");     // Go and grab all the black attacks
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
