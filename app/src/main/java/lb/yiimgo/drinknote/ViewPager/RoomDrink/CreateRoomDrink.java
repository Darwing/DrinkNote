package lb.yiimgo.drinknote.ViewPager.RoomDrink;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import lb.yiimgo.drinknote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.drinknote.R;
import lb.yiimgo.drinknote.Utility.Utility;


public class CreateRoomDrink extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name;
    private Spinner spinner;
    private String roomSelected;

    private static final String[] paths = {"Room I", "Room II", "Room III","Room IV","Room V","Room VI",
    "Room VII","Room VIII","Room IX","Room X","Room XII"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_rooms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner)findViewById(R.id.spinner_room);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateRoomDrink.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(this);

        name = (EditText) findViewById(R.id.room_name);


        final Button button = findViewById(R.id.send_data_room);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regiterDrinkRoom();
            }
        });
    }


    private void regiterDrinkRoom()
    {
        ConecctionSQLiteHelper conn = new ConecctionSQLiteHelper(this, "db_drinknote",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utility.FIELD_NAME_ROOM , name.getText().toString());
        values.put(Utility.FIELD_ROOM_DRINK_UBICATION, roomSelected);

        Long idResult =db.insert(Utility.TABLE_ROOM_DRINK,Utility.FIELD_ID_ROOM,values);
        //Toast.makeText(getApplicationContext(),"id result: "+ idResult, Toast.LENGTH_SHORT).show();
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                roomSelected = "Room I";
                break;
            case 1:
                roomSelected = "Room II";
                break;
            case 2:
                roomSelected = "Room III";
                break;
            case 3:
                roomSelected = "Room IV";
                break;
            case 4:
                roomSelected = "Room V";
                break;
            case 5:
                roomSelected = "Room VI";
                break;
            case 6:
                roomSelected = "Room VII";
                break;
            case 7:
                roomSelected = "Room VIII";
                break;
            case 8:
                roomSelected = "Room IX";
                break;
            case 9:
                roomSelected = "Room X";
                break;
            case 10:
                roomSelected = "Room XI";
                break;
            case 11:
                roomSelected = "Room XII";
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
