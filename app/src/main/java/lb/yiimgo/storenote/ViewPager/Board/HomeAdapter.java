package lb.yiimgo.storenote.ViewPager.Board;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.Homes;
import lb.yiimgo.storenote.R;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder>
{
    Button testBh;

    public boolean running;
    ArrayList<Homes> listHome;
    private Context mContext;
    private ListAdapterListener mListener;
    View layoutInflater;
    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public HomeAdapter(Context context,ArrayList<Homes> listHome, ListAdapterListener  mListener) {
        this.listHome = listHome;
        this.mContext = context;
        this.mListener = mListener;

    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        return new HomeHolder(layoutInflater);
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
    /*    private int getCurrentMiliSecondsOfChronometer(final HomeHolder holder) {
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

        private void startChronometer(final HomeHolder holder) {
             int stoppedMilliseconds = getCurrentMiliSecondsOfChronometer(holder);
             holder.tx_chronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
             holder.tx_chronometer.start();
        }*/
    @Override
    public void onBindViewHolder(final HomeHolder holder, final int position)
    {

        holder.tx_id.setText(listHome.get(position).getIdServices());
        holder.tx_waiter.setText(listHome.get(position).getAmount());
        holder.tx_drinkroom.setText(listHome.get(position).getCategoryId());
        holder.tx_status.setText(listHome.get(position).getUbication());
        holder.tx_status.setBackgroundColor(statusType(listHome.get(position).getUserCreate()));
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
    public void updateList(ArrayList<Homes> newList)
    {
        listHome = new ArrayList<>();
        listHome.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listHome.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_drinkroom,tx_status,tx_waiter;
        //Chronometer tx_chronometer;

        public HomeHolder(View itemView)
        {   super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
            tx_drinkroom = (TextView) itemView.findViewById(R.id.t_drinkroom);
            tx_status = (TextView) itemView.findViewById(R.id.t_status);
            //tx_chronometer = layoutInflater.findViewById(R.id.chronometer);

        }
    }


}
