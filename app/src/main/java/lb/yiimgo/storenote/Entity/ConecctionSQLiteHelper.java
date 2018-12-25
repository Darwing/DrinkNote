package lb.yiimgo.storenote.Entity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lb.yiimgo.storenote.Utility.Utility;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class ConecctionSQLiteHelper extends SQLiteOpenHelper
{


    public ConecctionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utility.CREATE_TABLE_CATEGORY);
        db.execSQL(Utility.CREATE_TABLE_ROOM_DRINK);
    }
    public Cursor getCategoryInfo(SQLiteDatabase db)
    {
        String[] cat = {Utility.FIELD_ID,Utility.FIELD_NAME,Utility.FIELD_AMOUNT,
        Utility.FIELD_CATEGORY};

        Cursor cursor = db.query(Utility.TABLE_CATEGORY,cat,null,null,
                null,null,null);

        return cursor;
    }
    public boolean getRoomById(SQLiteDatabase db,String id)
    {
        String[] room = {Utility.FIELD_ROOM_DRINK_UBICATION };

        Cursor cursorRoom = db.query(Utility.TABLE_ROOM_DRINK,room,"RoomUbication = ?",
                new String[]{ id },null,
                null,null,null);
        if(cursorRoom.getCount() == 0)
            return true;
        else
            return false;
    }
    public Cursor getRoomInfo(SQLiteDatabase db)
    {
        String[] room = {Utility.FIELD_ID_ROOM,Utility.FIELD_NAME_ROOM,Utility.FIELD_ROOM_DRINK_UBICATION,Utility.FIELD_ROOM_DRINK_STATUS };

        Cursor cursorRoom = db.query(Utility.TABLE_ROOM_DRINK,room,null,null,
                null,null,room[2]+" ASC");

        return cursorRoom;
    }
/*    public void saveRoomDrink(SQLiteDatabase db)
    {
        String[] room = {Utility.FIELD_ID,Utility.FIELD_NAME,Utility.FIELD_AMOUNT,
                Utility.FIELD_CATEGORY,Utility.FIELD_DATE_TIME};

         db.insert(Utility.TABLE_ROOM_DRINK,Utility.FIELD_ID,room);
    }*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS RoomDrink");
        onCreate(db);
    }
}
