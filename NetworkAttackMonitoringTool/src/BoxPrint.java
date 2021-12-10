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

        /* Print dashes(-) for the top of the box */
        for (int i = 0; i < welcome.length() + 7; i++) { System.out.print("-"); }
        System.out.println();

        System.out.print("! " + welcome + "    !\n");

        /* Print dashes(-) for the bottom of the box */
        for (int i = 0; i < welcome.length() + 7; i++) { System.out.print("-"); }
        System.out.print("\n");
    }
    public static void commandList()
    {
        String command = ("Command Entry  -   Full Name");
        String CMD1     =("    STAT       -   Statistics");
        String CMD2     =("    VIRUS      -   Viruses");
        String CMD3     =("    SAFE       -   Safe Routes");
        String end      =("    END        -   END");

        /* Print dashes(-) for the top of the box */
        for (int i = 0; i < CMD3.length() + 7; i++) { System.out.print("-"); }
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

        /* Print dashes(-) for the bottom of the box */
        for (int i = 0; i < CMD3.length() + 7; i++) { System.out.print("-"); }
        System.out.println("\nPlease enter a command: ");
    }


    public static void stat()
    {
        String command = ("Command Entry  -   Full Name");
        String CMD1     =("    INF        -   Nodes Infected");
        String CMD2     =("    FIR        -   Nodes with Firewall");
        String CMD3     =("    FIA        -   Firewalls Attacked");
        String CMD4     =("    OUT        -   Nodes with outbreaks");
        String CMD5     =("    INA        -   Inactive Nodes");
        String end      =("    END        -   END");

        /* Print dashes(-) for the top of the box */
        for (int i = 0; i < CMD4.length() + 7; i++) { System.out.print("-"); }
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

        /* Print dashes(-) for the bottom of the box */
        for (int i = 0; i < CMD4.length() + 7; i++) { System.out.print("-"); }
        System.out.println("\nPlease enter a command: ");
    }
}
