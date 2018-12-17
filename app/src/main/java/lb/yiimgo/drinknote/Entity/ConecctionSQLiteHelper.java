package lb.yiimgo.drinknote.Entity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import lb.yiimgo.drinknote.Utility.Utility;

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
    }
    public Cursor getCategoryInfo(SQLiteDatabase db)
    {
        String[] cat = {Utility.FIELD_ID,Utility.FIELD_NAME,Utility.FIELD_AMOUNT,
        Utility.FIELD_CATEGORY};

        Cursor cursor = db.query(Utility.TABLE_CATEGORY,cat,null,null,
                null,null,null);

        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }
}
