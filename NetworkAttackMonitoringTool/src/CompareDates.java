/**
 *  Sarah Virr
 *  101146506
 *  Jawad Kadri
 *  101147056
 *  Last modified: November 21st, 2021
 *
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class CompareDates implements Comparator<String> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int compare(String date1, String date2)
    {
        try
        {
            // 0 means the dates are equal. -1 means date2 is after date 1. 1 means date 1 is after date2
            return dateFormat.parse(date1).compareTo(dateFormat.parse(date2));
        } catch (ParseException e)  // A format error can occur when the dates aren't following the SimpleDateFormat pattern
        {
            System.out.println("Bad format Unable to Compare:" +date1 +" with" +date2);
            e.printStackTrace();
            return 2;   // If the comparison failed we still need to return a value
        }
    }
}

