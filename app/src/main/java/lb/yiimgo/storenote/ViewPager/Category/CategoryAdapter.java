package lb.yiimgo.storenote.ViewPager.Category;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import lb.yiimgo.storenote.Entity.Category;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>
 {

    ArrayList<Category> listCategory;
    private Context mContext;
    private ListAdapterListener mListener; 
    View layoutInflater;
    public interface ListAdapterListener {
        void onClickAddButton(View v);
    }
    public CategoryAdapter(Context context,ArrayList<Category> listCategory, ListAdapterListener  mListener) {
       this.listCategory = listCategory;
       this.mContext = context;
       this.mListener = mListener;

    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_row,parent,false);

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

        holder.tx_id.setText(listCategory.get(position).getId());
        holder.tx_name.setText(listCategory.get(position).getName());
        holder.tx_amount.setText(Utility.getFormatedAmount(listCategory.get(position).getAmount()));
        holder.tx_category.setText(listCategory.get(position).getCategory());
        holder.im_typeDrink.setImageResource(drinkType(listCategory.get(position).getCategory()));
        holder.tx_status.setText(listCategory.get(position).getStatus());
        holder.tx_status.setBackgroundColor(statusType(listCategory.get(position).getStatus()));

        layoutInflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAddButton(view);
            }
        });

      /*  holder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickDeleteButton(position);
            }
        });*/

    }
    public void updateList(ArrayList<Category> newList)
    {
        listCategory = new ArrayList<>();
        listCategory.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listCategory.size();
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
