package lb.yiimgo.storenote.Fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.SessionManager;
import lb.yiimgo.storenote.Entity.Category;
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
    public Category _category;
    TextView itemSelected;
    TextView itemCost;
    public DialogsFragment(Context context,Category category)
    {
        this._context = context;
        this._category = category;
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
       itemSelected.setText(_category.getName());
       itemCost.setText(Utility.getFormatedAmount(_category.getAmount()));
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

    public void addServicesToRoom(View v)
    {

        Toast.makeText(getActivity(), "Popup ID: " +listRoomDrink.get(recyclerRoomDrink.getChildAdapterPosition(v)).getIdRoom(), Toast.LENGTH_SHORT).show();
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
    public void loadWebServices()
    {
        sessionManager = new SessionManager(_context);

        String idUser = sessionManager.getDataFromSession().get(4);
        String idProfile = sessionManager.getDataFromSession().get(0);

        String url = "http://rizikyasociados.com.do/wsDrinkNote/Main/getDataRooms?Id=" + idUser +"&idProfile="+idProfile;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }
}
