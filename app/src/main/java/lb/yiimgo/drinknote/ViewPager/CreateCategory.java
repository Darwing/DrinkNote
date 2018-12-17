package lb.yiimgo.drinknote.ViewPager;

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


public class CreateCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name,amount;
    private Spinner category;
    private Spinner spinner;
    private String categorySelected;

    private static final String[] paths = {"Cerveza", "Wisky", "Romo"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateCategory.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(this);

        category = (Spinner)findViewById(R.id.spinner);
        name = (EditText) findViewById(R.id.store_name);
        amount = (EditText) findViewById(R.id.amount);

        final Button button = findViewById(R.id.send_data);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regiterCategory();
            }
        });
    }


    private void regiterCategory()
    {
        ConecctionSQLiteHelper conn = new ConecctionSQLiteHelper(this, "db_drinknote",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utility.FIELD_NAME, name.getText().toString());
        values.put(Utility.FIELD_AMOUNT, amount.getText().toString());
        values.put(Utility.FIELD_CATEGORY, categorySelected);

         db.insert(Utility.TABLE_CATEGORY,Utility.FIELD_ID,values);

       // Toast.makeText(getApplicationContext(),"id result: "+ idResult,Toast.LENGTH_SHORT).show();
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                categorySelected = "Cerveza";
                break;
            case 1:
                categorySelected = "Wisky";
                break;
            case 2:
                categorySelected = "Romo";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
