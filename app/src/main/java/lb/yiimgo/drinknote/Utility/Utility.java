package lb.yiimgo.drinknote.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class Utility
{
    //const field table category
    public static final  String TABLE_CATEGORY = "Category";
    public static final  String FIELD_ID = "Id";
    public static final  String FIELD_NAME = "Name";
    public static final  String FIELD_AMOUNT = "Amount";
    public static final  String FIELD_CATEGORY = "Category";

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+TABLE_CATEGORY+" ("+FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME+" TEXT, "+FIELD_AMOUNT+" INTEGER, "+FIELD_CATEGORY+" TEXT)";


}
