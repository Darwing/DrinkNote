package lb.yiimgo.drinknote;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.Utility.Utility;


/**
 * Created by Darwing on 16-Dec-18.
 */

public class BackGroundTask extends AsyncTask<String,Category,String> {
    Context ctx;
    CategoryAdapter categoryAdapter;
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
        categoryAdapter = new CategoryAdapter(ctx,R.layout.fragment_category);
        listView = (ListView) activity.findViewById(R.id.display_category);

        if(method.equals("get_info"))
        {
            Cursor cursor = conn.getCategoryInfo(db);
            String name,category;
            int id,amount;
            while (cursor.moveToNext())
            {
                id = cursor.getInt(cursor.getColumnIndex(Utility.FIELD_ID));
                name = cursor.getString(cursor.getColumnIndex(Utility.FIELD_NAME));
                amount = cursor.getInt(cursor.getColumnIndex(Utility.FIELD_AMOUNT));
                category = cursor.getString(cursor.getColumnIndex(Utility.FIELD_CATEGORY));

                Category catEntity = new Category(id,name,amount,category);
                publishProgress(catEntity);
            }

            return "get_info";
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Category... values)
    {
        categoryAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result.equals("get_info"))
        {
            listView.setAdapter(categoryAdapter);
        }
        else
            {
                Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
            }
    }

}
