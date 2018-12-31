package lb.yiimgo.storenote.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public ProgressDialog progressDialog;

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
       recyclerRoomDrink.setLayoutManager(new LinearLayoutManager(_context));
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
                RoomDrink.setRoomUbication("Ubication - " + jsonObject.optString("RoomUbication"));
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
        String ubication = listRoomDrink.get(recyclerRoomDrink.getChildAdapterPosition(v)).getIdRoom();
        String url = Utility.BASE_URL + "Main/saveDataBoard?IdServices="+ _services.getId()
                +"&Amount="+ _services.getAmount()
                +"&CategoryId="+ _services.getServiceId()
                +"&Ubication="+ubication
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
}
