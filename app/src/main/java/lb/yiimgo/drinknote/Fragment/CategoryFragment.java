package lb.yiimgo.drinknote.Fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONObject;
import java.util.ArrayList;
import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.Utility.Utility;

import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.ViewPager.Category.CategoryAdapter;

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    View view;
    RecyclerView recyclerCategory;
    ArrayList<Category> listCategory;
    ProgressDialog progressDialog;
    ConecctionSQLiteHelper conn;
    SQLiteDatabase db;
    Category category;

    public CategoryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        view = inflater.inflate(R.layout.fragment_category, container, false);
        listCategory = new ArrayList<>();
        recyclerCategory = (RecyclerView) view.findViewById(R.id.idRecycler);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerCategory.setHasFixedSize(true);
        getData();

        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
    }

    public void getData()
    {
        category = null;
        conn = new ConecctionSQLiteHelper(this.getContext(), "db_drinknote",null,1);
        db = conn.getWritableDatabase();

        Cursor categoryData = conn.getCategoryInfo(db);

        while (categoryData.moveToNext()){
            category = new Category();
            category.setId(categoryData.getInt(categoryData.getColumnIndex(Utility.FIELD_ID)));
            category.setName(categoryData.getString(categoryData.getColumnIndex(Utility.FIELD_NAME)));
            category.setAmount(categoryData.getInt(categoryData.getColumnIndex(Utility.FIELD_AMOUNT)));
            category.setCategory(categoryData.getString(categoryData.getColumnIndex(Utility.FIELD_CATEGORY)));

            listCategory.add(category);
        }

        CategoryAdapter adapter = new CategoryAdapter(listCategory);
        recyclerCategory.setAdapter(adapter);
        progressDialog.hide();
        conn.close();

    }
    @Override
    public void onResponse(JSONObject response) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
