package lb.yiimgo.storenote.ViewPager.Board;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


    ArrayList<Boards> listBoard;
    private Context mContext;
    View layoutInflater;
    public Animation animation;
    private int previousPosition = 0;
    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClickAddButton(View v);
        void onClickPayButton(int p);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    public BoardAdapter(Context context, ArrayList<Boards> listBoard) {
        this.listBoard = listBoard;
        this.mContext = context;
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

        holder.tx_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickPayButton(position);
            }
        });

        holder.tx_timer.setText(listBoard.get(position).getTotalHours());
        layoutInflater.setTag(holder);

        if(position > previousPosition){
            animation = AnimationUtils.loadAnimation(mContext,R.anim.item_animation_fall_down);
            layoutInflater.startAnimation(animation);
        }else{
            animation = AnimationUtils.loadAnimation(mContext,R.anim.item_animation_fall_down);
            layoutInflater.startAnimation(animation);
        }
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
        TextView tx_ubication,tx_total_amount,tx_waiter,tx_timer;
        CardView tx_pay;

        public BoardHolder(View itemView)
        {   super(itemView);

            tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
            tx_ubication = (TextView) itemView.findViewById(R.id.t_drinkroom);
            tx_total_amount = (TextView) itemView.findViewById(R.id.t_total_amount);
            tx_timer = layoutInflater.findViewById(R.id.t_timer);
            tx_pay = (CardView) itemView.findViewById(R.id.pay_now);
        }
    }


}
