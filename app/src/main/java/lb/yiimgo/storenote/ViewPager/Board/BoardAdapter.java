package lb.yiimgo.storenote.ViewPager.Board;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder>
{

    public boolean running;
    ArrayList<Boards> listBoard;
    private Context mContext;
    private ListAdapterListener mListener;
    View layoutInflater;
    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public BoardAdapter(Context context, ArrayList<Boards> listBoard, ListAdapterListener  mListener) {
        this.listBoard = listBoard;
        this.mContext = context;
        this.mListener = mListener;

    }

    @Override
    public BoardHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_board_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        return new BoardHolder(layoutInflater);
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
    /*    private int getCurrentMiliSecondsOfChronometer(final BoardHolder holder) {
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

        private void startChronometer(final BoardHolder holder) {
             int stoppedMilliseconds = getCurrentMiliSecondsOfChronometer(holder);
             holder.tx_chronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
             holder.tx_chronometer.start();
        }*/
    @Override
    public void onBindViewHolder(final BoardHolder holder, final int position)
    {

        holder.tx_id.setText(listBoard.get(position).getIdServices());
        holder.tx_waiter.setText(listBoard.get(position).getFullName());
        holder.tx_ubication.setText(listBoard.get(position).getUbication());
        holder.tx_total_amount.setText(Utility.getFormatedAmount(listBoard.get(position).getTotalAmount()));

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
    public void updateList(ArrayList<Boards> newList)
    {
        listBoard = new ArrayList<>();
        listBoard.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listBoard.size();
    }


    public class BoardHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_ubication,tx_total_amount,tx_waiter;
        //Chronometer tx_chronometer;

        public BoardHolder(View itemView)
        {   super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
            tx_ubication = (TextView) itemView.findViewById(R.id.t_drinkroom);
            tx_total_amount = (TextView) itemView.findViewById(R.id.t_total_amount);
            //tx_chronometer = layoutInflater.findViewById(R.id.chronometer);

        }
    }


}
