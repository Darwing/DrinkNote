package lb.yiimgo.drinknote.Fragment;


import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.ViewPager.Category.CategoryAdapter;

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    View view;
    RecyclerView recyclerCategory;
    ArrayList<Category> listCategory;
    ProgressDialog progressDialog;
    CategoryAdapter adapter;
    Category category = null;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public CategoryFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_category, container, false);
        listCategory = new ArrayList<>();
        recyclerCategory = (RecyclerView) view.findViewById(R.id.idRecycler);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerCategory.setHasFixedSize(true);

        requestQueue = Volley.newRequestQueue(getContext());
        loadWebServices();
        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error " + error.toString(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }
    @Override
    public void onResponse(JSONObject response) {


        JSONArray json = response.optJSONArray("category");
        try{
            for(int i =0; i<json.length(); i++)
            {
                category = new Category();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                category.setId(jsonObject.optInt("Id"));
                category.setName(jsonObject.optString("Name"));
                category.setAmount(jsonObject.optDouble("Amount"));
                category.setCategory(jsonObject.optString("Category"));
                category.setStatus(jsonObject.optString("Status"));

                listCategory.add(category);

            }
            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            progressDialog.hide();
            adapter = new CategoryAdapter(listCategory);

            recyclerCategory.setAdapter(adapter);
            addServicesRoom(adapter,recyclerCategory,listCategory);
    }
    public void loadWebServices()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = "http://rizikyasociados.com.do/wsDrinkNote/Main/getDataServices";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    public void addServicesRoom(final CategoryAdapter adapters,final RecyclerView recy,final ArrayList<Category> c)
    {

        adapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Seleccion "+
                c.get(recy.getChildAdapterPosition(view))
                        .getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteServices(final CategoryAdapter adapters,final RecyclerView recy,final ArrayList<Category> c)
    {

        adapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Seleccion "+
                        c.get(recy.getChildAdapterPosition(view))
                                .getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
