package lb.yiimgo.storenote.Fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import lb.yiimgo.storenote.ViewPager.Board.BoardDetailAdapter;

@SuppressLint("ValidFragment")
public class BoardDetailsFragment extends DialogFragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public RecyclerView recyclerBoard;
    public ArrayList<Boards> listBoard;
    public ArrayList<Boards> newList;
    public ProgressDialog progressDialog;
    public BoardDetailAdapter adapter;
    public Boards board = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SearchView searchView;
    public SessionManager sessionManager;
    public TextView notFound;
    public boolean ifSearch = false;
    int spanCount = 1;
    int spacing = 50;
    boolean includeEdge = false;
    public Context _context;
    public Boards _boarDetail;

    public BoardDetailsFragment(Context context, Boards board) {
        this._context = context;
        this._boarDetail = board;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_board_details, container, false);
        listBoard = new ArrayList<>();

        recyclerBoard = (RecyclerView) view.findViewById(R.id.id_recycle_board_details);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, spanCount);
        recyclerBoard.setLayoutManager(mLayoutManager);
        recyclerBoard.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerBoard.setHasFixedSize(true);
        notFound = (TextView) view.findViewById(R.id.not_found);
        adapterOnClick();
        requestQueue = Volley.newRequestQueue(_context);

        loadWebServices();
        return view;
    }

    public void adapterOnClick()
    {
        adapter = new BoardDetailAdapter(getActivity(), listBoard, new BoardDetailAdapter.ListAdapterListener() {

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
    @Override
    public void onErrorResponse(VolleyError error)
    {
        Toast.makeText(_context,"Error " + error.toString(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("boardDetail");
        try
        {
            for(int i =0; i<json.length(); i++)
            {
                board = new Boards();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                board.setIdServices(jsonObject.optString("Id"));
                board.setFullName(jsonObject.getString("FullName"));
                board.setUbication(jsonObject.optString("Ubication"));
                board.setAmount(jsonObject.getDouble("Amount"));
                board.setService(jsonObject.getString("Service"));
                board.setServiceCategory(jsonObject.getString("ServiceCategory"));
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
    public void loadWebServices()
    {
        sessionManager = new SessionManager(_context);
        progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        String idUser = sessionManager.getDataFromSession().get(4);

        String url = Utility.BASE_URL +"Main/getDataBoardDetails?Id=" + idUser +"&Ub="+_boarDetail.getUbication();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }
    private void webServiceDelete(String id,final int po) {

        progressDialog = new ProgressDialog(_context);
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
                Toast.makeText(_context,"Not connection",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });
        VolleySingleton.getIntanciaVolley(_context).addToRequestQueue(stringRequest);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column+1* spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column ) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
