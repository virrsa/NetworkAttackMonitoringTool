/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: December 10th, 2021
 *
 */
public class BoxPrint
{
    public static void welcomeMsg()
    {
        String welcome = ("  Welcome to the Network Attack Monitoring Tool.");
        int n = welcome.length();

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.println();
        System.out.print("! " + welcome + "    !\n");

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.print("\n");
    }
    public static void commandList()
    { //statistics, virus statistics or create a safe route? STAT/VIRUS/SAFE/END (Statistics/Viruses/Safe Routes
        String command = ("Command Entry  -   Full Name");
        String CMD1     =("    STAT       -   Statistics");
        String CMD2     =("    VIRUS      -   Viruses");
        String CMD3     =("    SAFE       -   Safe Routes");
        String end      =("    END        -   END");

        int n = CMD3.length();  // Largest command entry

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.println();

        System.out.print("-+ " + command);
        System.out.format("%5s", "-");
        System.out.print("+\n");

        System.out.print("-> " + CMD1);
        System.out.format("%3s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD2);
        System.out.format("%6s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD3);
        System.out.format("%2s", "");
        System.out.print("<-\n");

        System.out.print("-> " + end);
        System.out.format("%10s", "");
        System.out.print("<-\n");

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.println("\nPlease enter a command: ");
    }


    public static void stat()
    { //System.out.println("Enter INF/FIR/FIA/OUT/INA/END (Nodes Infected/Nodes with Firewall/Firewalls Attacked/Nodes with outbreaks/Inactive Nodes):");

        String command = ("Command Entry  -   Full Name");
        String CMD1     =("    INF        -   Nodes Infected");
        String CMD2     =("    FIR        -   Nodes with Firewall");
        String CMD3     =("    FIA        -   Firewalls Attacked");
        String CMD4     =("    OUT        -   Nodes with outbreaks");
        String CMD5     =("    INA        -   Inactive Nodes");
        String end      =("    END        -   END");

        int n = CMD4.length();  // Largest command entry

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.println();

        System.out.print("-+ " + command);
        System.out.format("%14s", "-");
        System.out.print("+\n");

        System.out.print("-> " + CMD1);
        System.out.format("%8s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD2);
        System.out.format("%3s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD3);
        System.out.format("%4s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD4);
        System.out.format("%2s", "");
        System.out.print("<-\n");

        System.out.print("-> " + CMD5);
        System.out.format("%8s", "");
        System.out.print("<-\n");

        System.out.print("-> " + end);
        System.out.format("%19s", "");
        System.out.print("<-\n");

        for (int i = 0; i < n + 7; i++)
        {
            System.out.print("-");
        }
        System.out.println("\nPlease enter a command: ");
    }
}
