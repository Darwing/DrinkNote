package lb.yiimgo.storenote.Utility;

import android.app.AlertDialog;
import android.content.Context;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class Utility
{
    Context _context;
    public Utility(Context context)
    {
        this._context = context;
    }
    //const field to create services
    public static final  String TABLE_CATEGORY = "Services";

    public static final  String FIELD_ID = "Id";
    public static final  String FIELD_NAME = "Name";
    public static final  String FIELD_AMOUNT = "Amount";
    public static final  String FIELD_CATEGORY = "Services";


    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+TABLE_CATEGORY+" ("+FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME+" TEXT, "+FIELD_AMOUNT+" INTEGER, "+FIELD_CATEGORY+" TEXT)";

    //const to create room drink
    public static final  String TABLE_ROOM_DRINK = "RoomBase";

    public static final  String FIELD_ID_ROOM = "IdRoom";
    public static final  String FIELD_NAME_ROOM = "NameRoom";
    public static final  String FIELD_ROOM_DRINK_UBICATION = "RoomUbication";
    public static final  String FIELD_ROOM_DRINK_STATUS = "Status";

    public static final String CREATE_TABLE_ROOM_DRINK = "CREATE TABLE "+TABLE_ROOM_DRINK+"" +
            " ("+FIELD_ID_ROOM+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME_ROOM+" TEXT, "+FIELD_CATEGORY+" TEXT," +
            " "+FIELD_ROOM_DRINK_UBICATION+" TEXT,"+FIELD_ROOM_DRINK_STATUS+" INTEGER DEFAULT 0)";

    public static  String BASE_URL  ="http://rizikyasociados.com.do/wsDrinkNote/";

    public void showDialogAnimation(int type,String message,String title)
    {
        AlertDialog dialog = new AlertDialog.Builder(_context).create();
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.getWindow().getAttributes().windowAnimations = type;
        dialog.show();
    }

    public static String getFormatedAmount(Double amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public static String dateFormat(String date) throws ParseException
    {
        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        String input = date;
        Date d = sdfIn.parse(input);

        return  sdfOut.format(d);
    }
}
