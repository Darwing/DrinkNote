package lb.yiimgo.storenote.ViewPager.Board;


import android.app.Activity;
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

public class BoardDetailAdapter extends RecyclerView.Adapter<BoardDetailAdapter.BoardHolder>
{

    public boolean running;
    ArrayList<Boards> listBoard;
    private Context mContext;
    private ListAdapterListener mListener;
    View layoutInflater;


    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public BoardDetailAdapter(Context context, ArrayList<Boards> listBoard, ListAdapterListener  mListener) {
        this.listBoard = listBoard;
        this.mContext = context;
        this.mListener = mListener;

    }

    @Override
    public BoardHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_board_detail_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        return new BoardHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(final BoardHolder holder, final int position)
    {
        holder.tx_waiter.setText(listBoard.get(position).getFullName());
        holder.tx_ubication.setText(listBoard.get(position).getUbication());
        holder.tx_amount.setText(Utility.getFormatedAmount(listBoard.get(position).getAmount()));

        layoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAddButton(view);
            }
        });

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
        TextView tx_ubication,tx_amount,tx_waiter;

        public BoardHolder(View itemView)
        {   super(itemView);

            tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
            tx_ubication = (TextView) itemView.findViewById(R.id.t_drinkroom);
            tx_amount = (TextView) itemView.findViewById(R.id.t_amount);


        }
    }


}
