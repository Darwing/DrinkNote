package lb.yiimgo.storenote.ViewPager.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lb.yiimgo.storenote.Entity.Users;
import lb.yiimgo.storenote.R;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class UserAdapter extends RecyclerView.Adapter<lb.yiimgo.storenote.ViewPager.User.UserAdapter.UserHolder>
{

    List<Users> listUser;
    private Context mContext;
    private lb.yiimgo.storenote.ViewPager.User.UserAdapter.ListAdapterListener mListener;

    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public UserAdapter(Context context,List<Users> listUser, lb.yiimgo.storenote.ViewPager.User.UserAdapter.ListAdapterListener mListener) {
        this.listUser = listUser;
        this.mContext = context;
        this.mListener = mListener;

    }

    @Override
    public lb.yiimgo.storenote.ViewPager.User.UserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);
        layoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAddButton(view);
            }
        });

        return new lb.yiimgo.storenote.ViewPager.User.UserAdapter.UserHolder(layoutInflater);
    }
    private int drinkType(String cat)
    {
        int result = 0;
        switch (cat)
        {
            case "Cerveza" :
                result = R.drawable.bg_presidente_light;
                break;
            case "Wisky" :
                result = R.drawable.bg_wisky;
                break;
            case "Ron" :
                result = R.drawable.bg_romo;
                break;
        }

        return result;
    }

    private int statusType(String s)
    {
        int result = 0;
        switch (s)
        {
            case "Available" :
                result = mContext.getResources().getColor(android.R.color.holo_green_light);
                break;
            case "Not available" :
                result = mContext.getResources().getColor(android.R.color.holo_red_light);
                break;

        }

        return result;
    }
    private String getFormatedAmount(Double amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    @Override
    public void onBindViewHolder(lb.yiimgo.storenote.ViewPager.User.UserAdapter.UserHolder holder, final int position)
    {

       /*  holder.tx_id.setText(listUser.get(position).getId().toString());
        holder.tx_name.setText(listUser.get(position).getFullName().toString());
        holder.tx_user.setText(listUser.get(position).getUserName().toString());
        holder.tx_status.setText(listUser.get(position).getId());
       holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickDeleteButton(position);
            }
        });*/

    }
    public void updateList(List<Users> newList)
    {
        listUser = new ArrayList<>();
        listUser.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listUser.size();
    }


    public class UserHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_name,tx_amount,tx_user,tx_status;
        ImageView im_typeDrink;
        ImageButton deleteUser;

        public UserHolder(View itemView)
        {   super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_name = (TextView) itemView.findViewById(R.id.t_name);
            tx_amount = (TextView) itemView.findViewById(R.id.t_amount);
            tx_user = (TextView) itemView.findViewById(R.id.t_user);
            im_typeDrink= (ImageView)itemView.findViewById(R.id.typeDrink);
            tx_status = (TextView) itemView.findViewById(R.id.t_status);
//            deleteUser = (ImageButton) itemView.findViewById(R.id.delete);

        }
    }
}
