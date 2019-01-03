package lb.yiimgo.storenote.ViewPager.Board;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder>
{


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

    @Override
    public void onBindViewHolder(final BoardHolder holder, final int position)
    {
        holder.tx_waiter.setText(listBoard.get(position).getFullName());
        holder.tx_ubication.setText(listBoard.get(position).getUbication());
        holder.tx_total_amount.setText(Utility.getFormatedAmount(listBoard.get(position).getTotalAmount()));

        layoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAddButton(view);
            }
        });

        currentTime(holder,position);
    }

    public void currentTime(final BoardHolder holder,final int position)
    {

            final String strDate = listBoard.get(position).getDateCreate();
            final String tStart = strDate.substring(strDate.indexOf(' ') +1);

            Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
                                String dateString = sdf.format(date);
                                String tFinish = dateString;

                                String[] sTimeHourMinSec = tStart.split(":");
                                int sHour = Integer.valueOf(sTimeHourMinSec[0]);
                                int sMin = Integer.valueOf(sTimeHourMinSec[1]);
                                int sSec = Integer.valueOf(sTimeHourMinSec[2]);

                                String[] fTimeHourMinSec = tFinish.split(":");
                                int fHour = Integer.valueOf(fTimeHourMinSec[0]);
                                int fMin = Integer.valueOf(fTimeHourMinSec[1]);
                                int fSec = Integer.valueOf(fTimeHourMinSec[2]);

                                int diffTotSec = (fHour - sHour) * 3600 + (fMin - sMin) * 60 + (fSec - sSec);
                                final int diffHours = diffTotSec / 3600;
                                final int diffMins = (diffTotSec % 3600) / 60;
                                final int diffSecs = (diffTotSec % 3600) % 60;
                                holder.tx_timer.setText(diffHours+" h : "+diffMins+" m "+diffSecs +" s");
                           }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
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
        TextView tx_id,tx_ubication,tx_total_amount,tx_waiter,tx_timer;

        public BoardHolder(View itemView)
        {   super(itemView);

            tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
            tx_ubication = (TextView) itemView.findViewById(R.id.t_drinkroom);
            tx_total_amount = (TextView) itemView.findViewById(R.id.t_total_amount);
            tx_timer = layoutInflater.findViewById(R.id.t_timer);

        }
    }


}
