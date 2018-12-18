package lb.yiimgo.drinknote.ViewPager.RoomDrink;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.Entity.RoomDrinks;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class RoomDrinkAdapter extends ArrayAdapter {

    List list = new ArrayList();
    ImageButton button;
    LinearLayout buttonAddDrink;

    public RoomDrinkAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(RoomDrinks object)
    {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RoomDrinkHolder roomHolder;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.fragment_room_row,parent,false);
            roomHolder = new RoomDrinkHolder();

            roomHolder.tx_id = (TextView) row.findViewById(R.id.t_id);
            roomHolder.tx_name = (TextView) row.findViewById(R.id.t_name);
            roomHolder.tx_ubication = (TextView) row.findViewById(R.id.t_ubication);

            row.setTag(roomHolder);
        }else
        {
            roomHolder = (RoomDrinkHolder) row.getTag();
        }

        RoomDrinks category = (RoomDrinks) getItem(position);

        roomHolder.tx_id.setText(category.getIdRoom().toString());
        roomHolder.tx_name.setText(category.getNameRoom().toString());
        roomHolder.tx_ubication.setText(category.getRoomUbication().toString());

        deleteDrinkRow(position,row,category,"delete");
       // addDinrk(position,row,category,"add");
        return row;
    }

    private void deleteDrinkRow(final int position, View row,final RoomDrinks c,final String method)
    {

        button = (ImageButton) row.findViewById(R.id.delete);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AlertDialog(position,c.getIdRoom().toString(),method);
            }
        });
    }

    private void addDinrk(final int position, View row,final RoomDrinks c,final String method)
    {
        buttonAddDrink = (LinearLayout) row.findViewById(R.id.layout_row);

        buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AlertDialog(position,c.getIdRoom().toString(),method);
            }
        });
    }
    public void AlertDialog(final int position,final String field_id,final String method){
        ConecctionSQLiteHelper conn = new ConecctionSQLiteHelper(getContext(), "db_drinknote",null,1);
        final SQLiteDatabase db = conn.getWritableDatabase();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Are your sure "+method+" this item?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (method)
                        {
                            case  "delete" :
                                String[] param = {field_id};
                                db.delete(Utility.TABLE_ROOM_DRINK,Utility.FIELD_ID_ROOM +"=?",param);
                                list.remove(position);
                                notifyDataSetChanged();
                                db.close();
                                break;
                            case  "add" :
                                Toast.makeText(getContext(),"Se agrego " + String.valueOf(position),Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    static class RoomDrinkHolder
    {
        TextView tx_id,tx_name,tx_ubication;
        int tx_status;
    }
}
