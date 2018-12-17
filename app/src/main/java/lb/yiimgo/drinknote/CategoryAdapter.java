package lb.yiimgo.drinknote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.Utility.Utility;

/**
 * Created by Darwing on 16-Dec-18.
 */

public class CategoryAdapter extends ArrayAdapter {
    List list = new ArrayList();
    ImageButton button;
    public CategoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(Category object)
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
        categoryHolder.tx_id.setText(category.getId().toString());
        categoryHolder.tx_name.setText(category.getName().toString());
        categoryHolder.tx_amount.setText(category.getAmount().toString());
        categoryHolder.tx_category.setText(category.getCategory().toString());
        deleteCategory(position,row,category);

        return row;
    }

    private void deleteCategory(final int position, View row,final Category c)
    {
        ConecctionSQLiteHelper conn = new ConecctionSQLiteHelper(getContext(), "db_drinknote",null,1);
        final SQLiteDatabase db = conn.getWritableDatabase();
        button = (ImageButton) row.findViewById(R.id.delete);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String[] param = {c.getId().toString()};
                db.delete(Utility.TABLE_CATEGORY,Utility.FIELD_ID +"=?",param);
                list.remove(position);
                notifyDataSetChanged();
                db.close();
               // Toast.makeText(getContext(),c.getId().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
    static class CategoryHolder
    {
        TextView tx_id,tx_name,tx_amount,tx_category;

    }
}
