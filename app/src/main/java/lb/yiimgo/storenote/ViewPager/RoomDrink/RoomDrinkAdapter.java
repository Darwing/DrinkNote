package lb.yiimgo.storenote.ViewPager.RoomDrink;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.R;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class RoomDrinkAdapter extends RecyclerView.Adapter<RoomDrinkAdapter.RoomDrinkHolder>
{
    Button testBh;

    public boolean running;
    ArrayList<RoomDrinks> listRoomDrink;
    private Context mContext;
    private ListAdapterListener mListener;
    View layoutInflater;
    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public RoomDrinkAdapter(Context context,ArrayList<RoomDrinks> listRoomDrink, ListAdapterListener  mListener) {
        this.listRoomDrink = listRoomDrink;
        this.mContext = context;
        this.mListener = mListener;

    }

    @Override
    public RoomDrinkHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        return new RoomDrinkHolder(layoutInflater);
    }

    private int statusType(String s)
    {
        int result = 0;
        switch (s)
        {
            case "Free" :
                result = mContext.getResources().getColor(android.R.color.holo_green_light);
                break;
            case "Occupied" :
                result = mContext.getResources().getColor(android.R.color.holo_red_light);
                break;

        }

        return result;
    }
/*    private int getCurrentMiliSecondsOfChronometer(final RoomDrinkHolder holder) {
        int stoppedMilliseconds = 0;
        String chronoText = holder.tx_chronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds =
                    Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000
                            + Integer.parseInt(array[2]) * 1000;
        }
        return stoppedMilliseconds;
    }

    private void startChronometer(final RoomDrinkHolder holder) {
         int stoppedMilliseconds = getCurrentMiliSecondsOfChronometer(holder);
         holder.tx_chronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
         holder.tx_chronometer.start();
    }*/
    @Override
    public void onBindViewHolder(final RoomDrinkHolder holder, final int position)
    {

        holder.tx_id.setText(listRoomDrink.get(position).getIdRoom());
        holder.tx_waiter.setText(listRoomDrink.get(position).getWaiterRoom());
        holder.tx_drinkroom.setText(listRoomDrink.get(position).getRoomUbication());
        holder.tx_status.setText(listRoomDrink.get(position).getStatus());
        holder.tx_status.setBackgroundColor(statusType(listRoomDrink.get(position).getStatus()));
        layoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAddButton(view);
             }
        });
/*        holder.tx_testBh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer(holder);

                holder.tx_chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onChronometerTick(Chronometer cArg) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, getCurrentMiliSecondsOfChronometer(holder));
                        holder.tx_chronometer.setText(DateFormat.format("HH:mm:ss", calendar.getTime()));
                    }
                });
            }
        });*/
    }
    public void updateList(ArrayList<RoomDrinks> newList)
    {
        listRoomDrink = new ArrayList<>();
        listRoomDrink.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listRoomDrink.size();
    }


    public class RoomDrinkHolder extends RecyclerView.ViewHolder
    {
            TextView tx_id,tx_drinkroom,tx_status,tx_waiter;
            //Chronometer tx_chronometer;

            public RoomDrinkHolder(View itemView)
            {   super(itemView);

                tx_id = (TextView) itemView.findViewById(R.id.t_id);
                tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
                tx_drinkroom = (TextView) itemView.findViewById(R.id.t_drinkroom);
                tx_status = (TextView) itemView.findViewById(R.id.t_status);
                //tx_chronometer = layoutInflater.findViewById(R.id.chronometer);

            }
     }


}
