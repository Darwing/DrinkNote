package lb.yiimgo.storenote.Fragment;


import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.Boards;
import lb.yiimgo.storenote.Fragment.DialogFragment.BoardDetailsFragment;
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
    int spanCount = 2;
    int spacing = 50;

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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), spanCount);
        recyclerBoard.setLayoutManager(mLayoutManager);
        recyclerBoard.addItemDecoration(new GridSpacingItemDecoration(spacing));
        recyclerBoard.setHasFixedSize(true);
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

        JSONArray json = response.optJSONArray("board");
        try{
            for(int i =0; i<json.length(); i++)
            {
                board = new Boards();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                board.setIdServices(jsonObject.optString("Id"));
                board.setFullName(jsonObject.getString("FullName"));
                board.setUbication(jsonObject.optString("Ubication"));
                board.setTotalAmount(jsonObject.getDouble("TotalAmount"));
                board.setDateCreate(jsonObject.getString("DateCreate"));

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
        Boards value;
        if(ifSearch)
            value = newList.get(recyclerBoard.getChildAdapterPosition(v));
        else
            value = listBoard.get(recyclerBoard.getChildAdapterPosition(v));

        BoardDetailsFragment baordDetailsaordFragment = new BoardDetailsFragment(getContext(),value);
        baordDetailsaordFragment.show(getActivity().getFragmentManager(),"roomDialog");
    }
    public void loadWebServices()
    {
        sessionManager = new SessionManager(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String idUser = sessionManager.getDataFromSession().get(4);
        String p = sessionManager.getDataFromSession().get(0);

        String url = Utility.BASE_URL +"Main/getDataBoard?Id=" + idUser +"&profile="+ p;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onPrepareOptionsMenu(menu);
       MenuItem item = menu.findItem(R.id.search).setVisible(false);
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
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int halfSpace;

        public GridSpacingItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }

            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }

}
