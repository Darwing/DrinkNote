package lb.yiimgo.drinknote.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import lb.yiimgo.drinknote.Entity.Category;
import lb.yiimgo.drinknote.Entity.VolleySingleton;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.ViewPager.Category.CategoryAdapter;

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
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

            adapter = new CategoryAdapter(getActivity(), listCategory, new CategoryAdapter.ListAdapterListener() {
            @Override
                public void onClickDeleteButton(int p) {
                     alertDialog(p,"delete");
                }
                @Override
                public void onClickAddButton(View view) {
                     addDialog(view);
                }
        });
            recyclerCategory.setAdapter(adapter);


    }

    public void addDialog(View v)
    {
       Toast.makeText(getActivity(), "Popup ID: " + listCategory.get(recyclerCategory.getChildAdapterPosition(v)).getName(), Toast.LENGTH_SHORT).show();
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

    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest;
        String url="http://rizikyasociados.com.do/wsDrinkNote/Main/deleteCategory?Id="+id;

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                listCategory.remove(po);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Not conexion",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        //request.add(stringRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
    public void alertDialog(final int position, final String method)
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Are your sure "+method+" this item?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        switch (method)
                        {
                            case  "delete" :
                                webServiceDelete(listCategory.get(position).getId().toString(),position);
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
    public void refresh()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


/*    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category_fragment, menu);
 *//*       MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);*//*

        super.onCreateOptionsMenu(menu, inflater);
    }*/


}
