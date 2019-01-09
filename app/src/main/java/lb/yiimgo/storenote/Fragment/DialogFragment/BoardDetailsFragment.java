package lb.yiimgo.storenote.Fragment.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        Response.ErrorListener,BoardDetailAdapter.OnItemClickListener
{
    public View view;
    public RecyclerView recyclerBoard;
    public ArrayList<Boards> listBoard;
    public ProgressDialog progressDialog;
    public BoardDetailAdapter adapter;
    public Boards board = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SessionManager sessionManager;
    public TextView notFound;
    int spanCount = 1;
    int spacing = 50;
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_board_details, container, false);
        listBoard = new ArrayList<>();

        recyclerBoard = (RecyclerView) view.findViewById(R.id.id_recycle_board_details);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, spanCount);
        recyclerBoard.setLayoutManager(mLayoutManager);
        recyclerBoard.addItemDecoration(new GridSpacingItemDecoration(spacing));
        recyclerBoard.setHasFixedSize(true);
        notFound = (TextView) view.findViewById(R.id.not_found);
        adapter = new BoardDetailAdapter(getActivity(), listBoard);
        requestQueue = Volley.newRequestQueue(_context);
        loadWebServices();

        return view;
    }

    public void addDialog(final int position)
    {
        CommentServicesFragment commentServicesFragment = new CommentServicesFragment(_context,listBoard,position,adapter);
        commentServicesFragment.show(((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction(),"commentDialog");

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
                board.setSerCateId(jsonObject.getInt("SerCateId"));

                listBoard.add(board);

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        progressDialog.hide();
        adapter.setOnItemClickListener(this);
        recyclerBoard.setAdapter(adapter);
    }

    public void loadWebServices()
    {
        sessionManager = new SessionManager(_context);
        progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String idUser = sessionManager.getDataFromSession().get(4);
        String p = sessionManager.getDataFromSession().get(0);

        String url = Utility.BASE_URL +"Main/getDataBoardDetails?Id=" + idUser +"&Ub="+_boarDetail.getUbication()+"&profile="+p;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClickDeleteButton(int position) {
        addDialog(position);
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
