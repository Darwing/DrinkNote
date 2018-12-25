package lb.yiimgo.storenote.ViewPager.RoomDrink;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lb.yiimgo.storenote.Entity.ConecctionSQLiteHelper;
import lb.yiimgo.storenote.R;
import lb.yiimgo.storenote.Utility.Utility;


public class CreateRoomDrink extends AppCompatActivity  {

    private EditText name;
    private EditText roomNumber;
    private ConecctionSQLiteHelper conn;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_rooms);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText) findViewById(R.id.room_name);
        roomNumber = (EditText) findViewById(R.id.numberRoom);

        final Button button = findViewById(R.id.send_data_room);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regiterDrinkRoom();
            }
        });
    }

    private void regiterDrinkRoom() {
        conn = new ConecctionSQLiteHelper(this, "db_drinknote", null, 1);
        db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (conn.getRoomById(db, roomNumber.getText().toString())) {

            values.put(Utility.FIELD_NAME_ROOM, name.getText().toString());
            values.put(Utility.FIELD_ROOM_DRINK_UBICATION, roomNumber.getText().toString());

            db.insert(Utility.TABLE_ROOM_DRINK, Utility.FIELD_ID_ROOM, values);

            name.getText().clear();
            roomNumber.getText().clear();
        } else
        {
            Toast.makeText(getApplicationContext(),"This room already exists!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}
