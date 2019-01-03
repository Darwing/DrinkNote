package lb.yiimgo.storenote.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import lb.yiimgo.storenote.Entity.Users;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.User.UserAdapter;

public class UserFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public RecyclerView recyclerUser;
    public ArrayList<Users> listUser;
    public ProgressDialog progressDialog;
    public UserAdapter adapter;
    public Users users;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SearchView searchView;
    public ArrayList<Users> newList;
    public TextView notFound;

    public UserFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_user, container, false);
        listUser = new ArrayList<>();
        recyclerUser = (RecyclerView) view.findViewById(R.id.idRecycler);
        recyclerUser.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerUser.setHasFixedSize(true);
        notFound = (TextView) view.findViewById(R.id.not_found);

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

        JSONArray json = response.optJSONArray("services");
        try{
            for(int i =0; i<json.length(); i++)
            {
                users = new Users();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                users.setUserName(jsonObject.optString("UserName"));
                users.setPassword(jsonObject.optString("Password"));
                users.setProfile(jsonObject.optString("Profile"));
                users.setCompany(jsonObject.optString("Company"));
                users.setFullName(jsonObject.optString("FullName"));

                listUser.add(users);

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        progressDialog.hide();

        adapter = new UserAdapter(getActivity(), listUser, new UserAdapter.ListAdapterListener() {

            @Override
            public void onClickAddButton(View view) {
                addDialog(view);
            }
        });
        recyclerUser.setAdapter(adapter);

    }

    public void addDialog(View v)
    {
        Toast.makeText(getActivity(), "Popup ID: " + listUser.get(recyclerUser.getChildAdapterPosition(v)).getFullName(), Toast.LENGTH_SHORT).show();
    }
    public void loadWebServices()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = Utility.BASE_URL +"Main/getDataServices";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest;
        String url= Utility.BASE_URL +"Main/deleteUser?Id="+id;

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                listUser.remove(po);
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
                                webServiceDelete(listUser.get(position).getId().toString(),position);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) item.getActionView();

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //Toast.makeText(getContext(), "onMenuItemActionExpand called", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                notFound.setVisibility(View.GONE);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                newList = new ArrayList<>();

                for(Users c : listUser)
                {
                    String name = c.getFullName().toLowerCase();
                    if(name.contains(newText)){
                        newList.add(c);
                    }
                }

                if (newList.size() == 0){
                    if(!newText.isEmpty())
                        notFound.setText("Record not found with '"+newText+"'");
                }else
                {
                    notFound.setVisibility(View.GONE);
                }

                adapter.updateList(newList);

                return true;
            }
        });

    }



}
