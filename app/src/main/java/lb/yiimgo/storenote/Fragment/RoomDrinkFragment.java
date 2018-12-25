package lb.yiimgo.storenote.Fragment;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import lb.yiimgo.storenote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.storenote.Entity.RoomDrinks;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;
import lb.yiimgo.storenote.ViewPager.RoomDrink.RoomDrinkAdapter;

public class RoomDrinkFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    View view;
    RecyclerView recyclerRoom;
    ArrayList<RoomDrinks> listDrinkRoom;
    ProgressDialog progressDialog;
    ConecctionSQLiteHelper conn;
    SQLiteDatabase db;
    RoomDrinks drinkRoom;
    public RoomDrinkFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());

        view = inflater.inflate(R.layout.fragment_room_drink, container, false);
        listDrinkRoom = new ArrayList<>();
        recyclerRoom = (RecyclerView) view.findViewById(R.id.display_room);
        recyclerRoom.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerRoom.setHasFixedSize(true);
        getData();

        return view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
    }

    public void getData()
    {
        drinkRoom = null;
        conn = new ConecctionSQLiteHelper(this.getContext(), "db_drinknote",null,1);
        db = conn.getWritableDatabase();

        Cursor roomData = conn.getRoomInfo(db);

        while (roomData.moveToNext()){
            drinkRoom = new RoomDrinks();
            drinkRoom.setIdRoom(roomData.getInt(roomData.getColumnIndex(Utility.FIELD_ID_ROOM)));
            drinkRoom.setNameRoom(roomData.getString(roomData.getColumnIndex(Utility.FIELD_NAME_ROOM)));
            drinkRoom.setRoomUbication(roomData.getString(roomData.getColumnIndex(Utility.FIELD_ROOM_DRINK_UBICATION)));
            drinkRoom.setStatus(statusText(roomData.getInt(roomData.getColumnIndex(Utility.FIELD_ROOM_DRINK_STATUS))));

            listDrinkRoom.add(drinkRoom);
        }

        RoomDrinkAdapter adapter = new RoomDrinkAdapter(listDrinkRoom);
        recyclerRoom.setAdapter(adapter);

        conn.close();

    }

    private String statusText(Integer status)
    {
        String r = "";
        switch (status)
        {
            case 0 :
                r = "Disponible";
                break;
            case 1 :
                r = "No disponible";
                break;
        }
        return r;
    }
    @Override
    public void onResponse(JSONObject response) {

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_room_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
