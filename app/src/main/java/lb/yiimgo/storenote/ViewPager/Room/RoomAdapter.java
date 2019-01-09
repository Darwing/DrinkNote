package lb.yiimgo.storenote.ViewPager.Room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.Rooms;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.AnimationUtil;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomDrinkHolder>
{

    ArrayList<Rooms> listRoomDrink;
    private Context mContext;
    private OnItemClickListener mListener;
    View layoutInflater;
    private int previousPosition = 0;

    public interface OnItemClickListener {
        void onClickAddButton(View v);
    }

    public void  setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    public RoomAdapter(Context context, ArrayList<Rooms> listRoomDrink) {
        this.listRoomDrink = listRoomDrink;
        this.mContext = context;
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
        if(position > previousPosition)
            AnimationUtil.animate(holder, true);
        else
            AnimationUtil.animate(holder, false);

        previousPosition = position;
    }
    public void updateList(ArrayList<Rooms> newList)
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

            public RoomDrinkHolder(View itemView)
            {   super(itemView);

                tx_id = (TextView) itemView.findViewById(R.id.t_id);
                tx_waiter = (TextView) itemView.findViewById(R.id.t_waiter);
                tx_drinkroom = (TextView) itemView.findViewById(R.id.t_drinkroom);
                tx_status = (TextView) itemView.findViewById(R.id.t_status);

            }
     }


}
