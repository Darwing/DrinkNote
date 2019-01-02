package lb.yiimgo.storenote.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.Entity.Services;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.RoomDrink.RoomDrinkAdapter;

@SuppressLint("ValidFragment")
public class DialogsFragment extends DialogFragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    public View view;
    public RecyclerView recyclerRoomDrink;
    public ArrayList<RoomDrinks> listRoomDrink;
    public RoomDrinkAdapter adapter;
    public RoomDrinks RoomDrink = null;
    public RequestQueue requestQueue;
    public JsonObjectRequest jsonObjectRequest;
    public SessionManager sessionManager;
    public Context _context;
    public Services _services;
    TextView itemSelected;
    TextView itemCost;
    int spanCount = 1;
    int spacing = 50;
    boolean includeEdge = false;

    public DialogsFragment(Context context,Services services)
    {
        this._context = context;
        this._services = services;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                             saveInstanceState)
    {
       view = inflater.inflate(R.layout.form_add_services,container,false);
       listRoomDrink = new ArrayList<>();
       adapterOnClick();
       recyclerRoomDrink = (RecyclerView) view.findViewById(R.id.display_room_dialog);
       RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(_context, spanCount);
       recyclerRoomDrink.setLayoutManager(mLayoutManager);
       recyclerRoomDrink.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
       recyclerRoomDrink.setHasFixedSize(true);
       itemSelected = (TextView) view.findViewById(R.id.item_selected);
       itemCost = (TextView) view.findViewById(R.id.itemCost);
       itemSelected.setText(_services.getName());
       itemCost.setText(Utility.getFormatedAmount(_services.getAmount()));
       requestQueue = Volley.newRequestQueue(_context);

       loadWebServices();
       return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void adapterOnClick()
    {
        adapter = new RoomDrinkAdapter(getActivity(), listRoomDrink, new RoomDrinkAdapter.ListAdapterListener() {

            @Override
            public void onClickAddButton(View v) {

                addServicesToRoom(v);
            }
        });
    }

    public void addServicesToRoom(final View v)
    {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(_context, R.style.DialogTheme);
            builder1.setMessage("Are you sure to add "+ _services.getName()+" in the room "+
                    listRoomDrink.get(recyclerRoomDrink.getChildAdapterPosition(v)).getRoomUbication());
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            saveDataBoard(v);

                            android.app.FragmentManager fm = getFragmentManager();
                            fm.executePendingTransactions();

                            android.app.DialogFragment existingFragment = (android.app.DialogFragment) fm
                                    .findFragmentByTag("roomDialog");
                            if (existingFragment != null) {
                                // Just show the latest error
                                existingFragment.dismiss();
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
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("roomDrink");
        try{
            for(int i =0; i<json.length(); i++)
            {
                RoomDrink = new RoomDrinks();
                JSONObject jsonObject = null;
                jsonObject =json.getJSONObject(i);

                RoomDrink.setIdRoom(jsonObject.optString("IdRoom"));
                RoomDrink.setWaiterRoom(jsonObject.getString("WaiterRoom"));
                RoomDrink.setRoomUbication(jsonObject.optString("RoomUbication"));
                RoomDrink.setStatus(jsonObject.optString("Status"));

                listRoomDrink.add(RoomDrink);

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

        recyclerRoomDrink.setAdapter(adapter);

    }

    public void saveDataBoard(View v)
    {
        sessionManager = new SessionManager(_context);

        String idUser = sessionManager.getDataFromSession().get(4);
        String ubication = listRoomDrink.get(recyclerRoomDrink.getChildAdapterPosition(v)).getRoomUbication();
        String url = Utility.BASE_URL + "Main/saveDataBoard?IdServices="+ _services.getId()
                +"&Amount="+ _services.getAmount()
                +"&CategoryId=" + _services.getServiceId()
                +"&Ubication=" + ubication
                +"&UserCreate=" + idUser;

         jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
         requestQueue.add(jsonObjectRequest);
    }

    public void loadWebServices()
    {
        sessionManager = new SessionManager(_context);

        String idUser = sessionManager.getDataFromSession().get(4);
        String idProfile = sessionManager.getDataFromSession().get(0);

        String url = Utility.BASE_URL +"Main/getDataRooms?Id=" + idUser +"&idProfile="+idProfile;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
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
                outRect.left = (int) (column + 0.2 * spacing / spanCount); // column * ((1f / spanCount) * spacing)
                outRect.right = (int) (spacing - (column + 0.8) * spacing / spanCount); // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
