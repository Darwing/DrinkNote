package lb.yiimgo.storenote.ViewPager.Service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lb.yiimgo.storenote.Entity.Services;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.AnimationUtil;
import lb.yiimgo.storenote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.CategoryHolder>
 {

    ArrayList<Services> listServices;
    private Context mContext;
    private OnItemClickListener mListener;
    View layoutInflater;
    private int previousPosition = 0;

    public interface OnItemClickListener {
        void onClickAddButton(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
    public ServiceAdapter(Context context, ArrayList<Services> listServices) {
       this.listServices = listServices;
       this.mContext = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_service_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);


        return new CategoryHolder(layoutInflater);
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
            default:
                result= R.drawable.unknown_image;
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

    @Override
    public void onBindViewHolder(CategoryHolder holder, final int position)
    {

        holder.tx_id.setText(listServices.get(position).getId());
        holder.tx_name.setText(listServices.get(position).getName());
        holder.tx_amount.setText(Utility.getFormatedAmount(listServices.get(position).getAmount()));
        holder.tx_category.setText(listServices.get(position).getService());
        holder.im_typeDrink.setImageResource(drinkType(listServices.get(position).getService()));
        holder.tx_status.setText(listServices.get(position).getStatus());
        holder.tx_status.setBackgroundColor(statusType(listServices.get(position).getStatus()));

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
    public void updateList(ArrayList<Services> newList)
    {
        listServices = new ArrayList<>();
        listServices.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listServices.size();
    }


    public class CategoryHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_name,tx_amount,tx_category,tx_status;
        ImageView im_typeDrink;
        ImageButton deleteCategory;

        public CategoryHolder(View itemView)
        {   super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_name = (TextView) itemView.findViewById(R.id.t_name);
            tx_amount = (TextView) itemView.findViewById(R.id.t_amount);
            tx_category = (TextView) itemView.findViewById(R.id.t_category);
            im_typeDrink= (ImageView)itemView.findViewById(R.id.typeDrink);
            tx_status = (TextView) itemView.findViewById(R.id.t_status);
//            deleteCategory = (ImageButton) itemView.findViewById(R.id.delete);

        }
    }
}
