package lb.yiimgo.storenote.ViewPager.RoomDrink;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.R;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class RoomDrinkAdapter extends RecyclerView.Adapter<RoomDrinkAdapter.RoomHolder> {

    ImageButton button;
    LinearLayout buttonAddDrink,layoutRoom;

    List<RoomDrinks> listDrinkRoom;

    public RoomDrinkAdapter(List<RoomDrinks> listDrinkRoom) {
        this.listDrinkRoom = listDrinkRoom;
    }


  /*  public void add(RoomDrinks object)
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
            roomHolder.tx_status = (TextView) row.findViewById(R.id.t_status);
            row.setTag(roomHolder);
        }else
        {
            roomHolder = (RoomDrinkHolder) row.getTag();
        }
        layoutRoom = (LinearLayout) row.findViewById(R.id.layout_room_row);
        RoomDrinks roomDrinks = (RoomDrinks) getItem(position);

        if(roomDrinks.getStatus() == 0)
        {
            layoutRoom.setBackgroundResource(R.color.bgRowsGreen);
            roomHolder.tx_status.setText("Disponible");
        }else{
            layoutRoom.setBackgroundResource(R.color.bgRowsRed);
            roomHolder.tx_status.setText("No Disponible");
        }
        roomHolder.tx_id.setText(roomDrinks.getIdRoom().toString());
        roomHolder.tx_name.setText(roomDrinks.getNameRoom().toString());
        roomHolder.tx_ubication.setText(roomDrinks.getRoomUbication().toString());

        deleteDrinkRow(position,row,roomDrinks,"delete");
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
    }*/


    @Override
    public RoomDrinkAdapter.RoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_row,parent,false);
        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        return new RoomDrinkAdapter.RoomHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        holder.tx_id.setText(listDrinkRoom.get(position).getIdRoom().toString());
        holder.tx_name.setText(listDrinkRoom.get(position).getNameRoom().toString());
        holder.tx_ubication.setText(listDrinkRoom.get(position).getRoomUbication().toString());
        holder.tx_status.setText(listDrinkRoom.get(position).getStatus().toString());
        //holder.im_typeDrink.setImageResource( DrinkType(listCategory.get(position).getCategory().toString()));
}


    @Override
    public int getItemCount() {
        return listDrinkRoom.size();
    }

    public class RoomHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_name,tx_ubication,tx_status;

        public RoomHolder(View itemView) {
            super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_name = (TextView) itemView.findViewById(R.id.t_name);
            tx_ubication = (TextView) itemView.findViewById(R.id.t_ubication);
            tx_status = (TextView) itemView.findViewById(R.id.t_status);
        }
    }
}
