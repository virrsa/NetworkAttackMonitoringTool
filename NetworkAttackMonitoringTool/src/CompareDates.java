import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class CompareDates implements Comparator<String> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public int compare(String date1, String date2)
    {
        try
        {
            // 0 means the dates are equal. -1 means date2 is after date 1. 1 means date 1 is after date2
            return dateFormat.parse(date1).compareTo(dateFormat.parse(date2));
        } catch (ParseException e)
        {
            System.out.print("Bad format Unable to Compare");
            e.printStackTrace();
        }
            return 0;   // Fail-safe, if you could not compare
        }
}

