package sanchez.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import sanchez.Adapters.AdapterRecords;
import sanchez.Model.Accounts;

public class Accounts_Record extends AppCompatActivity {
    Context context;
    DatabaseHelper DB;
    ListView lvRecordLists;
    ArrayList<Accounts> arrayList;
    AdapterRecords adapterRecords;
    Accounts ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts__record);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        arrayList = new ArrayList<>();
        DB = new DatabaseHelper(this);
        lvRecordLists = findViewById(R.id.lvAccountsRecord);
        arrayList = DB.getAllRecords();
        adapterRecords = new AdapterRecords(this,arrayList);


        //Display All Accounts Record
        try {
            lvRecordLists.setAdapter(adapterRecords);
            adapterRecords.notifyDataSetChanged();
            lvRecordLists.invalidate();
            lvRecordLists.refreshDrawableState();
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu_id = item.getItemId();
        if (menu_id==R.id.btnlogout){
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
