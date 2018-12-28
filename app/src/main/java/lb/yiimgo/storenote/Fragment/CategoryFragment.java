package lb.yiimgo.storenote.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
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

import lb.yiimgo.storenote.Entity.Category;
import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.Category.CategoryAdapter;

public class CategoryFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public RecyclerView recyclerCategory;
    public ArrayList<Category> listCategory;
    public ArrayList<Category> newList;
    public ProgressDialog progressDialog;
    public CategoryAdapter adapter;
    public Category category = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SearchView searchView;
    public RoomDrinkFragment roomDrinkFragment;
    public TextView notFound;
    public boolean ifSearch = false;
    public Utility utility;
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
        notFound = (TextView) view.findViewById(R.id.not_found);
        adapterOnClick();
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

                category.setId(jsonObject.optString("Id"));
                category.setName(jsonObject.optString("Name"));
                category.setAmount(jsonObject.optDouble("Amount"));
                category.setCategory(jsonObject.optString("Category"));
                category.setStatus(jsonObject.optString("Status"));
                category.setNumStatus(jsonObject.optInt("StatusId"));
                listCategory.add(category);

            }
            }catch (JSONException e)
            {
                e.printStackTrace();
            }

            progressDialog.hide();

            recyclerCategory.setAdapter(adapter);

    }

    public void adapterOnClick()
    {
        adapter = new CategoryAdapter(getActivity(), listCategory, new CategoryAdapter.ListAdapterListener() {

            @Override
            public void onClickAddButton(View v) {
                if(listCategory.get(recyclerCategory.getChildAdapterPosition(v)).getNumStatus() == 1)
                {
                    addDialog(v);

                }else
                {
                    String message =listCategory.get(recyclerCategory.getChildAdapterPosition(v)).getName();
                    utility = new Utility(getContext());
                    utility.showDialogAnimation(R.style.DialogSlide,
                        "This item: "+ message
                                +", is not available.","Not Available");
                }
            }
        });
    }
    public void addDialog(View v)
    {
        Category value;
        if(ifSearch)
            value = newList.get(recyclerCategory.getChildAdapterPosition(v));
        else
            value = listCategory.get(recyclerCategory.getChildAdapterPosition(v));

        DialogsFragment dialogsFragment = new DialogsFragment(getContext(),value);
        dialogsFragment.show(getActivity().getFragmentManager(),"roomDialog");

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
                Toast.makeText(getContext(),"Not connection",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
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
                                webServiceDelete(listCategory.get(position).getId(),position);
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
        ifSearch = false;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        inflater.inflate(R.menu.menu_category_fragment, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(listCategory.size() > 0){
                ifSearch = true;
                newText = newText.toLowerCase();
                newList = new ArrayList<>();

                for(Category c : listCategory)
                {
                    String name = c.getName().toLowerCase();
                    if(name.contains(newText)){
                        newList.add(c);
                    }
                }

                if (newList.size() == 0){
                        if(!newText.isEmpty())
                            notFound.setText("Record not found with '"+newText+"'");
                  }

                  adapter.updateList(newList);

                    return true;
                }else{
                    return false;
                }
            }
        });
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

}
