/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bbgbws;
import java.util.Calendar;
/**
 *
 * @author vic
 */
public class BbWsUtil {
    public static String extractDate(Calendar cal)
    {
        if (cal == null) return "";
        return cal.get(Calendar.YEAR)
                +"-"+(cal.get(Calendar.MONTH)+1)
                +"-"+cal.get(Calendar.DAY_OF_MONTH)+" "
                +cal.get(Calendar.HOUR_OF_DAY)+":"
                +cal.get(Calendar.MINUTE)+":"
                +cal.get(Calendar.SECOND);
    }

}
