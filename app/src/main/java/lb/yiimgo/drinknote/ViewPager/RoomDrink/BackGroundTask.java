package lb.yiimgo.drinknote.ViewPager.RoomDrink;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import lb.yiimgo.drinknote.Entity.RoomDrinks;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.Utility.Utility;


/**
 * Created by Darwing on 16-Dec-18.
 */

public class BackGroundTask extends AsyncTask<String,RoomDrinks,String> {
    Context ctx;
    RoomDrinkAdapter roomAdapter;
    Activity activity;
    ListView listView;


    public BackGroundTask(Context ctx){
        this.ctx = ctx;
        activity = (Activity) ctx;
    }
    @Override
    protected void onPreExecute(){super.onPreExecute();}
    @Override
    protected String doInBackground(String... params) {
        String method = params[0];

        ConecctionSQLiteHelper conn = new ConecctionSQLiteHelper(ctx, "db_drinknote",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        roomAdapter = new RoomDrinkAdapter(ctx, R.layout.fragment_room_drink);
        listView = (ListView) activity.findViewById(R.id.display_room);
        String name,ubication;
        int id,status;

        if(method.equals("get_info_room"))
        {
            Cursor cursor = conn.getRoomInfo(db);

            while (cursor.moveToNext())
            {
                id = cursor.getInt(cursor.getColumnIndex(Utility.FIELD_ID_ROOM));
                name = cursor.getString(cursor.getColumnIndex(Utility.FIELD_NAME_ROOM));
                ubication = cursor.getString(cursor.getColumnIndex(Utility.FIELD_ROOM_DRINK_UBICATION));
                status = cursor.getInt(cursor.getColumnIndex(Utility.FIELD_ROOM_DRINK_STATUS));

                RoomDrinks roomEntity = new RoomDrinks(id,name,ubication,status);

                publishProgress(roomEntity);
            }

            return "get_info_room";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(RoomDrinks... values)
    {
        roomAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result.equals("get_info_room"))
        {
            listView.setAdapter(roomAdapter);
        }
        else
        {
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }
    }

}
