package lb.yiimgo.storenote.Fragment;


import android.app.ProgressDialog;
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
import android.widget.AbsListView;
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
import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.Entity.VolleySingleton;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.Board.BoardAdapter;

public class BoardFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public RecyclerView recyclerBoard;
    public ArrayList<Boards> listBoard;
    public ArrayList<Boards> newList;
    public ProgressDialog progressDialog;
    public BoardAdapter adapter;
    public Boards board = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SearchView searchView;
    public SessionManager sessionManager;
    public TextView notFound;
    public boolean ifSearch = false;

    public BoardFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_board, container, false);
        listBoard = new ArrayList<>();

        recyclerBoard = (RecyclerView) view.findViewById(R.id.id_recycle_board);
        recyclerBoard.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerBoard.setHasFixedSize(true);
        notFound = (TextView) view.findViewById(R.id.not_found);
        adapterOnClick();
        requestQueue = Volley.newRequestQueue(getContext());


        recyclerBoard.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    Toast.makeText(getContext(),"UP",Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getContext(),"DOWN",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Toast.makeText(getContext(),"SCROLL_STATE_FLING",Toast.LENGTH_SHORT);
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Toast.makeText(getContext(),"SCROLL_STATE_TOUCH_SCROLL",Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getContext(),"lo contrario",Toast.LENGTH_SHORT);
                }
            }
        });
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

        JSONArray json = response.optJSONArray("board");
        try{
            for(int i =0; i<json.length(); i++)
            {
                board = new Boards();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                board.setIdServices(jsonObject.optString("Id"));
                board.setFullName(jsonObject.getString("FullName"));
                board.setUbication("Ubication - " + jsonObject.optString("Ubication"));
                //Board.getAmount(jsonObject.optString("Amount"));

                listBoard.add(board);

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        progressDialog.hide();

        recyclerBoard.setAdapter(adapter);

    }

    public void adapterOnClick()
    {
        adapter = new BoardAdapter(getActivity(), listBoard, new BoardAdapter.ListAdapterListener() {

            @Override
            public void onClickAddButton(View v) {
                addDialog(v);
            }
        });
    }
    public void addDialog(View v)
    {
        String value;
        if(ifSearch)
            value = newList.get(recyclerBoard.getChildAdapterPosition(v)).getUbication();
        else
            value = listBoard.get(recyclerBoard.getChildAdapterPosition(v)).getUbication();

        Toast.makeText(getActivity(), "Popup ID: " + value, Toast.LENGTH_SHORT).show();
    }
    public void loadWebServices()
    {
        sessionManager = new SessionManager(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String idUser = sessionManager.getDataFromSession().get(4);

        String url = Utility.BASE_URL +"Main/getDataBoard?Id=" + idUser;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest;
        String url= Utility.BASE_URL +"Main/deleteBoard?Id="+id;

        stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                listBoard.remove(po);
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


    public void refresh()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        ifSearch = false;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
        inflater.inflate(R.menu.menu_board_fragment, menu);
        MenuItem item = menu.findItem(R.id.search_board);
        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(listBoard.size() > 0){
                    ifSearch = true;
                    newText = newText.toLowerCase();
                    newList = new ArrayList<>();

                    for(Boards c : listBoard)
                    {
                        String name = c.getUbication().toLowerCase();
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
            case R.id.refresh_board:
                refresh();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
