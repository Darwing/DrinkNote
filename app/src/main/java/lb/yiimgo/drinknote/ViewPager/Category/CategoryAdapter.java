package lb.yiimgo.drinknote.ViewPager.Category;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>
    implements View.OnClickListener{

    List<Category> listCategory;
    private View.OnClickListener listener;
    private Context mContext;
    private ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void onClickAtOKButton(int p); // create callback function
    }
    public CategoryAdapter(Context context,List<Category> listCategory, ListAdapterListener  mListener) {
       this.listCategory = listCategory;
       this.mContext = context;
       this.mListener = mListener;

    }


  /*   public void add(Category object)
    {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount()
    {
       return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CategoryHolder categoryHolder;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.fragment_category_row,parent,false);
            categoryHolder = new CategoryHolder();

            categoryHolder.im_typeDrink = (ImageView) row.findViewById(R.id.typeDrink);
            categoryHolder.tx_id = (TextView) row.findViewById(R.id.t_id);
            categoryHolder.tx_name = (TextView) row.findViewById(R.id.t_name);
            categoryHolder.tx_amount = (TextView) row.findViewById(R.id.t_amount);
            categoryHolder.tx_category = (TextView) row.findViewById(R.id.t_category);

            row.setTag(categoryHolder);
        }else
        {
            categoryHolder = (CategoryHolder) row.getTag();
        }

        Category category = (Category) getItem(position);
        categoryHolder.im_typeDrink.setImageResource( DrinkType(category.getCategory().toString()));
        categoryHolder.tx_id.setText(category.getId().toString());
        categoryHolder.tx_name.setText(category.getName().toString());
        categoryHolder.tx_amount.setText(category.getAmount().toString());
        categoryHolder.tx_category.setText(category.getCategory().toString());
        deleteCategory(position,row,category,"delete");
        addDinrk(position,row,category,"add");
        return row;
    }
    private int DrinkType(String cat)
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
            case "Romo" :
                result = R.drawable.bg_romo;
                break;
        }

        return result;
    }
    private void deleteCategory(final int position, View row,final Category c,final String method)
    {

        button = (ImageButton) row.findViewById(R.id.delete);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AlertDialog(position,c.getId().toString(),method);
            }
        });
    }

    private void addDinrk(final int position, View row,final Category c,final String method)
    {
        buttonAddDrink = (LinearLayout) row.findViewById(R.id.layout_row);

        buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AlertDialog(position,c.getId().toString(),method);
            }
        });
    }
    public void AlertDialog(final int position,final ViewGroup parent, final String method){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(parent.getContext());
        builder1.setMessage("Are your sure "+method+" this item?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (method)
                        {
                            case  "delete" :

                                listCategory.remove(position);
                                notifyDataSetChanged();

                            break;
                            case  "add" :
                                Toast.makeText(parent.getContext(),"Se agrego " + String.valueOf(position),Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
*/

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_row,parent,false);

        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutInflater.setLayoutParams(layoutParams);

        layoutInflater.setOnClickListener(this);
        return new CategoryHolder(layoutInflater);
    }
    private int DrinkType(String cat)
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
    private String getFormatedAmount(Double amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, final int position)
    {

        holder.tx_id.setText(listCategory.get(position).getId().toString());
        holder.tx_name.setText(listCategory.get(position).getName().toString());
        holder.tx_amount.setText(getFormatedAmount(listCategory.get(position).getAmount()));
        holder.tx_category.setText(listCategory.get(position).getCategory().toString());
        holder.im_typeDrink.setImageResource( DrinkType(listCategory.get(position).getCategory().toString()));
        holder.deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use callback function in the place you want
                mListener.onClickAtOKButton(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null)
        {
            listener.onClick(view);

        }
    }

    public class CategoryHolder extends RecyclerView.ViewHolder
    {
        TextView tx_id,tx_name,tx_amount,tx_category;
        ImageView im_typeDrink;
        ImageButton deleteCategory;

        public CategoryHolder(View itemView)
        {   super(itemView);

            tx_id = (TextView) itemView.findViewById(R.id.t_id);
            tx_name = (TextView) itemView.findViewById(R.id.t_name);
            tx_amount = (TextView) itemView.findViewById(R.id.t_amount);
            tx_category = (TextView) itemView.findViewById(R.id.t_category);
            im_typeDrink= (ImageView)itemView.findViewById(R.id.typeDrink);
            deleteCategory = (ImageButton) itemView.findViewById(R.id.delete);

        }
    }
}
